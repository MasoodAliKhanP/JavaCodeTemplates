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
public class PersonBasicInfo {
	private String personGivenName;
	private String personSurname;
	private String personCitizenship;
	private String personBirthDate;
	private String personCode;
	private boolean personIsPoliticallyExposed;
}
