package com.connectpay.nano.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BasicInfo {
	private String fullName;
	private String registrationNumber;
	private String taxIdentificationNumber;
	private String registrationCountry;
	private String registrationDate;
	private String businessCategory;
}
