package com.servicetimedic.jwt.fcm.push.notification;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsServiceForUser {

	private static final String FIREBASE_SERVER_KEY_USER = "AAAArGD9xrA:APA91bGEZWZlHS3ZPi33PPRVmOxAlWNQYoW_GIq2uwbvrm9dppM80DoViPw7nmwVBx8YdnwD_EOv3g0MKLP7V2jRpT1prlbITRrgaga6nsKcubHZg0v0E6oQ2zUMce7XxvE8QNPnliQb";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY_USER));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
