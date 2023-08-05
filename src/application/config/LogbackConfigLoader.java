package application.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackConfigLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbackConfigLoader.class);

	private static final String LOGBACK_CONFIG_FILE = "logback.xml";

	public LogbackConfigLoader() {

		String jarPathName = ClassLoader.getSystemResource("").getPath();

		try {

			File logbackConfigFile = getLogbackConfigFile();
			if (logbackConfigFile.canRead() && logbackConfigFile.isFile()) {
				LOGGER.info("logback.xml exists. Will use it");
				
			} else {

				LOGGER.info("logback.xml does not exist. Will create it");
				InputStream wsdlInputStream = ClassLoader
						.getSystemResourceAsStream("application/config/" + LOGBACK_CONFIG_FILE);
				byte[] buffer = new byte[wsdlInputStream.available()];
				wsdlInputStream.read(buffer);
				wsdlInputStream.close();

				File targetFile = new File(jarPathName + LOGBACK_CONFIG_FILE);
				OutputStream outStream = new FileOutputStream(targetFile);
				outStream.write(buffer);
				outStream.close();
				LOGGER.info("logback.xml created");

			}

		} catch (Exception e) {
			LOGGER.error("Error with log file", e);
		}

		// Init logger
		LoggerContext lc = (LoggerContext) (LoggerFactory.getILoggerFactory());
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();

		// Load config file
		try {
			String logConfigFile = jarPathName + LOGBACK_CONFIG_FILE;
			
			LOGGER.info("Will load config file: " + logConfigFile);
			configurator.doConfigure(logConfigFile);

		} catch (JoranException e1) {
			LOGGER.error("Logback config exception", e1);
		}
	}

	private File getLogbackConfigFile() {
		try {
			// Find the jar file location
			String pathName = ClassLoader.getSystemResource("").getPath();
			LOGGER.info("Jar path: {}", pathName);
			File base = new File(pathName);
			return new File(base, LOGBACK_CONFIG_FILE);

		} catch (Exception e) {
			LOGGER.error("Error ", e);
			return new File(LOGBACK_CONFIG_FILE);
		}
	}

}
