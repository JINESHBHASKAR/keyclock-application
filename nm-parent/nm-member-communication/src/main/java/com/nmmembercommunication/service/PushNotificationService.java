package com.nmmembercommunication.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.nmmembercommunication.config.firebase.NotificationParameter;
import com.nmmembercommunication.vo.PushNotificationRequest;

@Service
public class PushNotificationService {
	private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);
	
	public void sendNotificationAndDataMessage(PushNotificationRequest request) throws InterruptedException, ExecutionException, FirebaseMessagingException {
		log.info("inside sendNotification method - PushNotificationService");		
		Notification notification = new Notification(request.getTitle(),request.getMessage());
		
		Map<String, String> data = new HashMap<>();
		data.put("title", request.getTitle());
		data.put("message", request.getMessage());
		
		AndroidConfig androidConfig = AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(request.getTopic())
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(request.getTopic()).build()).build();
		List<String> tokens = request.getTokenList();
		
		MulticastMessage multicastMessage = MulticastMessage.builder()
											.setNotification(notification)
											.putAllData(data)
											.setAndroidConfig(androidConfig)
											.addAllTokens(tokens)											
											.build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);								
        log.info(response.getSuccessCount() + " messages were sent successfully");
        if (response.getFailureCount() > 0) {
        	List<SendResponse> responses = response.getResponses();
        	List<String> failedTokens = new ArrayList<>();
        	for (int i = 0; i < responses.size(); i++) {
        	    if (!responses.get(i).isSuccessful()) {
        	      // The order of responses corresponds to the order of the registration tokens.
        	      failedTokens.add(request.getTokenList().get(i));
        	    }
        	 }
        	log.info("List of tokens that caused failures on sending notification: " + failedTokens);
        }
	}	
	
	
}
