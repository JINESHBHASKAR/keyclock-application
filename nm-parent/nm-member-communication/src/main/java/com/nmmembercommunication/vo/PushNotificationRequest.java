package com.nmmembercommunication.vo;

import java.io.Serializable;
import java.util.List;

public class PushNotificationRequest implements Serializable{
	
	private static final long serialVersionUID = 6476386725709508588L;
	private String title;
    private String message;
    private String topic;
    private String token;
    private List<String> tokenList;
    
    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }
    
    public PushNotificationRequest(String title, String message, List<String> tokenList) {
		super();
		this.title = title;
		this.message = message;
		this.tokenList = tokenList;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public List<String> getTokenList() {
		return tokenList;
	}

	public void setTokenList(List<String> tokenList) {
		this.tokenList = tokenList;
	}
    
}
