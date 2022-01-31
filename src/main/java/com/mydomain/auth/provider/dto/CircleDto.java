package com.mydomain.auth.provider.dto;

import lombok.Getter;
import lombok.Setter;

public class CircleDto {

	@Getter
	@Setter
	private boolean actionableContent;

	@Getter
	@Setter
	private boolean mipIndicative;

	@Getter
	@Setter
	private boolean mipFull;

	@Getter
	@Setter
	private boolean lcmBasic;

	@Getter
	@Setter
	private boolean lcmFull;

}
