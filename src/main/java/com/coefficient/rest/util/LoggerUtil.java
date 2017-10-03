package com.coefficient.rest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coefficient.rest.util.ElementLogger.ElementLoggerLevel;

/**
 * Helps in reading log file based on date
 * 
 * 
 *
 */
@Component
public class LoggerUtil {

	@Value("${logger.base.path}")
	private String LOGGER_BASE_PATH;

	@Value("${logger.file.name}")
	private String LOGGER_FILE_NAME;

	@Value("${catalina.home.path}")
	private String CATALINA_HOME_PATH;

	/**
	 * Parse string to Date type
	 * 
	 * @param date
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertToDateddMMyyyy(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		if (date != null) {
			return format.parse(date);
		} else {
			throw new NullPointerException();
		}

	}

	/**
	 * Parse string to Date type
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date convertToDateyyyyMMdd(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			return format.parse(date);
		} else {
			throw new NullPointerException();
		}

	}

	/**
	 * Method to convert java date to sql date type
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date convertJavaDateToSqlDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date utilDate = dateFormat.parse(date);
		dateFormat.applyPattern("yyyy-MM-dd");
		return convertToDateyyyyMMdd(dateFormat.format(utilDate));
	}

	/**
	 * This method will test if log starts with a date(dd-MM-yyyy) or not, if
	 * exist we will perform further operation
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isValidDateFormate(String date) {
		return date.matches("^\\d{2}-\\d{2}-\\d{4}");
	}

	/**
	 * Method to read log file
	 * 
	 * @param date
	 * @return
	 */
	public String readLogs(String userDate) {

		File file = new File(LOGGER_BASE_PATH + LOGGER_FILE_NAME + "." + userDate);
		StringBuilder builder = new StringBuilder();
		String path;
		if (file.exists()) {
			path = LOGGER_BASE_PATH + LOGGER_FILE_NAME + "." + userDate;
		} else {
			path = LOGGER_BASE_PATH + LOGGER_FILE_NAME;
		}

		try (FileReader in = new FileReader(path); BufferedReader br = new BufferedReader(in);) {
			String str;
			while ((str = br.readLine()) != null) {

				builder.append(str).append(System.getProperty("line.separator"));
			}

		} catch (FileNotFoundException e) {
			ElementLogger.log(ElementLoggerLevel.ERROR, "File (logger) not found exception ", e, LoggerUtil.class);
			builder.setLength(0);
			builder.append("Could not find log file");
		} catch (IOException e) {
			ElementLogger.log(ElementLoggerLevel.ERROR, "IOException for logger ", e, LoggerUtil.class);
			builder.setLength(0);
			builder.append("Exception occured while reading file");
		}
		return builder.toString();
	}

	/**
	 * List all files name in folder
	 * 
	 * @param folderPath
	 * @return
	 */
	public static List<String> listOfAllFileInFolder(String folderPath) {
		List<String> files = new ArrayList<String>();
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				files.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return files;
	}

	/**
	 * Method to compare two dates
	 * 
	 * @param userDate
	 * @param loggerDate
	 * @return
	 */
	public static boolean compareDates(Date userDate, Date loggerDate) {
		return userDate.compareTo(loggerDate) == 0 ? true : false;
	}

}
