package com.mycompany.auth.provider.dto;

public class AdditionalInfoDto {
	private boolean actionableContent;
	private boolean mipIndicative;

	public boolean isActionableContent() {
		return actionableContent;
	}

	public void setActionableContent(boolean actionableContent) {
		this.actionableContent = actionableContent;
	}

	public boolean isMipIndicative() {
		return mipIndicative;
	}

	public void setMipIndicative(boolean mipIndicative) {
		this.mipIndicative = mipIndicative;
	}

}
