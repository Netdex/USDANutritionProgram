<<<<<<< HEAD
package parser;

import gui.GUI;

import java.awt.Component;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
public class ImageExtract {

	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 5);

	private static int IMAGE_WIDTH = 400;

	public static void injectImage(JLabel image, String key) {
		new Thread() {
			public void run() {
				try {
					Image img = getSearchImage(key);
					if (img != null) {

						image.setHorizontalAlignment(SwingConstants.CENTER);
						double ratio = (double) IMAGE_WIDTH
								/ img.getWidth(null);
						image.setIcon(new ImageIcon(img.getScaledInstance(
								IMAGE_WIDTH,
								(int) (img.getHeight(null) * ratio),
								Image.SCALE_SMOOTH)));
						image.setSize(IMAGE_WIDTH, image.getIcon()
								.getIconHeight());
						image.setAlignmentX(Component.LEFT_ALIGNMENT);
						image.setBorder(IMAGE_BORDER);
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
		try {
			URL url = new URL(uri);
			image = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println(e.getMessage() + key);
		}
		System.out.println("searching for " + key);
		return image;
	}

	public static String getSearchResult(String key) {
		return getImageURL(getJSONResult(key));
	}

	private static String getImageURL(String json) {
		Pattern p = Pattern.compile("\"unescapedUrl\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		String url = "";
		if (m.find())
			url = m.group(1);
		return url;
	}

	private static String getJSONResult(String key) {
		try {
			URL remote = new URL(
					"http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
							+ key.replace(" ", "%20") + "%20food&imgsz=medium");
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
=======
package parser;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gui.GUI;

public class ImageExtract {

	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 3);

	private static int IMAGE_HEIGHT = 192;

	public static void injectImage(JLabel image, String key) {
		new Thread() {
			public void run() {
				try {
					Image img = getSearchImage(key);
					if (img != null) {
						image.setSize(480, IMAGE_HEIGHT);
						image.setBorder(IMAGE_BORDER);
						double ratio = (double) IMAGE_HEIGHT / img.getHeight(null);
						image.setIcon(new ImageIcon(
								img.getScaledInstance((int) (img.getWidth(null) * ratio),
										IMAGE_HEIGHT, Image.SCALE_SMOOTH)));
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
			image = ImageIO.read(url);
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
		Pattern p = Pattern.compile("\"unescapedUrl\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		String url = "";
		while (m.find() && !(url.endsWith("jpg") || url.endsWith("png") || url.endsWith("gif")))
			url = m.group(1);
		return url;
	}

	private static String getJSONResult(String key) {
		try {
			URL remote = new URL(
					"http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=food%20"
							+ key.replace(" ", "%20") + "&imgsz=medium");
			BufferedReader br = new BufferedReader(new InputStreamReader(remote.openStream()));
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
=======
package parser;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gui.GUI;

public class ImageExtract {

	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 3);

	private static int IMAGE_HEIGHT = 192;

	public static void injectImage(JLabel image, String key) {
		new Thread() {
			public void run() {
				try {
					Image img = getSearchImage(key);
					if (img != null) {
						image.setSize(480,
								IMAGE_HEIGHT);
						image.setBorder(IMAGE_BORDER);
						double ratio = (double) IMAGE_HEIGHT
								/ img.getHeight(null);
						image.setIcon(new ImageIcon(img.getScaledInstance(
								(int) (img.getWidth(null) * ratio),
								IMAGE_HEIGHT, Image.SCALE_SMOOTH)));
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
		try {
			URL url = new URL(uri);
			image = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println(e.getMessage() + key);
		}
		System.out.println("searching for " + key);
		return image;
	}

	public static String getSearchResult(String key) {
		return getImageURL(getJSONResult(key));
	}

	private static String getImageURL(String json) {
		Pattern p = Pattern.compile("\"unescapedUrl\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		String url = "";
		if (m.find())
			url = m.group(1);
		return url;
	}

	private static String getJSONResult(String key) {
		try {
			URL remote = new URL(
					"http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=food%20"
							+ key.replace(" ", "%20") + "&imgsz=medium");
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
>>>>>>> branch 'master' of https://github.com/Netdex/USDANutritionProgram.git
