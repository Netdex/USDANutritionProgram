package parser;

import gui.GUI;

import java.awt.Component;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import parser.util.DoublyLinkedList;

public class ImageExtract {

	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 3);

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
	private static final String ADDITIONAL_KEYWORD = "food";
	private static int IMAGE_WIDTH = 400;

	public static void injectImage(JLabel imageLabel, String key) {
		new Thread() {
			public void run() {
				try {
					Image img = getSearchImage(key);
					if (img != null) {

						imageLabel
								.setHorizontalAlignment(SwingConstants.CENTER);
						double ratio = (double) IMAGE_WIDTH
								/ img.getWidth(null);
						imageLabel.setIcon(new ImageIcon(img.getScaledInstance(
								IMAGE_WIDTH,
								(int) (img.getHeight(null) * ratio),
								Image.SCALE_SMOOTH)));
						imageLabel.setSize(IMAGE_WIDTH, imageLabel.getIcon()
								.getIconHeight());
						imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
						imageLabel.setBorder(IMAGE_BORDER);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	public static Image getSearchImage(String key) {
		String uri = getSearchResult(key);
		Image image = null;
		System.out.println("searching for " + key + "\n" + uri);
		try {
			URL url = new URL(uri);
			URLConnection conn = url.openConnection();
			// Set user-agent to prevent 403s on websites which block robot
			// access
			conn.setRequestProperty("User-Agent", USER_AGENT);
			image = ImageIO.read(conn.getInputStream());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return image;
	}

	public static String getSearchResult(String key) {
		return getImageURL(getJSONResult(key));
	}

	private static String getImageURL(String json) {
		System.out.println(json);
		Pattern p = Pattern
				.compile("height\":\"([0-9]*)\",.*?,\"unescapedUrl\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		String selectedURL = "";
		int minimumHeight = Integer.MAX_VALUE;
		while (m.find()) {
			int currentHeight = Integer.parseInt(m.group(1));
			String currentURL = m.group(2);
			if (currentURL.endsWith("jpg") || currentURL.endsWith("png")) {
				if (currentHeight < minimumHeight && currentHeight > 128) {
					selectedURL = currentURL;
					minimumHeight = currentHeight;
				}
			}
		}
		System.out.println(selectedURL);
		return selectedURL;
	}

	private static String getJSONResult(String key) {
		try {
			URL remote = new URL((
					"http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
							+ ADDITIONAL_KEYWORD + "%20"
							+ key).replace(" ", "%20"));
			BufferedReader br = new BufferedReader(new InputStreamReader(
					remote.openStream()));
			String result = "";
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "";
	}
}