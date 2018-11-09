package resources;

import java.util.Properties;

public class Config {
	Properties configFile;
	private static Config configurations;

	public static Config getInstance() {
		if (configurations == null)
			configurations = new Config();
		return configurations;
	}

	private Config() {
		configFile = new java.util.Properties();
		try {
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("resources/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}
}