package com.Jin.blog.model;

import lombok.Data;

@Data	// @Getter, Setter 을 자동으로 등록해줌
public class OAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
