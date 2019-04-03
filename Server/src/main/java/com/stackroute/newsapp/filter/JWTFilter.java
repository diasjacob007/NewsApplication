package com.stackroute.newsapp.filter;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * This is the filter class through which all requests must pass
 * and generating the claims_01.26.2019
 */
public class JWTFilter extends GenericFilterBean {
	final String SECRET1 = Base64.getEncoder().encodeToString("ThisIsASecret".getBytes());
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;
		final String authHeader = req.getHeader("authorization");
		if ("OPTIONS".equals(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
		} else {
			String claimsSubject="";
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing or invalid Authorization Header");
			}
			final String token = authHeader.substring(7);
			try {
				claimsSubject = Jwts.parser().setSigningKey(SECRET1).parseClaimsJws(token).getBody().getSubject();
				String[] claimsArry = claimsSubject.split(":");
				String claims = claimsArry[0].toString();
				System.out.println("*****Claims are : " + claims.toString());
				request.setAttribute("claims", claims);
				chain.doFilter(request, response);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
			} catch (UnsupportedJwtException e) {
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				e.printStackTrace();
			} catch (SignatureException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			
		}
	}
}
