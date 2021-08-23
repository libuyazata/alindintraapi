package com.yaz.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/*private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private AuthenticationEntryPoint defaultEntryPoint;*/
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		System.out.println("CustomAuthenticationEntryPoint,Entery: "+request.getRequestURL());
		 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		/*// TODO Auto-generated method stub
		System.out.println("Entery: "+request.getRequestURL());
		request.getSession().setAttribute("targetUrl",request.getRequestURL());
	//	redirectStrategy.sendRedirect(request,response,request.getRequestURL().toString());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");*/
	}
	/*public void setDefaultEntryPoint(AuthenticationEntryPoint defaultEntryPoint) {
		this.defaultEntryPoint = defaultEntryPoint;
	}*/

}
