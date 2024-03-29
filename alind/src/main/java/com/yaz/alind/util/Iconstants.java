package com.yaz.alind.util;

public interface Iconstants {

	public static final String BEARER_TOKEN= "Bearer ";
	public static final String HEADER= "authorization";
	public static final String ISSUER= "ducat-springboot-jwttoken";
	public static final String SECRET_KEY= "Springbootjwttutorial";
	// The session time in minutes
	public static final int SESSION_TIME = 90;
	
	public final static String BUILD_NAME = "alind";
	
	public final static String EMPLOYEE_DOCUMENT_LOCATION = "AlindUploadFiles/Employee/Doc/";
	public final static String PROJECT_DOCUMENT_LOCATION = "AlindUploadFiles/WorkDetails/";
	public final static String EMPLOYEE_PROFILE_PIC_LOCATION = "AlindUploadFiles/Employee/ProfilePic/";
	public final static String DEFAULT_PROFILE_IMAGE = "AlindUploadFiles/Employee/ProfilePic/default/default.jpg";
	public final static String WORK_MESSAGE_DOCUMENT = "AlindUploadFiles/WorkMessage/";
	public final static String GENERAL_MESSAGE_DOCUMENT = "AlindUploadFiles/GeneralMessage/";
//	public final static String PROJECT_DOCUMENT_LOCATION = "F:/Test/AlindUploadFiles/Project/";
	
	public final static String  ANNEXURE_FORMAT= "PS/ANX/09";

}