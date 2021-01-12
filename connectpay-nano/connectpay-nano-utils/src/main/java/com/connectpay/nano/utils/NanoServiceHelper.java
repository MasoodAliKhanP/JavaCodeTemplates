package com.connectpay.nano.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.connectpay.dto.AccountOwner;
import com.connectpay.dto.Application;
import com.connectpay.dto.Corporate;
import com.connectpay.dto.CorporateContactDetail;
import com.connectpay.dto.IdentificationDocument;
import com.connectpay.dto.IdentificationDocumentType;
import com.connectpay.dto.Person;
import com.connectpay.dto.PersonIbMapping;
import com.connectpay.dto.dao.PersonIbMappingDao;
import com.connectpay.nano.bean.AddressBasicInfo;
import com.connectpay.nano.bean.ContactBasicInfo;
import com.connectpay.nano.bean.CorporateAddress;
import com.connectpay.nano.bean.DocumentBasicInfo;
import com.connectpay.nano.bean.NanoContactPerson;
import com.connectpay.nano.bean.NanoCorporateAddress;
import com.connectpay.utils.ConnectPayUtils;
import com.connectpay.utils.constants.ApplicationConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class NanoServiceHelper {
	private static final String DEFAULT_LANGUAGE = "en";
	private static final String PHONE_TYPE_LAND = "LAND";
	private static final String PHONE_TYPE_MOBILE = "MOBILE";
	private static final String PASSPORT = "Passport";
	private static final String LOCAL_PASSPORT = "LOCAL_PASSPORT";
	private static final String ID_CARD = "ID_CARD";
	private static final String ID_ISSUE_PLACE = "VPK";
	private static final String LT = "LT";
	private static final String ALIEN_PASSPORT = "ALIEN_PASSPORT";
	private static final String EURO_ID_CARD = "EURO_ID_CARD";
	
	@Autowired
	private PersonIbMappingDao personIbMappingDao;

	public Map<String, String> prepareAddressRequestData(NanoCorporateAddress nanoCorporateAddress, String partyId) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put(ApplicationConstants.NANO_CORPORATE_ADDRESS, gson.toJson(nanoCorporateAddress));
		requestMap.put(ApplicationConstants.PARTY_ID, partyId);
		return requestMap;
	}

	public CorporateAddress prepareCorporateAddress(AddressBasicInfo residenceAddress) {
		CorporateAddress corporateAddress = new CorporateAddress();
		corporateAddress.setResidence(residenceAddress);
		return corporateAddress;
	}

	public NanoCorporateAddress prepareNanoCorporateAddress(CorporateAddress corporateAddress, boolean b) {
		NanoCorporateAddress nanoCorporateAddress = new NanoCorporateAddress();
		nanoCorporateAddress.setAddresses(corporateAddress);
		nanoCorporateAddress.setUseResidenceAddressForCorrespondence(b);
		return nanoCorporateAddress;
	}

	public Map<String, String> prepareAuthorizedPersonRequest(List<AccountOwner> ownerList) {
		List<String> authorizedPersons = new ArrayList<>();
		Corporate corporate = null;
		for (AccountOwner owner : ownerList) {
			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType().equalsIgnoreCase(owner.getAccountOwnersType().getName())) {
				Person person = owner.getPerson();
				corporate = owner.getApplication().getCorporate();
				PersonIbMapping personIbMapping = personIbMappingDao.getPersonIbMappingByPersonId(person.getId());
				authorizedPersons.add(personIbMapping.getPartyId());
			}
		}
		
		Map<String, String> reuqestMap = new HashMap<>();
		reuqestMap.put(ApplicationConstants.AUTHORIZED_PERSONS, String.join(",", authorizedPersons));
		reuqestMap.put(ApplicationConstants.PARTY_ID, null!= corporate ? corporate.getPartyId() : null);
		return reuqestMap;
	}

	public Map<String, String> prepareContactBasicInfo(Person person, String partyId) {
		ContactBasicInfo contactBasicInfo = new ContactBasicInfo();
		Map<String, String> requestMap = new HashMap<>();

		contactBasicInfo.setLanguage(DEFAULT_LANGUAGE);
		if (null == person.getMobile()) {
			contactBasicInfo.setMainPhoneNumber(person.getLandlineCallingCode()+person.getLandLine());
			contactBasicInfo.setMainPhoneType(PHONE_TYPE_LAND);
		} else {
			contactBasicInfo.setMainPhoneNumber(person.getMobileCallingCode()+person.getMobile());
			contactBasicInfo.setMainPhoneType(PHONE_TYPE_MOBILE);
		}
		contactBasicInfo.setMainEmail(person.getEmail());

		NanoContactPerson nanoContactPerson = new NanoContactPerson();
		nanoContactPerson.setContacts(contactBasicInfo);

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		requestMap.put(ApplicationConstants.NANO_CONTACT_DETAILS, gson.toJson(nanoContactPerson));
		requestMap.put(ApplicationConstants.PARTY_ID, partyId);
		return requestMap;
	}

	public Map<String, String> preparePersonDocumentRequest(Person person, String partyId) {
		Map<String, String> requestMap = new HashMap<>();
		DocumentBasicInfo documentBasicInfo = new DocumentBasicInfo();
		IdentificationDocument identificationDocument = person.getIdentificationDocument();
		IdentificationDocumentType identificationDocumentType = identificationDocument.getIdentificationDocumentType();
		if (PASSPORT.equalsIgnoreCase(identificationDocumentType.getName())) {
			if (LT.equalsIgnoreCase(identificationDocument.getCountry().getAlpha2())) {
				documentBasicInfo.setIdType(ALIEN_PASSPORT);
			} else {
				documentBasicInfo.setIdType(LOCAL_PASSPORT);
			}
		} else {
			if (LT.equalsIgnoreCase(identificationDocument.getCountry().getAlpha2())) {
				documentBasicInfo.setIdType(EURO_ID_CARD);
			} else {
				documentBasicInfo.setIdType(ID_CARD);
			}
		}
		documentBasicInfo.setIdNumber(identificationDocument.getDocNumber());
		documentBasicInfo.setIdIssuingCountry(identificationDocument.getCountry().getAlpha2());
		//documentBasicInfo.setIdIssuePlace(ID_ISSUE_PLACE);
		documentBasicInfo.setIdIssueDate(ConnectPayUtils.getStringFromDate(identificationDocument.getIssueDate()));
		documentBasicInfo.setIdExpiryDate(ConnectPayUtils.getStringFromDate(identificationDocument.getExpiryDate()));
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		requestMap.put(ApplicationConstants.NANO_DOCUMENT_BASIC_INFO, gson.toJson(documentBasicInfo));
		requestMap.put(ApplicationConstants.PARTY_ID, partyId);
		return requestMap;
	}

	public String getPersonPartyId(Person person) {
		PersonIbMapping personIbMapping = personIbMappingDao.getPersonIbMappingByPersonId(person.getId());
		return personIbMapping.getPartyId();
	}

	public String getCorporatePartyId(Application application) {
		Corporate corporate = application.getCorporate();
		return corporate.getPartyId();
	}

	public Map<String, String> prepareContactBasicInfo(Corporate corporate) {
		ContactBasicInfo contactBasicInfo = new ContactBasicInfo();
		Map<String, String> requestMap = new HashMap<>();

		contactBasicInfo.setLanguage(DEFAULT_LANGUAGE);
		CorporateContactDetail corporateContactDetails = corporate.getCorporateContactDetails().get(0);
		contactBasicInfo.setMainPhoneNumber(corporateContactDetails.getLandlineCallingCode()+corporateContactDetails.getLandlineNumer());
		contactBasicInfo.setMainPhoneType(PHONE_TYPE_MOBILE);//ON UI the above phone number is mandatory even though it is a landline number we are sending type as Mobile
		contactBasicInfo.setMainEmail(corporateContactDetails.getContactEmail());

		NanoContactPerson nanoContactPerson = new NanoContactPerson();
		nanoContactPerson.setContacts(contactBasicInfo);

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		requestMap.put(ApplicationConstants.NANO_CONTACT_DETAILS, gson.toJson(nanoContactPerson));
		requestMap.put(ApplicationConstants.PARTY_ID, corporate.getPartyId());
		return requestMap;
	}
}
