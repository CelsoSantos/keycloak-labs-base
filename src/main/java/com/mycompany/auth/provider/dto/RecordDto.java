package com.mycompany.auth.provider.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecordDto {

	private String userAdditionalInfo;
	private String userFirstName;
	private String userLastName;
	private String userFullName;
	private Optional<String> userPhoneNumber;
	private String userEmail;
	private String id;
	private List<String> userRoles = new ArrayList<>();

	@JsonProperty("id")
	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

	@JsonProperty("user_first_name")
	public String getUserFirstName() { return userFirstName; }

	public void setUserFirstName(String firstName) { this.userFirstName = firstName; }

	@JsonProperty("user_last_name")
	public String getUserLastName() { return userLastName; }

	public void setUserLastName(String lastName) { this.userLastName = lastName; }

	@JsonProperty("user_full_name")
	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

	@JsonProperty("user_phone_number")
	public Optional<String> getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = Optional.of(userPhoneNumber);
	}

	@JsonProperty("user_email")
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty("user_roles")
	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	@JsonProperty("user_additional_info")
	public String getUserAdditionalInfo() {
		return userAdditionalInfo;
	}

	public void setUserAdditionalInfo(String userAdditionalInfo) {
		this.userAdditionalInfo = userAdditionalInfo;
	}

}
