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
public class DocumentBasicInfo {
	private String idType;
	private String idNumber;
	private String idIssuingCountry;
	private String idIssuePlace;
	private String idIssueDate;
	private String idExpiryDate;
}
