package com.yaz.alind.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name="alind_t_employees")
@NamedQuery(name="EmployeeEntity.findAll", query="SELECT e FROM EmployeeEntity e")
public class EmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="employee_id", unique = true, nullable = false)
	private int employeeId;

	@Column(name="created_at")
	private Timestamp createdAt;
//	private Date createdAt;
	
	@Column(name="date_of_join")
	private Timestamp dateOfJoin;

	@Column(name="email_id")
	private String emailId;

	@Column(name="emp_code")
	private String empCode;

	@Column(name="first_name")
	private String firstName;

	@Column(name="gender")
	private String gender;

	@Column(name="is_active")
	private int isActive;

	@Column(name="last_name")
	private String lastName;

	@Column(name="primary_mobile_no")
	private String primaryMobileNo;

	@Column(name="secondary_email_id")
	private String secondaryEmailId;

	@Column(name="secondary_mobile_no")
	private String secondaryMobileNo;

	@Column(name="updated_at")
	private Timestamp updatedAt;
//	private Date updatedAt;

	@Column(name="user_name")
	private String userName;

	@Column(name="password")
	private String password;
	
	@Column(name = "empolyee_type_id")
	private int empolyeeTypeId;
	
	@ManyToOne
	@JoinColumn(name="empolyee_type_id",insertable = false, updatable = false)
	private EmployeeTypesEntity employeeTypes;
	
	@Column(name = "last_working_day")
	private Date lastWorkingDay;
	@Column(name = "nationality")
	private String nationality;
	@Column(name = "passport_no")
	private String passportNo;
	@Column(name = "accommodation_location")
	private String accommodationLocation;
	@Column(name = "health_card_validity")
	private Date healthCardValidity;
	@Column(name = "health_card_no")
	private String healthCardNo;
	@Column(name = "insurance_policy_no")
	private String insurancePolicyNo;
	@Column(name = "insurance_validity ")
	private Date insuranceValidity ;
	
	@Column(name = "native_address")
	private String nativeAddress;
	@Column(name = "emergency_contact_name")
	private String emergencyContactName ;
	@Column(name = "emergency_contact_phone")
	private String emergencyContactPhone;
	@Column(name = "relationship")
	private String relationship;
	@Column(name = "other_details")
	private String otherDetails;
	
	
	@Column(name = "department_id")
	private int departmentId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;

	@Column(name = "user_role_id")
	private int userRoleId;
	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="user_role_id",insertable = false, updatable = false)
	private UserRolesEntity usrRole;
	
	//bi-directional many-to-one association to Upload
//	@ManyToOne
//	@JoinColumn(name="upload_id")
//	private Upload upload;
	@Column(name="upload_id")
	private int uploadId;
	@Column(name="profile_pic_path")
	private String profilePicPath;
	@Column(name="orginal_profile_pic_name")
	private String orginalProfilePicName;
	
	@Transient
	private String token;
		
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public EmployeeEntity() {
	}

	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPrimaryMobileNo() {
		return this.primaryMobileNo;
	}

	public void setPrimaryMobileNo(String primaryMobileNo) {
		this.primaryMobileNo = primaryMobileNo;
	}


	public String getSecondaryEmailId() {
		return this.secondaryEmailId;
	}

	public void setSecondaryEmailId(String secondaryEmailId) {
		this.secondaryEmailId = secondaryEmailId;
	}

	public String getSecondaryMobileNo() {
		return this.secondaryMobileNo;
	}

	public void setSecondaryMobileNo(String secondaryMobileNo) {
		this.secondaryMobileNo = secondaryMobileNo;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastWorkingDay() {
		return lastWorkingDay;
	}

	public void setLastWorkingDay(Date lastWorkingDay) {
		this.lastWorkingDay = lastWorkingDay;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getAccommodationLocation() {
		return accommodationLocation;
	}

	public void setAccommodationLocation(String accommodationLocation) {
		this.accommodationLocation = accommodationLocation;
	}

	public Date getHealthCardValidity() {
		return healthCardValidity;
	}

	public void setHealthCardValidity(Date healthCardValidity) {
		this.healthCardValidity = healthCardValidity;
	}

	public String getHealthCardNo() {
		return healthCardNo;
	}

	public void setHealthCardNo(String healthCardNo) {
		this.healthCardNo = healthCardNo;
	}

	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}

	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}

	public Date getInsuranceValidity() {
		return insuranceValidity;
	}

	public void setInsuranceValidity(Date insuranceValidity) {
		this.insuranceValidity = insuranceValidity;
	}

	public String getNativeAddress() {
		return nativeAddress;
	}

	public void setNativeAddress(String nativeAddress) {
		this.nativeAddress = nativeAddress;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public int getUploadId() {
		return uploadId;
	}

	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}

	public EmployeeTypesEntity getEmployeeTypes() {
		return employeeTypes;
	}

	public void setEmployeeTypes(EmployeeTypesEntity employeeTypes) {
		this.employeeTypes = employeeTypes;
	}

	public UserRolesEntity getUsrRole() {
		return usrRole;
	}

	public void setUsrRole(UserRolesEntity usrRole) {
		this.usrRole = usrRole;
	}

	public int getEmpolyeeTypeId() {
		return empolyeeTypeId;
	}

	public void setEmpolyeeTypeId(int empolyeeTypeId) {
		this.empolyeeTypeId = empolyeeTypeId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public String getOrginalProfilePicName() {
		return orginalProfilePicName;
	}

	public void setOrginalProfilePicName(String orginalProfilePicName) {
		this.orginalProfilePicName = orginalProfilePicName;
	}

	public Timestamp getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(Timestamp dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}
	
	

}