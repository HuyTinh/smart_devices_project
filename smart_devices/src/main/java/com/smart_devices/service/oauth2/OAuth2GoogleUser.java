package com.smart_devices.service.oauth2;

import java.util.Map;

public class OAuth2GoogleUser extends OAuth2UserDetails {

	public OAuth2GoogleUser(Map<String, Object> attributes) {
		super(attributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String) attributes.get("name");
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String) attributes.get("email");
	}
	
	

}
