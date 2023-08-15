package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkException.APIFrameworkException;

public class ConfigurationManager {

	// to read the properties we need
	private Properties prop;
	private FileInputStream ip;

	public Properties initProp() {

		// for multiple environments:

		prop = new Properties();

		// maven: cmd line argument:
		// mvn clean install -Denv="qa" or Denv="stage" --> Denv is reading the value of
		// the env variable

		String envName = System.getProperty("env"); // read the value of the env variable in maven

		System.out.println("Running tests on env: " + envName);

		try {

			if (envName == null) {
				System.out.println("no env is given ... hence running tests on QA env ...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				
			} else {
				System.out.println("Running tests on env: " + envName);

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("Please pass the right env name ..." + envName);
					throw new APIFrameworkException("WRONG ENV is Given");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// this is when we have only one environment:
//		prop = new Properties();
//		try {
//			ip = new FileInputStream("./src/test/resources/config/config.properties");
//			prop.load(ip);		
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		

		return prop;
	}

}
