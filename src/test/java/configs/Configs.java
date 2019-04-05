package configs;

import datas.PathProvider;
import utils.ReadProperties;

public class Configs {
	private static ReadProperties readProConf = new ReadProperties(PathProvider.getConfigs());
	public static String BROWSER = readProConf.getValue("browser");
	public static String SCREEN_SIZE = readProConf.getValue("screenSize");
}
