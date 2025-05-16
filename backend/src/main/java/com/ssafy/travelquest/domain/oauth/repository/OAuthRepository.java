package com.ssafy.travelquest.domain.oauth.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelquest.domain.oauth.entity.OAuthIdentity;
import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;


@Mapper
public interface OAuthRepository {
	void create(OAuthIdentity oAuthIdentity);
	Optional<OAuthIdentity> findWithUserBySubAndProvider(String sub, OAuthProvider provider);
}
