package com.boilerplate.api;

import com.boilerplate.entity.OAuthClient;

public interface IOAuthClientApi {
	public OAuthClient registerClient(String web_server_redirect_uri);
}
