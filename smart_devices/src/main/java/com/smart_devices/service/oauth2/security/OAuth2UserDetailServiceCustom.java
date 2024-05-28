package com.smart_devices.service.oauth2.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.smart_devices.exception.BaseException;
import com.smart_devices.model.User;
import com.smart_devices.repository.RoleRepository;
import com.smart_devices.repository.UserRepository;
import com.smart_devices.service.oauth2.OAuth2UserDetailFactory;
import com.smart_devices.service.oauth2.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserDetailServiceCustom extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			return checkingOAuth2User(userRequest, oAuth2User);
		} catch (AuthenticationException e) {
			// TODO: handle exception
			throw e;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User checkingOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2UserDetails oAuth2UserDetails = OAuth2UserDetailFactory.getAuth2UserDetails(
				userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

		if (ObjectUtils.isEmpty(oAuth2UserDetails)) {
			throw new BaseException("400", "can't found oauth2 user from properties");
		}

		Optional<User> user = userRepository.findByEmailAndProviderId(oAuth2UserDetails.getEmail(),
				userRequest.getClientRegistration().getRegistrationId());

		User userDetail;

		if (user.isPresent()) {
			userDetail = user.get();

			if (!userDetail.getProviderId().equals(userRequest.getClientRegistration().getRegistrationId())) {
				throw new BaseException("400", "Invalid site login with " + userDetail.getProviderId());
			}

			userDetail = updateOAuth2UserDetail(userDetail, oAuth2UserDetails);
		} else {
			userDetail = registerNewOAuth2UserDetail(userRequest, oAuth2UserDetails);
		}

		return new OAuth2UserDetailCustom(
				userDetail.getId(),
				userDetail.getUsername(),
				userDetail.getPassword(),
				userDetail.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
	}

	public User registerNewOAuth2UserDetail(OAuth2UserRequest userRequest, OAuth2UserDetails oAuth2UserDetails) {
		User user = new User();
		user.setUsername(oAuth2UserDetails.getName());
		user.setEmail(oAuth2UserDetails.getEmail());
		user.setProviderId(userRequest.getClientRegistration().getRegistrationId());
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setRoles(new HashSet<>());
		user.getRoles().add(roleRepository.findByName("USER"));
		
		return userRepository.save(user);
	}

	public User updateOAuth2UserDetail(User user, OAuth2UserDetails oAuth2UserDetails) {
		user.setUsername(oAuth2UserDetails.getEmail());
		return userRepository.save(user);
	}

}
