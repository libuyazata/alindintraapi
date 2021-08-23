package com.yaz.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


public class JwtAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {

//	@Autowired 
//	UserService userService;

	public JwtAuthenticationFilter() {
		
		super("/**");
		System.out.println("JwtAuthenticationFilter,constructor ");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+request.getRequestURI());
		System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+request.getQueryString());
		System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+request.getContextPath());
		System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+request.getHeaderNames());
		Enumeration<String> enums = request.getHeaderNames();
		while(enums.hasMoreElements()){
			String param = enums.nextElement();
			System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+param);
		}
		String referer = request.getHeader("referer");
		String header = request.getHeader("Authorization");
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue()[0];
			System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+name + "  ****   " + value);
			// System.out.println(request.getParameterNames());
		}
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			System.out.println(parameterName);
		}
		System.out.println(request.getParameterNames());
		if (header == null || !header.startsWith("Basic ")) {
			// throw new JwtTokenMissingException("No JWT token found in request headers");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			//email="kris@gnail2.com";
			//password="12345";
			System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+"email: "+email);
			System.out.println("JwtAuthenticationFilter,attemptAuthentication,"+"password: "+password);
//			User user = userService.getUserByEmail(email);


//			return new UsernamePasswordAuthenticationToken(user, user.getPassword());
			return new UsernamePasswordAuthenticationToken("", "Test-user");
		}

		String authToken = header.substring(6);

		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

		return getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}

}
