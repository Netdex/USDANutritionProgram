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

import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;

public class ImageExtract {

	private static BinaryTreeMap<String, Image> imageCache = new BinaryTreeMap<String, Image>();

	private static final javax.swing.border.Border IMAGE_BORDER = BorderFactory
			.createLineBorder(GUI.HEADER_COLOUR, 3);

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
	private static final String ADDITIONAL_KEYWORD = "food";
	private static int IMAGE_WIDTH = 400;

	public static final int MAXIMUM_IMAGE_PRELOAD_RESULTS = 100;
	public static final int MAX_DOWNLOAD_TIME = 10000;

	private static Stack<FoodItem> PRELOAD_LINE = new Stack<FoodItem>();
	
	public static void injectImage(JLabel imageLabel, String key) {
		new Thread() {
			public void run() {
				try {
					System.out.println("injecting " + key + " into label");
					if (imageCache.get(key.toLowerCase()) != null) {
						System.out.println("loaded image from cache");
						insertImage(imageCache.get(key.toLowerCase()), imageLabel);
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
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void preloadImages(FoodItem[] items){
		new Thread(){
			public void run(){
				try{
					int count = 0;
					for(int i = 0; i < items.length; i++){
						String name = DataManager.getInstance().getRelevantKeywords(items[i]).toLowerCase();
						if(imageCache.get(name) == null){
							System.out.println("downloading image " + name);
							Image img = getSearchImage(name);
							if(img != null){
								System.out.println("preloaded image");
								imageCache.put(name, img);
								count++;

	public static void initPreload() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						while (!PRELOAD_LINE.isEmpty()) {
							FoodItem fi = PRELOAD_LINE.pop();
							String name = DataManager.getInstance().getFoodItemRelevantKeyword(fi)
									.toLowerCase();
							if (imageCache.get(name) == null) {
								System.out.println("downloading image " + name);
								Image img = getSearchImage(name);
								if (img != null) {
									System.out.println("preloaded image");
									imageCache.put(name, img);
								} else {
									System.out.println("failed to preload image");
								}
							} else {
								System.out.println("image " + name + " is already in the cache");
							}
						}
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}.start();
	}

	public static void preloadImages(FoodItem[] items) {
		for (int i = Math.min(MAXIMUM_IMAGE_PRELOAD_RESULTS, items.length - 1); i > 0; i--) {
			PRELOAD_LINE.push(items[i]);
		}
	}

	private static void insertImage(Image img, JLabel imageLabel) {
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		double ratio = (double) IMAGE_WIDTH / img.getWidth(null);
		imageLabel.setIcon(new ImageIcon(img.getScaledInstance(IMAGE_WIDTH,
				(int) (img.getHeight(null) * ratio), Image.SCALE_SMOOTH)));
		imageLabel.setSize(IMAGE_WIDTH, imageLabel.getIcon().getIconHeight());
		imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		imageLabel.setBorder(IMAGE_BORDER);
	}

	public static Image getSearchImage(String key) {
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

	public static String getSearchResult(String key) {
		return getImageURL(getJSONResult(key));
	}

	private static String getImageURL(String json) {
		Pattern p = Pattern.compile("height\":\"([0-9]*)\",.*?,\"unescapedUrl\":\"(.*?)\"");
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
		return selectedURL;
	}

	private static String getJSONResult(String key) {
		try {
			URL remote = new URL(("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
					+ ADDITIONAL_KEYWORD + "%20" + key.replace(".", "%2e")).replace(" ", "%20"));
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