package com.yaz.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;


@Component
public class CustomCORSFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CustomCORSFilter.class);

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		logger.info("CustomCORSFilter,doFilter,FIltering Request with CustomCORSFilter");
		System.out.println("CustomCORSFilter,doFilter");
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    Enumeration<String> headerNames = httpRequest.getHeaderNames();
	    System.out.println("CustomCORSFilter,doFilter, header: "+httpRequest.getHeader("token")
	    		+",empCode: "+req.getParameter("empCode"));
	    
//	    StringBuffer jb = new StringBuffer();
//        String line = null;
//        try {
//            BufferedReader reader = request.getReader();
//            while ((line = reader.readLine()) != null) {
//                jb.append(line);
//            }
//        } catch (Exception e) {
//        }
//        System.out.println("CustomCORSFilter,doFilter, jSon:"+line);
//        try {
////            JSONObject jsonObject = HTTP.toJSONObject(jb.toString());
////            String email = jsonObject.getString("email");
//        } catch (Exception e) {
//        }
//	    if (headerNames != null) {
//	        while (headerNames.hasMoreElements()) {
//	            String name = headerNames.nextElement();
//	            System.out.println("doFilter,Header: " + name + " value:" + httpRequest.getHeader(name));
//	        }
//	    }
//		System.out.println("CustomCORSFilter,doFilter, header: "+httpRequest.getHeaders("token").toString());
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
		chain.doFilter(req, res);
	}
	
	public void init(FilterConfig filterConfig) {
		System.out.println("CustomCORSFilter,init");
	}

	public void destroy() {
	}
}
