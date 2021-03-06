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
public class AddressBasicInfo {
	private String country;
	private String houseNo;
	private String street;
	private String postalCode;
	private String settlement;
}
