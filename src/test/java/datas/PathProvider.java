package datas;

public class PathProvider {
	private static String PROJ_DIR = System.getProperty("user.dir");
	private static String RESOURCES_CONFIGS = PROJ_DIR + "/src/test/resources/Configs/";
	private static String RESOURCES_ELEMENTS = PROJ_DIR + "/src/test/resources/Elements/";
	private static String RESOURCES_DRIVERS= PROJ_DIR + "/src/test/resources/Drivers/";
	
	public static String getChromeDriver() {
		return RESOURCES_DRIVERS + "chromedriver.exe";
	}
	
	public static String getElements(String fileName) {
		return RESOURCES_ELEMENTS + fileName;
	}
	
	public static String getConfigs(String fileName) {
		return RESOURCES_CONFIGS + fileName;
	}
	
	public static String getConfigs() {
		return RESOURCES_CONFIGS + "configs.properties";
	}
	
}
