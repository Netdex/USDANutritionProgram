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
	
	public ConfigurationManager(File file){
		prop = new Properties();
		config = file;
	}

	public void addItem(String key, String value) {
		prop.setProperty(key, value);
	}

	public String getItem(String key) {
		return prop.getProperty(key);
	}

	public void load() {
		try {
			prop.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			prop.store(new FileOutputStream(config), "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
