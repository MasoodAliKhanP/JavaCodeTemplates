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
public class ContactBasicInfo {
	private String language;
	private String mainEmail;
	private String mainPhoneNumber;
	private String mainPhoneType;
}
