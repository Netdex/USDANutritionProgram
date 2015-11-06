package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

	private Properties prop;
	private File config;

	public ConfigurationManager(String fileName) {
		prop = new Properties();
		config = new File(fileName);
	}

	public ConfigurationManager(File file) {
		prop = new Properties();
		config = file;
	}

	public void addItem(String key, Object value) {
		prop.setProperty(key, value.toString());
	}

	public String getString(String key) {
		return prop.getProperty(key);
	}

	public int getInteger(String key) {
		return Integer.parseInt(getString(key));
	}

	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}
	
	public void load() {
		try {
			prop.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			prop.store(new FileOutputStream(config), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
