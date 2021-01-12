package com.connectpay.nano.dozer.config;

import static org.dozer.loader.api.FieldsMappingOptions.deepHintA;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.connectpay.dto.Address;
import com.connectpay.dto.Corporate;
import com.connectpay.dto.CorporateContactDetail;
import com.connectpay.dto.CorporateFinancialDetail;
import com.connectpay.dto.Country;
import com.connectpay.dto.Person;
import com.connectpay.dto.PersonContactDetail;
import com.connectpay.nano.bean.AddressBasicInfo;
import com.connectpay.nano.bean.BasicInfo;
import com.connectpay.nano.bean.CorporateAddress;
import com.connectpay.nano.bean.PersonBasicInfo;

@Configuration
public class NanoDozerConfig {

	@Qualifier("nanoDozerBeanMapper")
	@Bean
	public DozerBeanMapper nanoDozerBeanMapper() {
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.addMapping(getCorporateBeanMappingBuilder());
		dozerBean.addMapping(getPersonBeanMappingBuilder());
		dozerBean.addMapping(getCorporateAddressMappingBuilder());
		dozerBean.addMapping(getBasicAddressMappingBuilder());
		dozerBean.addMapping(getPersonAddressMappingBuilder());
		return dozerBean;
	}

	private BeanMappingBuilder getBasicAddressMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(Address.class, AddressBasicInfo.class)
						.fields("country.alpha2", "country", deepHintA(Country.class, String.class))
						.fields("houseNumber", "houseNo").fields("address", "street").fields("postalcode", "postalCode")
						.fields("city", "settlement");

			}
		};
	}

	private BeanMappingBuilder getCorporateAddressMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(CorporateContactDetail.class, CorporateAddress.class)
						.fields("address2.country.alpha2", "residence.country",
								deepHintA(Address.class, Country.class, String.class))
						.fields("address2.houseNumber", "residence.houseNo")
						.fields("address2.address", "residence.street")
						.fields("address2.postalcode", "residence.postalCode")
						.fields("address2.city", "residence.settlement")
						.fields("address1.country.alpha2", "correspondence.country",
								deepHintA(Address.class, Country.class, String.class))
						.fields("address1.houseNumber", "correspondence.houseNo")
						.fields("address1.address", "correspondence.street")
						.fields("address1.postalcode", "correspondence.postalCode")
						.fields("address1.city", "correspondence.settlement");
			}
		};
	}

	private BeanMappingBuilder getPersonBeanMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				String dateFormat = "yyyy-MM-dd";
				mapping(Person.class, PersonBasicInfo.class, TypeMappingOptions.dateFormat(dateFormat))
						.fields("firstName", "personGivenName").fields("lastName", "personSurname")
						.fields("dob", "personBirthDate").fields("country1.alpha2", "personCitizenship")
						.fields("personalCode", "personCode").fields("pepStatus", "personIsPoliticallyExposed");

			}

		};
	}

	private BeanMappingBuilder getCorporateBeanMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				String dateFormat = "yyyy-MM-dd";
				mapping(Corporate.class, BasicInfo.class, TypeMappingOptions.dateFormat(dateFormat))
						.fields("dateOfRegistration", "registrationDate").fields("name", "fullName")
						.fields("registrationNumber", "registrationNumber")
						.fields("country.alpha2", "registrationCountry")
						.fields("corporateFinancialDetails[0].taxIdNumber", "taxIdentificationNumber",
								deepHintA(CorporateFinancialDetail.class, String.class));
			}
		};
	}
	
	private BeanMappingBuilder getPersonAddressMappingBuilder() {
		return new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(PersonContactDetail.class, CorporateAddress.class)
						.fields("address2.country.alpha2", "residence.country",
								deepHintA(Address.class, Country.class, String.class))
						.fields("address2.houseNumber", "residence.houseNo")
						.fields("address2.address", "residence.street")
						.fields("address2.postalcode", "residence.postalCode")
						.fields("address2.city", "residence.settlement")
						.fields("address1.country.alpha2", "correspondence.country",
								deepHintA(Address.class, Country.class, String.class))
						.fields("address1.houseNumber", "correspondence.houseNo")
						.fields("address1.address", "correspondence.street")
						.fields("address1.postalcode", "correspondence.postalCode")
						.fields("address1.city", "correspondence.settlement");
			}
		};
	}
}
