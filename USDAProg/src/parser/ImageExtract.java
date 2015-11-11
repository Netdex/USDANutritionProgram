package parser;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class ImageExtract {

	public static Image getSearchImage(String key) {
		String uri = getSearchResult(key);
		Image image = null;
		try {
			URL url = new URL(uri);
			image = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println(e.getMessage());
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
							+ key + "&imgsz=small");
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
