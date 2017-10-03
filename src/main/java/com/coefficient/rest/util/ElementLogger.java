package com.coefficient.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>ElementLogger</code> class helps user to log message to file, it takes
 * logger level and message as an argument.
 * 
 *
 */
public class ElementLogger {

	// Protecting from initialization
	private ElementLogger() {

	}

	// Level of logs
	public enum ElementLoggerLevel {
		WARNING, INFO, DEBUG, ERROR
	};

	private static Logger logger;

	/**
	 * Method to write log to file
	 * 
	 * @param level,
	 *            logger level
	 * @param msg,
	 *            message to be logged
	 * @param clazz,
	 *            Current class Object
	 */
	@SuppressWarnings("rawtypes")
	public static void log(ElementLoggerLevel level, String msg, Class clazz) {
		logger = LoggerFactory.getLogger(clazz);
		switch (level) {
		case WARNING:
			if (logger.isWarnEnabled())
				logger.warn(msg);
			break;
		case INFO:
			if (logger.isInfoEnabled())
				logger.info(msg);
			break;
		case DEBUG:
			if (logger.isDebugEnabled())
				logger.debug(msg);
			break;
		case ERROR:
			if (logger.isErrorEnabled())
				logger.error(msg);
		}
	}

	/**
	 * Method to write log to file
	 * 
	 * @param level,
	 *            logger level
	 * @param msg,
	 *            message to be logged
	 * @param t,
	 *            <code>Throwable</code> as an argument to error level
	 * @param clazz,
	 *            Current class Object
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static void log(ElementLoggerLevel level, String msg, Throwable t, Class clazz) {
		logger = LoggerFactory.getLogger(clazz);
		switch (level) {
		case WARNING:
			if (logger.isWarnEnabled())
				logger.warn(msg, t);
			break;
		case INFO:
			if (logger.isInfoEnabled())
				logger.info(msg, t);
			break;
		case DEBUG:
			if (logger.isDebugEnabled())
				logger.debug(msg, t);
			break;
		case ERROR:
			if (logger.isErrorEnabled())
				logger.error(msg, t);
		}
	}
}
