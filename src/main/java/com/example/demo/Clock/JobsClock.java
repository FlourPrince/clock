package com.example.demo.Clock;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The <code>com.example.demo.main.Jobs</code> class have to be described
 * <p>
 * The <code>Jobs</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/16 19:31
 * @see
 * @since 1.0
 */

public class JobsClock {

	@Autowired
	private  ClockUser user;

	public  String loadOn() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "http://10.20.251.35:8081/orm/user/login";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("Connection", "keep-alive");
		String str = "{\"username\":\"" + user.getUserName() + "\",\"password\":\"" + user.getPassWord() + "\"}";
		httpPost.setEntity(
				new StringEntity(str, "UTF-8"));
		CloseableHttpResponse response = null;
		String responseResult = "";
		String token = "";
		try {
			response = httpClient.execute(httpPost);
			responseResult = EntityUtils.toString(response.getEntity());
			LoadResult loadResult = JSON.parseObject(responseResult, LoadResult.class);
			token = loadResult.getData().getToken();
			response.close();
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return token;
	}

	@Scheduled(cron = "0 0 20 * * ?")
	public void clockOut() {
		String token = loadOn();
		String url = "http://10.20.251.35:8081/orm/attendance-record/clockOut";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Host", "10.20.251.35:8081");
		httpPost.setHeader("Origin", "http://10.20.251.35:8080");
		httpPost.setHeader("Referer", "http://10.20.251.35:8080/clock/cIndex");
		httpPost.setHeader("token", token);
		CloseableHttpResponse response = null;
		String responseResult = "";
		try {
			response = httpClient.execute(httpPost);
			responseResult = EntityUtils.toString(response.getEntity());
			System.out.println(responseResult);
			response.close();
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	//@Scheduled(cron = "0 0 8 * * ?")
	@Scheduled(cron = "* * * * * ?")
	public void clockIn() {
		String token = loadOn();
		String url = "http://10.20.251.35:8081/orm/attendance-record/clockIn";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Host", "10.20.251.35:8081");
		httpPost.setHeader("Origin", "http://10.20.251.35:8080");
		httpPost.setHeader("Referer", "http://10.20.251.35:8080/clock/cIndex");
		httpPost.setHeader("token", token);
		CloseableHttpResponse response = null;
		String responseResult = "";
		try {
			response = httpClient.execute(httpPost);
			responseResult = EntityUtils.toString(response.getEntity());
			System.out.println(responseResult);
			response.close();
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
