package com.mydomain.auth.provider.dto;

import com.mydomain.auth.provider.user.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RecordDto implements UserDetails {

	@Getter
	@Setter
	@JsonProperty("id")
	private String id;

//	@Getter
//	@Setter
//	private String organizationId;

	@Getter
	@Setter
	@JsonIgnore
	@JsonProperty("user_email")
	private String username;

	@Getter
	@Setter
	@JsonProperty("user_full_name")
	private String fullName;

	@Getter
	@Setter
	@JsonProperty("user_email")
	private String email;

	@Getter
	@Setter
	@JsonProperty("user_first_name")
	private String firstName;

	@Getter
	@Setter
	@JsonProperty("user_last_name")
	private String lastName;

	@Getter
	@Setter
	@JsonProperty("user_phone_number")
	private String phoneNumber;

	@Getter
	@Setter
	private boolean enabled;

	@Getter
	@Setter
	@JsonProperty("user_session_id")
	private String sessionId;

	@Setter
	@JsonProperty("user_roles")
	private Set<String> roles = new HashSet<>();

	public Set<String> getRoles() {
		return roles.stream().collect(Collectors.toSet());
	}

	@Getter
	@Setter
	@JsonProperty("user_additional_info")
	private String additionalInfo;
}
