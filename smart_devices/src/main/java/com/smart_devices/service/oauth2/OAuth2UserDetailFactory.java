package com.smart_devices.service.oauth2;

import java.util.Map;

import com.smart_devices.enums.Provider;
import com.smart_devices.exception.BaseException;


public class OAuth2UserDetailFactory {
	
	public static OAuth2UserDetails getAuth2UserDetails(String registrantionId, Map<String ,Object> attributes) {
		if(registrantionId.equals(Provider.google.name())) {
			return new OAuth2GoogleUser(attributes);
		} else if(registrantionId.equals(Provider.facebook.name())) {
			return new OAuth2FacebookUser(attributes);
		} else {
			throw new BaseException("400", "Sorry! Loggin with " + registrantionId + "is not supported!");
		}
	}
}
