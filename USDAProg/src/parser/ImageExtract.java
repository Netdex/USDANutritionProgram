package parser;

import gui.GUI;
import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;
import parser.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Downloads images based on search terms from Google Images
 *
 * @author Gordon Guan
 */
public class ImageExtract {

	private static final int MAXIMUM_IMAGE_PRELOAD_RESULTS = 100;
	private static final int MAX_DOWNLOAD_TIME = 10000;
	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.ACCENT_COLOUR, 3);
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
	private static final String ADDITIONAL_KEYWORD = "food";
	private static final BinaryTreeMap<String, Image> imageCache = new BinaryTreeMap<>();
	private static final Stack<FoodItem> PRELOAD_LINE = new Stack<>();

	/**
	 * Puts an image into a JLabel
	 *
	 * @param imageLabel
	 *            The JLabel to put the image in
	 * @param key
	 *            the search term
	 */
	public static void injectImage(JLabel imageLabel, String key) {
		new Thread() {
			public void run() {
				try {
					System.out.println("injecting " + key + " into label");
					if (imageCache.get(key.toLowerCase()) != null) {
						System.out.println("loaded image from cache");
						insertImage(imageCache.get(key.toLowerCase()),
								imageLabel);
					} else {
						System.out.println("searching for image");
						Image img = getSearchImage(key);
						if (img != null) {
							System.out.println("found image");
							insertImage(img, imageLabel);
							imageCache.put(key.toLowerCase(), img);
						} else {
							System.out.println("no image found");
						}
					}
				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null,
							"No internet connection", "Image Not Found",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}.start();
	}

	/**
	 * Initializes the ImageExtractor to preload images in a separate thread
	 */
	public static void initPreload() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						// Go through the entire stack of items waiting to be
						// preloaded, and load them if they don't exist in the
						// cache
						while (!PRELOAD_LINE.isEmpty()) {
							FoodItem fi = PRELOAD_LINE.pop();
							String name = DataManager.getInstance()
									.getRelevantKeywords(fi).toLowerCase();
							if (imageCache.get(name) == null) {
								System.out.println("downloading image " + name);
								Image img = getSearchImage(name);
								if (img != null) {
									System.out.println("preloaded image");
									imageCache.put(name, img);
								} else {
									System.out
											.println("failed to preload image");
								}
							} else {
								System.out.println("image " + name
										+ " is already in the cache");
							}
						}
						Thread.sleep(1000);
					} catch (Exception e) {
						JOptionPane.showConfirmDialog(null, "Failed to initialize image preloading thread.",
								"Preload Failure", JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE);					}
				}

			}
		}.start();
	}

	/**
	 * Queue all the items to be preloaded
	 * 
	 * @param items
	 *            items to the preloaded
	 */
	public static void preloadImages(FoodItem[] items) {
		for (int i = Math.min(MAXIMUM_IMAGE_PRELOAD_RESULTS, items.length - 1); i > 0; i--) {
			PRELOAD_LINE.push(items[i]);
		}
	}

	/**
	 * Inserts an image into a JLabel
	 * 
	 * @param img
	 *            the image to insert
	 * @param imageLabel
	 *            the JLabel to insert into
	 */
	private static void insertImage(Image img, JLabel imageLabel) {
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		int IMAGE_WIDTH = 400;
		double ratio = (double) IMAGE_WIDTH / img.getWidth(null);
		imageLabel.setIcon(new ImageIcon(img.getScaledInstance(IMAGE_WIDTH,
				(int) (img.getHeight(null) * ratio), Image.SCALE_SMOOTH)));
		imageLabel.setSize(IMAGE_WIDTH, imageLabel.getIcon().getIconHeight());
		imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		imageLabel.setBorder(IMAGE_BORDER);
	}

	/**
	 * Gets an image by search query
	 *
	 * @param key
	 *            the query
	 * @return the image
	 */
	private static Image getSearchImage(String key) {
		String uri = getSearchResult(key);
		Image image = null;
		try {
			URL url = new URL(uri);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(MAX_DOWNLOAD_TIME);
			// Set user-agent to prevent 403s on websites which block robot
			// access
			conn.setRequestProperty("User-Agent", USER_AGENT);
			image = ImageIO.read(conn.getInputStream());
		} catch (IOException e) {
			System.err.println(e.getMessage() + " " + uri);
		}

		return image;
	}

	/**
	 * Get the uri of the image from a key
	 *
	 * @param key
	 *            the key to search from
	 * @return the uri of the image
	 */
	private static String getSearchResult(String key) {
		return getImageURL(getJSONResult(key));
	}

	/**
	 * Gets the url of the image from JSON formatted string from Google's API
	 * 
	 * @param json
	 *            the JSON to extract the url from
	 * @return the url of the image
	 */
	private static String getImageURL(String json) {
		// Use regex to extract the url of the image
		Pattern p = Pattern
				.compile("height\":\"([0-9]*)\",.*?,\"unescapedUrl\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		String selectedURL = "";
		int minimumHeight = Integer.MAX_VALUE;
		// Prioritize shorter images, of JPG and PNG types
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
		return selectedURL;
	}

	/**
	 * Queries Google's API and searches for an image
	 * 
	 * @param key
	 *            the search query
	 * @return the JSON data from the API
	 */
	private static String getJSONResult(String key) {
		try {
			URL remote = new URL(
					("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
							+ ADDITIONAL_KEYWORD + "%20" + key.replace(".",
							"%2e")).replace(" ", "%20"));
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