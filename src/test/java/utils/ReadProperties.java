package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

public class ReadProperties {
	private String FILE_PATH = "";
	private Properties FILE = new Properties();
	private FileInputStream fileIn = null;
//	private FileOutputStream fileOut = null;
	
	public ReadProperties(String filePath) {
		super();
		this.FILE_PATH = filePath;
		openFile();
	}
	
	public String getFilePath() {
		return FILE_PATH;
	}

	public void setFILE_PATH(String filePath) {
		FILE_PATH = filePath;
	}
	
	public String getValue(String keyName) {
		String keyValue;
		try {
			keyValue = FILE.getProperty(keyName).trim();
			Assert.assertFalse(keyValue.isEmpty());
		} catch(AssertionError ae) {
			keyValue = "";
		}
		catch(Exception ex) {
			keyValue = "";
		}
		return keyValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ReadProperties) ? (((ReadProperties)obj).getFilePath() == this.FILE_PATH) : false;
	}
	
	private void openFile() {
		try {
			fileIn = new FileInputStream(FILE_PATH);
			FILE.load(fileIn);
		}catch(IOException ex) {
			System.err.printf("NOT FOUND File[%s]: %nERROR: %s%n", FILE_PATH, ex.getMessage());
		}
//		System.out.printf("FILE[%s] IS LOADED!%n", FILE_PATH);
	}
}
