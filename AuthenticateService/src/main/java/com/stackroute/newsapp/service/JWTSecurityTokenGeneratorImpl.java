package com.stackroute.newsapp.service;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stackroute.newsapp.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * This is the service class for implementing the jwt token logic
 * corresponding to each loggined users_01.26.2019
 */
@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {

	@Override
	public Map<String, String> generateToken(User user) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		final String SECRET = Base64.getEncoder().encodeToString("ThisIsASecret".getBytes());
		Map<String, Object> headerClaims = new LinkedHashMap<>();
		headerClaims.put("alg", "HS256");
		headerClaims.put("typ", "JWT");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.MINUTE, 144000); // 144000 minutes = 100 days
		Date expirationDate = c.getTime();
		String jwtToken = "";
		//Logic for setting UserRole along with UserId in the jwtToken
		String jwtTokenSubject = user.getUserId()+":"+user.getUserRole();
		jwtToken = Jwts.builder().setSubject(jwtTokenSubject).setIssuedAt(now).setExpiration(expirationDate)
				.setHeader(headerClaims).signWith(signatureAlgorithm, SECRET).compact();
		Map<String, String> map = new HashMap<>();
		map.put("token", jwtToken);
		map.put("message", "User successfully loggined in");
		return map;
	}
}
