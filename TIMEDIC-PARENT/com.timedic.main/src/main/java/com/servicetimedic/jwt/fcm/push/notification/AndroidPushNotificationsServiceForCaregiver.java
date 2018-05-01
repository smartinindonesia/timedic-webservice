package com.servicetimedic.jwt.fcm.push.notification;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsServiceForCaregiver {

	private static final String FIREBASE_SERVER_KEY_CAREGIVER = "AAAAIDuKbZs:APA91bGY2dikOX7hF7evPmX1PDr90h1GNvhXZ4GmskdRbzzeA_uclgl1h8lbg0cnevIQPX-s2Ek0KN83onpWtKMmpkoTuag5yVtxBc-3wYXy7DJ0KysER_Mu-cdUdJL54nXZCeHF4-7a";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY_CAREGIVER));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
