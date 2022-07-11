package com.yaz.alind.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.entity.InterCommRefNoEntity;



public interface UtilService {

	public String createToken();
	public Date getCurrentDateTime();
	public boolean evaluateSessionTime(Date loggedDate,Date systemDate);
	public boolean evaluateToken(String token);
	public Date getTodaysDate();
	public Date getFirstDayOfYear(Date date);
	public Date getLastDayOfYear(Date date);
	public Date getPreviousYearDate(Date todaysDate);
	public Date getDateFromString(String dateStr);
	public Timestamp dateToTimestamp(Date date);
	public Timestamp stringDateToTimestamp(String dateStr);
	public int saveFile(MultipartFile mulFile,String contextPath,String fileLocation);
	public String createFileName(String existingName);
	public boolean isRightFileType(String fileType);
	public String createDocumentNumber(String drawingSeries,String lastDocumentNo);
	public int getYearByDate(Date date);
	public  String incrementCharSequence( String soruceStr);
	public String updatedDocumentNumber(String lastDocumentNo);
	public String dateToString(Date date);
	public Date getCurrentDate();
	public Date stringToDate(String date );
	public String createDownLoadFileName();
//	public String interComRefNo(InterCommRefNoEntity refNo);
	
//	public Document getWaterMarkPDF(String fileName,Employee employee);
}
