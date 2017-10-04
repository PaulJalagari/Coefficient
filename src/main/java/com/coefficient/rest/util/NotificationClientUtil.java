package com.coefficient.rest.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coefficient.rest.util.CoefficientLogger.CoefficientLoggerLevel;

/**
 * Notification Client Utility
 * 
 * 
 *
 */
@Component
public class NotificationClientUtil {

	/**
	 * Notification URL - Read from configuration file
	 */
	@Value("${notificationURL}")
	private String NOTIFICATION_URL;

	/**
	 * Send notification email
	 * 
	 * @param clazz
	 * @param userId
	 * @param fromEmailAddress
	 * @param toEmailAddress
	 * @param subject
	 * @param body
	 * @param priority
	 * @param level
	 * @param requestAck
	 * @param tags
	 * @param token
	 */
	@SuppressWarnings("rawtypes")
	public void sendEmail(Class clazz, Long userId, String fromEmailAddress, String toEmailAddress, String subject,
			String body, String priority, String level, boolean requestAck, String tags, String token) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			CoefficientLogger.log(CoefficientLoggerLevel.INFO, "NOTIFICATION_URL = " + NOTIFICATION_URL, clazz);
			String url = NOTIFICATION_URL;
			String contentType = "application/json";

			CoefficientLogger.log(CoefficientLoggerLevel.INFO, "Sending notification email to " + url, clazz);
			HttpPost post = new HttpPost(url + userId);
			StringEntity entity = new StringEntity("{ \"media\":\"EMAIL\", \"metadata\":{ \"fromUser\" : { \"id\" : \""
					+ userId + "\", " + "\"emailAddress\" : \"" + fromEmailAddress + "\" }, \"subject\" : \"" + subject
					+ "\", " + "\"body\" : \"" + body + "\", " + "\"priority\" : \"" + priority + "\", \"level\" : \""
					+ level + "\", " + "\"requestAck\" : " + requestAck + "," + "\"toUserList\" : [ {\"id\" : \""
					+ userId + "\"," + "\"emailAddress\" : \"" + toEmailAddress + "\"} ] }, \"tags\" : \"" + tags
					+ "\", \"token\" : \"" + token + "\" }");

			post.addHeader("content-type", contentType);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			CoefficientLogger.log(CoefficientLoggerLevel.INFO,
					"HttpResponse Code: " + response.getStatusLine().getStatusCode(), clazz);
		} catch (Exception e) {
			CoefficientLogger.log(CoefficientLoggerLevel.ERROR,
					"Failed to send notification Email. Reason: " + e.getMessage(), clazz);
		}
	}

	/**
	 * Send notification alert
	 * 
	 * @param clazz
	 * @param userId
	 * @param message
	 * @param timestamp
	 * @param tags
	 * @param token
	 */
	@SuppressWarnings("rawtypes")
	public void sendNotificationAlert(Class clazz, Long userId, String message, String timestamp, String tags,
			String token) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			String url = NOTIFICATION_URL;
			String contentType = "application/json";

			CoefficientLogger.log(CoefficientLoggerLevel.INFO, "Sending notification email to " + url, clazz);
			HttpPost post = new HttpPost(url + userId);
			StringEntity entity = new StringEntity("{ \"media\":\"NOTIFICATION\", \"metadata\":{ \"message\" : \""
					+ message + "\", " + "\"timestamp\" : \"" + timestamp + "\" }, \"tags\" : \"" + tags
					+ "\", \"token\" : \"" + token + "\" }");

			post.addHeader("content-type", contentType);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			CoefficientLogger.log(CoefficientLoggerLevel.INFO,
					"HttpResponse Code: " + response.getStatusLine().getStatusCode(), clazz);
		} catch (Exception e) {
			CoefficientLogger.log(CoefficientLoggerLevel.ERROR,
					"Failed to send notification Email. Reason: " + e.getMessage(), clazz);
		}
	}
}
