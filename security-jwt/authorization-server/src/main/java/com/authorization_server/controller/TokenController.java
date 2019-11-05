package com.authorization_server.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	@Resource(name = "tokenStore")
	private TokenStore tokenStore;

	@PostMapping("/oauth/token/revokeById/{tokenId}")
	@ResponseBody
	public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
		tokenServices.revokeToken(tokenId);
	}

	@GetMapping("/tokens")
	@ResponseBody
	public List<String> getTokens() {
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("client");
		return Optional.ofNullable(tokens).orElse(Collections.emptyList()).stream().map(OAuth2AccessToken::getValue)
				.collect(Collectors.toList());
	}

	@PostMapping("/tokens/revokeRefreshToken/{tokenId:.*}")
	@ResponseBody
	public String revokeRefreshToken(@PathVariable String tokenId) {
		if (tokenStore instanceof JdbcTokenStore) {
			((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
		}
		return tokenId;
	}

}
