package com.yaz.alind.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.util.Iconstants;

@Service
public class UtilServiceImpl implements UtilService{

	private static final Logger logger = LoggerFactory.getLogger(UtilServiceImpl.class);

	@Autowired
	UserDAO userDAO;


	@Override
	public String createToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 *  Returns, Date with Time
	 */
	@Override
	public Date getCurrentDateTime() {

		return new Date(System.currentTimeMillis());
	}
	
	@Override
	public Timestamp getCurrentDateTimeStamp(){
		Timestamp timestamp = null;
		try{
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateStr = format.format(date);
		date = format.parse(dateStr);
		timestamp = new Timestamp(date.getTime());
        System.out.println("Current Time Stamp: "+timestamp);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCurrentDateTimeStamp: "+e.getMessage());
		}
        return timestamp;
	}

	@Override
	public boolean evaluateSessionTime(Date startDate, Date endDate) {
		boolean status = false;
		try{
			int dateDifference = endDate.getDate() - startDate.getDate();
			if(dateDifference == 0){
				long timeDifference = endDate.getTime() - startDate.getTime();
				long diffMinutes = timeDifference / (60 * 1000);   
				//				System.out.println("UtilserviceImpl, evaluateSessionTime, timeDifference: "+timeDifference+",diffMinutes: "+diffMinutes+", session time: "+Iconstants.SESSION_TIME);
				if(diffMinutes <= Iconstants.SESSION_TIME){
					status = true;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("evaluateSessionTime: "+e.getMessage());
		}

		return status;
	}

	@Override
	public boolean evaluateToken(String token) {
		//		boolean status = true;
		boolean status = false;
		try{
			TokenEntity tokenModel = userDAO.getTokenModelByToken(token);
			if(tokenModel != null){
				if(token.equals(tokenModel.getToken())){
					boolean timeStatus = evaluateSessionTime(tokenModel.getDateTime(), getCurrentDateTime());
					if(timeStatus){
						status = true;
						// Updating session time
						tokenModel.setDateTime(getCurrentDateTime());
						userDAO.saveOrUpdateToken(tokenModel);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("evaluateToken: "+e.getMessage());
		}
		return status;
	}

	@Override
	public Date getTodaysDate(){
		Date date = null;
		try{
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			date = new Date();
			String dateStr = format.format(date);
			DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			date = fmt.parse(dateStr);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getTodaysDate,"+e.getMessage());
		}
		return date;
	}

	@Override
	public Date getPreviousYearDate(Date date) {
		Date previousDate = null;
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.YEAR, -1);
			previousDate = cal.getTime();
			//			System.out.println("UtilImpl,getPreviousYearDate: "+previousDate);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getPreviousYearDate,"+e.getMessage());
		}
		return previousDate;
	}

	@Override
	public Date getDateFromString(String dateStr) {
		Date date = null;
		try{
			//			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//			System.out.println("getDateFromString,dateStr: "+dateStr);
			if(! dateStr.equals("") || date != null ){
				date = format.parse(dateStr);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDateFromString,"+e.getMessage());
		}
		return date;
	}
	
	@Override
	public Timestamp stringToTimestamp(String strDate){
		String pattern = "yyyy-MM-dd HH:mm:ss.S";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(strDate));
		Timestamp stamp = Timestamp.valueOf(localDateTime);
		return stamp;
	}

	@Override
	public Timestamp dateToTimestamp(Date date) {
		Timestamp timestamp = null;
		try{
			timestamp = new Timestamp(date.getTime());  

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDateFromString,"+e.getMessage());
		}

		return timestamp;
	}

	@Override
	public int saveFile(MultipartFile mulFile,String contextPath,String fileLocation) {
		int val = -1;
		String destination = null;
		try{
			String fileName = createFileName(mulFile.getOriginalFilename());
			//			System.out.println("Util Business,saveFile,fileName: "+fileName+", fileLocation: "+fileLocation);

			String[] arrOfStr = contextPath.split(Iconstants.BUILD_NAME, 2); 
			String path = arrOfStr[0]+fileLocation;
			//			System.out.println("Util Business,saveFile,path: "+path+", folder name: "+arrOfStr[0]+path);
			File folderName = new File(arrOfStr[0]+path);
			if(! folderName.exists()){
				new File(path).mkdirs();
			}
			destination = path+"/"+ fileName;
			System.out.println("Util Business,saveFile,destination: "+destination);
			File file = new File(destination);
			mulFile.transferTo(file);
			val = 1;
		}catch(Exception e){
			val = -1;
			e.printStackTrace();
			logger.error("saveFile,"+e.getMessage());
		}
		return val;
	}

	@Override
	public String createFileName(String existingName) {
		String fileName = null;
		try{
			String fileExtension = FilenameUtils.getExtension(existingName);
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			LocalTime localTime=LocalTime.now(zoneId);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// convert to date
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date date = cal.getTime();
			String dateStr = dateFormat.format(date);
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
			String formattedTime=localTime.format(formatter);
			fileName = dateStr+formattedTime+"."+fileExtension;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createFileName: "+e.getMessage());
		}
		return fileName;
	}

	@Override
	public boolean isRightFileType(String fileType) {
		boolean type = false;
		try{
			switch (fileType) {
			case "application/pdf":
			case "image/bmp":
			case "image/gif":
			case "image/jpeg":
			case "image/tiff":
			case "image/png":
			case "text/plain":
			case "text/rtf":
			case "application/msword":
			case "application/vnd.ms-excel":
				type = true;
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("isRightFileType: "+e.getMessage());
		}
		return type;
	}

	/**
	 *  Nomenclature 
	 * @param drawingSeries
	 * @param lastDocumentNo
	 * @return eg: 600 20XXX
	 */
	@Override
	public String createDocumentNumber(String drawingSeries,
			String lastDocumentNo) {
		String newNo = null;
		try{
			System.out.println("Utill Business,createDocumentNumber,drawingSeries:"+drawingSeries
					+", lastDocumentNo: "+lastDocumentNo);
			Date today = Calendar.getInstance().getTime();
			String currentYear = Integer.toString(getYearByDate(today));
			String year = null;
			String actualNo = null;
			if(lastDocumentNo != null){
				//				year = lastDocumentNo.substring(0,3);
				//				actualNo = lastDocumentNo.substring(3,lastDocumentNo.length()).;
				String[] strColl = lastDocumentNo.split(" ");
				year = strColl[0];
				//				actualNo = strColl[1];
				actualNo = strColl[1].substring(2,strColl[1].length());
			}else{
				year = currentYear.substring(0,2);
				actualNo = "100";
			}
			//			System.out.println("Year : "+year+", actualNo: "+actualNo);

			int actualIncr = (Integer.parseInt(actualNo)+1);
			if(year.equals(currentYear.substring(2, 4))){
				newNo = drawingSeries +" "+ year + Integer.toString(actualIncr);
			}else{
				newNo = drawingSeries +" "+ currentYear.substring(2,4) + Integer.toString(actualIncr);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDocumentNumber: "+e.getMessage());
		}
		return newNo;
	}

	@Override
	public int getYearByDate(Date date) {
		int year = -1;
		try{
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			year = localDate.getYear();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getYearByDate: "+e.getMessage());
		}
		return year;
	}

	/**
	 * It works up to length max 2 ( Eg: A & AB, sizes)
	 *  Sequence increment like, A, B, ....AA, AB....up to ZZ
	 * @param soruceStr
	 * @return
	 */
	@Override
	public  String incrementCharSequence( String soruceStr) {
		String str = null;
		int first = 0;
		int next = 0;
		try{
			int lenght = soruceStr.length();
			String[] strColl = soruceStr.split(" ");
			switch (lenght) {
			// If Empty string comes
			case 0:
				//				System.out.println("lenght: "+lenght);
				str = "A";
				break;
			case 1:
				char value = soruceStr.charAt(0);

				int nextValue = (int)value ; // find the int value 
				if(nextValue >= 90){ // whether, "Z", is coming
					str = "AA";
				}else{
					nextValue = (int)value + 1;
					char c = (char)nextValue; // convert that to back to a char
					str =  String.valueOf(c); // print the char as a string
				}

				//			System.out.println(""+str+", nextValue: "+nextValue);
				break;
			case 2:
				first = (soruceStr.charAt(0) - 'A') * 26;
				int second = soruceStr.charAt(1) - 'A';
				next = (first + second + 1) % (26*26); 
				str = new String(new byte[] {(byte)(next / 26 + 'A'), (byte)(next % 26 + 'A')});
				//			System.out.println(""+str+", first: "+first+", next: "+next);
				break;

			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("incrementCharSequence: "+e.getMessage());
		}

		return str;
	}
	/**
	 * lastDocumentNo - 600 20XXX or 600 20XXX AB
	 * Return eg: 600 20XXX A or 600 20XXX AC
	 */
	@Override
	public String updatedDocumentNumber(String lastDocumentNo) {
		String updateSeries = null;
		try{
		//	System.out.println("Util, Business,updatedDocumentNumber, lastDocumentNo: "+lastDocumentNo);
			String[] strArr = lastDocumentNo.split(" ");
			//System.out.println("Util, Business,updatedDocumentNumber, strArr.length: "+strArr.length);
			String lastStr = null;

			if(strArr.length > 2){
				lastStr = strArr[2];
				String incrementCharSequence = incrementCharSequence(lastStr);
				System.out.println("Util, Business,updatedDocumentNumber, incrementCharSequence: "+incrementCharSequence);
				updateSeries = strArr[0]+" "+strArr[1]+" "+incrementCharSequence;
				System.out.println("Util, Business,updatedDocumentNumber, updateSeries: "+updateSeries);
			}else{
				String incrementCharSequence = incrementCharSequence("");
				updateSeries = strArr[0]+" "+strArr[1]+" "+incrementCharSequence;
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDocumentSeriesNumber: "+e.getMessage());
		}
		return updateSeries;
	}

	/**
	 * 
	 * @param date
	 * @return - format "2021-06-29 10:40:36"
	 */
	@Override
	public String dateToString(Date date) {
		String strDate =  null;
		try {
			//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			strDate = dateFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception, Util: "+e.getMessage());
		}

		return strDate;
	}
	
	@Override
	public String timeStampToString(Timestamp timestamp){
		String timeStStr = null;
		try{
			timeStStr =  timestamp.toString();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception, timeStampToString: "+e.getMessage());
		}
		return timeStStr;
	}

	@Override
	public Date getCurrentDate() {
		Date currentDate = new Date();
		return currentDate;
	}

	@Override
	public Date stringToDate(String date ) {
		Date fromattedDate = null;
		try {
			fromattedDate = new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(date).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception, Util: "+e.getMessage());
		} 
		return fromattedDate;
	}

	/**
	 *  the file name for Excel / PDF download
	 */
	@Override
	public String createDownLoadFileName(){
		String fileName = null;
		try{
			//			String fileExtension = FilenameUtils.getExtension(existingName);
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			LocalTime localTime=LocalTime.now(zoneId);
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// convert to date
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date date = cal.getTime();
			String dateStr = dateFormat.format(date);
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
			String formattedTime=localTime.format(formatter);
			fileName = dateStr+formattedTime;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDownLoadFileName: "+e.getMessage());
		}
		return fileName;
	}

	@Override
	public Timestamp stringDateToTimestamp(String dateStr) {
		Timestamp timestamp = null;
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
			Date parsedDate = dateFormat.parse(dateStr);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("stringDateToTimestamp: "+e.getMessage());
		}
		return timestamp;
	}

	@Override
	public Date getFirstDayOfYear(Date date) {
		Date start = null;
		try{
			//			int year=date.getYear(); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			//			Calendar calendar = Calendar.getInstance();
			//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			System.out.println("Utill, getFirstDayOfYear,year: "+year+", date: "+date);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.DAY_OF_YEAR, 1);    
			start = cal.getTime();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getFirstDayOfYear: "+e.getMessage());
		}
		return start;
	}

	@Override
	public Date getLastDayOfYear(Date date) {
		Date end = null;
		try{
			//			int year=date.getYear(); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, 11); // 11 = december
			cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve 
			end = cal.getTime();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getLastDayOfYear: "+e.getMessage());
		}
		return end;
	}

//	@Override
//	public String interComRefNo(InterCommRefNoEntity refNo) {
//		String refNum = null;
//		int number = 0;
//		try{
//			Date today = getCurrentDate();
//			int currentYear = getYearByDate(today);
//			if( currentYear == refNo.getCurrentYear()){
//				number = refNo.getNo() + 1;
//			}else{
//				number = 1;
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error("interComRefNo: "+e.getMessage());
//		}
//		return refNum;
//	}


	//	@Override
	//	public Document getWaterMarkPDF(String fileName, Employee employee) {
	////		Document document = null;
	//		try{
	//			Document document = new Document(PageSize.A4);
	//		}catch(Exception e){
	//			e.printStackTrace();
	//			logger.error("getWaterMarkPDF: "+e.getMessage());
	//		}
	//		return null;
	//	}

}
