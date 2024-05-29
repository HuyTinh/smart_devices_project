package com.smart_devices.service.oauth2;

import java.util.Map;

public class OAuth2FacebookUser extends OAuth2UserDetails {

	public OAuth2FacebookUser(Map<String, Object> attributes) {
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
		return null;
	}

	@Override
	public String getFirstName() {
		return null;
	}
	
	@Override
	public String getLastName() {
		return null;
	}
}
