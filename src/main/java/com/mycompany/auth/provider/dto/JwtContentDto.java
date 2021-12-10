package com.mycompany.auth.provider.dto;

import java.util.Optional;

public class JwtContentDto {
	private RecordDto userInfo;
	private String circleContent;
	private Optional<String> parentToken;
	
	public Optional<String> getParentToken() {
		return parentToken;
	}
	public void setParentToken(Optional<String> parentToken) {
		this.parentToken = parentToken;
	}
	public RecordDto getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(RecordDto userInfo) {
		this.userInfo = userInfo;
	}
	public String getCircleContent() {
		return circleContent;
	}
	public void setCircleContent(String userInfo) {
		this.circleContent = userInfo;
	}
}