
package com.connectpay.nano.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.connectpay.dto.AccountOwner;
import com.connectpay.dto.Application;
import com.connectpay.dto.Corporate;
import com.connectpay.dto.CorporateContactDetail;
import com.connectpay.dto.Person;
import com.connectpay.dto.PersonContactDetail;
import com.connectpay.dto.PersonIbMapping;
import com.connectpay.dto.dao.ApplicationDao;
import com.connectpay.dto.dao.CorporateDao;
import com.connectpay.dto.dao.PersonDao;
import com.connectpay.http.bean.RequestAndResponseFormatter;
import com.connectpay.http.helper.IntegrationHelper;
import com.connectpay.nano.bean.AddressBasicInfo;
import com.connectpay.nano.bean.BasicInfo;
import com.connectpay.nano.bean.CorporateAddress;
import com.connectpay.nano.bean.NanoCorporateAddress;
import com.connectpay.nano.bean.PersonBasicInfo;
import com.connectpay.nano.service.NanoService;
import com.connectpay.nano.utils.NanoServiceHelper;
import com.connectpay.pojo.bean.request.NanoServiceRequest;
import com.connectpay.utils.APIResponse;
import com.connectpay.utils.CPErrorCodes;
import com.connectpay.utils.ConnectPayUtils;
import com.connectpay.utils.LogUtil;
import com.connectpay.utils.ResponseStatus;
import com.connectpay.utils.constants.ApplicationConstants;
import com.connectpay.utils.constants.ApplicationConstants.FormatterConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class NanoServiceImpl implements NanoService {

	private static final String PARTY_ID = "partyId";
	private static final String PARTY_IS_DUPLICATE = "PartyIsDuplicate";

	@Autowired
	private IntegrationHelper integrationHelper;
	@Autowired
	private ApplicationDao applicationDao;
	@Autowired
	private CorporateDao corporateDao;
	@Autowired
	@Qualifier("nanoDozerBeanMapper")
	private DozerBeanMapper nanoDozerBeanMapper;
	@Autowired
	private NanoServiceHelper nanoServiceHelper;
	@Autowired
	private PersonDao personDao;

	private static final Logger LOGGER = LogManager.getLogger(NanoServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public APIResponse<String> createCorporate(NanoServiceRequest nanoServiceRequest) {
		Application application = applicationDao.getApplicationDetails(nanoServiceRequest.getApplicationReference());
		if (null == application) {
			return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, CPErrorCodes.APPLICATION_NOT_FOUND, null);
		}
		LogUtil.log(LOGGER, "Nano Create Corporate Started");
		Corporate corporate = application.getCorporate();
		BasicInfo basicInfo = null != corporate ? nanoDozerBeanMapper.map(corporate, BasicInfo.class) : null;
		String requestHandler = FormatterConstants.CREATE_CORPORATE_REQUEST.getFormatterConstants();
		String responseHandler = FormatterConstants.CREATE_CORPORATE_RESPONSE.getFormatterConstants();
		LogUtil.log(LOGGER, "requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);
		RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper.prepareFormatters(requestHandler,
				responseHandler);

		String response = integrationHelper.execute(requestAndResponseFormatter, basicInfo);

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Map<String, String> finalResponseMap = gson.fromJson(response, Map.class);
		if (null != finalResponseMap && finalResponseMap.containsKey(PARTY_ID) && null != corporate) {
			corporate.setPartyId(finalResponseMap.get(PARTY_ID));
			corporateDao.saveCorporateDetails(corporate);
			return ConnectPayUtils.getSuccessResponse(response);
		}
		LogUtil.log(LOGGER, "Nano Create Corporate Ended");
		return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, null, null, response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public APIResponse<String> createPerson(List<AccountOwner> ownerList) {

		Map<Long, String> responseMap = new HashMap<>();
		for (AccountOwner owner : ownerList) {
			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType()
					.equalsIgnoreCase(owner.getAccountOwnersType().getName())) {
				Person person = owner.getPerson();
				PersonBasicInfo personBasicInfo = nanoDozerBeanMapper.map(person, PersonBasicInfo.class);
				String requestHandler = FormatterConstants.CREATE_PERSON_REQUEST.getFormatterConstants();
				String responseHandler = FormatterConstants.CREATE_PERSON_RESPONSE.getFormatterConstants();
				LogUtil.log(LOGGER,
						"requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

				RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper
						.prepareFormatters(requestHandler, responseHandler);

				String response = integrationHelper.execute(requestAndResponseFormatter, personBasicInfo);

				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				Map<String, String> finalResponseMap = gson.fromJson(response, Map.class);
				if (null != finalResponseMap && finalResponseMap.containsKey(PARTY_ID)) {
					// person.setPartyId(finalResponseMap.get(PARTY_ID));
					// personDao.savePersonDetails(person);
					responseMap.put(person.getId(), response);
				} else if (null != finalResponseMap
						&& finalResponseMap.containsKey(ApplicationConstants.NANO_ERROR_CODE) && PARTY_IS_DUPLICATE
								.equalsIgnoreCase(finalResponseMap.get(ApplicationConstants.NANO_ERROR_CODE))) {
					List<Person> personPartyList = personDao.getPartyId(person);
					String partyId = null;
					for (Person p : personPartyList) {
						if (null != p.getPartyId() && !"".equalsIgnoreCase(p.getPartyId())) {
							partyId = p.getPartyId();
							break;
						}
					}
					if (null == partyId) {
						for (Person p : personPartyList) {
							if (!p.getPersonIbMappings().isEmpty()) {
								LogUtil.log(LOGGER, "GETTING PersonIbMapping FOR :: "+p.getId());
								PersonIbMapping personIbMapping = p.getPersonIbMappings().get(0);
								if (null != personIbMapping) {
									partyId = personIbMapping.getPartyId();
									LogUtil.log(LOGGER, "SETTING PARTYiD FOR PERSON :: "+p.getId());
									break;
								}
							}
						}
					}
					// person.setPartyId(partyId);
					// personDao.savePersonDetails(person);
					Map<String, String> partyResponse = new HashMap<>();
					partyResponse.put(PARTY_ID, partyId);

					responseMap.put(person.getId(), gson.toJson(partyResponse));
				} else {
					responseMap.put(person.getId(), response);
				}
			}
		}
		Gson gson = new Gson();
		return ConnectPayUtils.getSuccessResponse(gson.toJson(responseMap));
	}

	@Override
	public APIResponse<String> addAddressCorporate(NanoServiceRequest nanoServiceRequest) {
		Application application = applicationDao.getApplicationDetails(nanoServiceRequest.getApplicationReference());
		if (null == application) {
			return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, CPErrorCodes.APPLICATION_NOT_FOUND, null);
		}
		Corporate corporate = application.getCorporate();
		CorporateContactDetail corporateContactDetails = corporate.getCorporateContactDetails().get(0);
		NanoCorporateAddress nanoCorporateAddress = null;
		if (!corporateContactDetails.getHasAlternateAddress()) {
			AddressBasicInfo residenceAddress = nanoDozerBeanMapper.map(corporateContactDetails.getAddress1(),
					AddressBasicInfo.class);
			CorporateAddress corporateAddress = nanoServiceHelper.prepareCorporateAddress(residenceAddress);
			nanoCorporateAddress = nanoServiceHelper.prepareNanoCorporateAddress(corporateAddress, true);
		} else {

			AddressBasicInfo residenceAddress = nanoDozerBeanMapper.map(corporateContactDetails.getAddress1(),
					AddressBasicInfo.class);
			AddressBasicInfo correspondenceAddress = nanoDozerBeanMapper.map(corporateContactDetails.getAddress2(),
					AddressBasicInfo.class);
			CorporateAddress corporateAddress = nanoServiceHelper.prepareCorporateAddress(residenceAddress);
			corporateAddress.setCorrespondence(correspondenceAddress);
			nanoCorporateAddress = nanoServiceHelper.prepareNanoCorporateAddress(corporateAddress, false);
		}

		String requestHandler = FormatterConstants.ADD_CORPORATE_ADDRESS_REQUEST.getFormatterConstants();
		String responseHandler = FormatterConstants.ADD_CORPORATE_ADDRESS_RESPONSE.getFormatterConstants();
		LogUtil.log(LOGGER, "requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

		RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper.prepareFormatters(requestHandler,
				responseHandler);

		String response = integrationHelper.execute(requestAndResponseFormatter,
				nanoServiceHelper.prepareAddressRequestData(nanoCorporateAddress, corporate.getPartyId()));

		if (ApplicationConstants.SUCCESS_MESSAGE.equalsIgnoreCase(response)) {
			return ConnectPayUtils.getSuccessResponse(response);
		}
		return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, null, null, response);
	}

	@Override
	public APIResponse<String> addAuthorizedPersonCorporate(List<AccountOwner> ownerList) {
		Map<String, String> requestMap = nanoServiceHelper.prepareAuthorizedPersonRequest(ownerList);

		String requestHandler = FormatterConstants.ADD_CORPORATE_AUTHORIZED_PERSON_REQUEST.getFormatterConstants();
		String responseHandler = FormatterConstants.ADD_CORPORATE_AUTHORIZED_PERSON_RESPONSE.getFormatterConstants();
		LogUtil.log(LOGGER, "requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

		RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper.prepareFormatters(requestHandler,
				responseHandler);

		String response = integrationHelper.execute(requestAndResponseFormatter, requestMap);

		if (ApplicationConstants.SUCCESS_MESSAGE.equalsIgnoreCase(response)) {
			return ConnectPayUtils.getSuccessResponse(response);
		}
		return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, null, null, response);

	}

	@Override
	public APIResponse<String> addDocuments(List<AccountOwner> ownerList, String partyId) {
		
		Map<Long, String> responseMap = new HashMap<>();
		
		for (AccountOwner owner : ownerList) {
			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType()
					.equalsIgnoreCase(owner.getAccountOwnersType().getName())) {
				Person person = owner.getPerson();
				Map<String, String> documentBasicInfo = nanoServiceHelper.preparePersonDocumentRequest(person, partyId);

				String requestHandler = FormatterConstants.CREATE_PERSON_DOCUMENT_REQUEST.getFormatterConstants();
				String responseHandler = FormatterConstants.CREATE_PERSON_DOCUMENT_RESPONSE.getFormatterConstants();
				LogUtil.log(LOGGER,
						"requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

				RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper
						.prepareFormatters(requestHandler, responseHandler);

				String response = integrationHelper.execute(requestAndResponseFormatter, documentBasicInfo);
				responseMap.put(person.getId(), response);
			}
		}
		Gson gson = new Gson();
		return ConnectPayUtils.getSuccessResponse(gson.toJson(responseMap));
	}

	@Override
	public APIResponse<String> addPersonContact(List<AccountOwner> ownerList, String partyId) {

		Map<Long, String> responseMap = new HashMap<>();

		for (AccountOwner owner : ownerList) {
			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType()
					.equalsIgnoreCase(owner.getAccountOwnersType().getName())) {

				Person person = owner.getPerson();
				Map<String, String> contactBasicInfo = nanoServiceHelper.prepareContactBasicInfo(person, partyId);

				String requestHandler = FormatterConstants.CREATE_PERSON_CONTACT_REQUEST.getFormatterConstants();
				String responseHandler = FormatterConstants.CREATE_PERSON_CONTACT_RESPONSE.getFormatterConstants();
				LogUtil.log(LOGGER,
						"requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

				RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper
						.prepareFormatters(requestHandler, responseHandler);

				String response = integrationHelper.execute(requestAndResponseFormatter, contactBasicInfo);
				responseMap.put(person.getId(), response);
			}
		}
		Gson gson = new Gson();
		return ConnectPayUtils.getSuccessResponse(gson.toJson(responseMap));
	}

	@Override
	public APIResponse<String> completePersonCreation(List<AccountOwner> ownerList, String partyId) {
		Map<Long, String> responseMap = new HashMap<>();

		for (AccountOwner owner : ownerList) {
			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType()
					.equalsIgnoreCase(owner.getAccountOwnersType().getName())) {
				Person person = owner.getPerson();
				//String partyId = nanoServiceHelper.getPersonPartyId(person);
				String requestHandler = FormatterConstants.COMPLETE_PERSON_CREATION_REQUEST.getFormatterConstants();
				String responseHandler = FormatterConstants.COMPLETE_PERSON_CREATION_RESPONSE.getFormatterConstants();
				LogUtil.log(LOGGER,
						"requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

				RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper
						.prepareFormatters(requestHandler, responseHandler);

				String response = integrationHelper.execute(requestAndResponseFormatter, partyId);
				responseMap.put(person.getId(), response);

			}
		}

		Gson gson = new Gson();
		return ConnectPayUtils.getSuccessResponse(gson.toJson(responseMap));
	}

	@Override
	public APIResponse<String> completeCorporateCreation(NanoServiceRequest nanoServiceRequest) {
		Application application = applicationDao.getApplicationDetails(nanoServiceRequest.getApplicationReference());
		if (null == application) {
			return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, CPErrorCodes.APPLICATION_NOT_FOUND, null);
		}

		String partyId = nanoServiceHelper.getCorporatePartyId(application);
		String requestHandler = FormatterConstants.COMPLETE_CORPORATE_CREATION_REQUEST.getFormatterConstants();
		String responseHandler = FormatterConstants.COMPLETE_CORPORATE_CREATION_RESPONSE.getFormatterConstants();
		LogUtil.log(LOGGER, "requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

		RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper.prepareFormatters(requestHandler,
				responseHandler);

		String response = integrationHelper.execute(requestAndResponseFormatter, partyId);
		return ConnectPayUtils.getSuccessResponse(response);
	}

	@Override
	public APIResponse<String> addCorporateContacts(NanoServiceRequest nanoServiceRequest) {
		Application application = applicationDao.getApplicationDetails(nanoServiceRequest.getApplicationReference());
		if (null == application) {
			return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, CPErrorCodes.APPLICATION_NOT_FOUND, null);
		}
		Corporate corporate = application.getCorporate();
		Map<String, String> contactBasicInfo = nanoServiceHelper.prepareContactBasicInfo(corporate);

		String requestHandler = FormatterConstants.CREATE_PERSON_CONTACT_REQUEST.getFormatterConstants();
		String responseHandler = FormatterConstants.CREATE_PERSON_CONTACT_RESPONSE.getFormatterConstants();
		LogUtil.log(LOGGER, "requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);

		RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper.prepareFormatters(requestHandler,
				responseHandler);

		String response = integrationHelper.execute(requestAndResponseFormatter, contactBasicInfo);

		if (ApplicationConstants.SUCCESS_MESSAGE.equalsIgnoreCase(response)) {
			return ConnectPayUtils.getSuccessResponse(response);
		}
		return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, null, null, response);
	}

	@Override
	public APIResponse<String> addAddressPerson(List<AccountOwner> ownerList, String partyId) {

		Map<Long, String> responseMap = new HashMap<>();
		for (AccountOwner owner : ownerList) {

			if (ApplicationConstants.AccountOwnerType.AUTHORIZED_SIGNATORIES.getAccountOwnerType()
					.equalsIgnoreCase(owner.getAccountOwnersType().getName())) {
				Person person = owner.getPerson();

				PersonContactDetail personContactDetails = person.getPersonContactDetails().get(0);
				NanoCorporateAddress nanoCorporateAddress = null;
				if (null == personContactDetails.getHasAlternateAddress()
						|| !personContactDetails.getHasAlternateAddress()) {
					AddressBasicInfo residenceAddress = nanoDozerBeanMapper.map(personContactDetails.getAddress1(),
							AddressBasicInfo.class);
					CorporateAddress corporateAddress = nanoServiceHelper.prepareCorporateAddress(residenceAddress);
					nanoCorporateAddress = nanoServiceHelper.prepareNanoCorporateAddress(corporateAddress, true);
				} else {
					AddressBasicInfo residenceAddress = null != personContactDetails.getAddress1()
							? nanoDozerBeanMapper.map(personContactDetails.getAddress1(), AddressBasicInfo.class)
							: null;
					AddressBasicInfo correspondenceAddress = null != personContactDetails.getAddress2()
							? nanoDozerBeanMapper.map(personContactDetails.getAddress2(), AddressBasicInfo.class)
							: null;
					CorporateAddress corporateAddress = nanoServiceHelper.prepareCorporateAddress(residenceAddress);
					corporateAddress.setCorrespondence(correspondenceAddress);
					nanoCorporateAddress = nanoServiceHelper.prepareNanoCorporateAddress(corporateAddress, false);
				}

				String requestHandler = FormatterConstants.ADD_CORPORATE_ADDRESS_REQUEST.getFormatterConstants();
				String responseHandler = FormatterConstants.ADD_CORPORATE_ADDRESS_RESPONSE.getFormatterConstants();
				LogUtil.log(LOGGER,
						"requestHandler is :: " + requestHandler + " :: responseHandler is :: " + responseHandler);
				RequestAndResponseFormatter requestAndResponseFormatter = integrationHelper
						.prepareFormatters(requestHandler, responseHandler);

				String response = integrationHelper.execute(requestAndResponseFormatter, nanoServiceHelper
						.prepareAddressRequestData(nanoCorporateAddress, partyId));

				responseMap.put(person.getId(), response);
			}
		}
		Gson gson = new Gson();
		return ConnectPayUtils.getSuccessResponse(gson.toJson(responseMap));
	}

	@Override
	public List<AccountOwner> getAccountOwners(NanoServiceRequest request) {
		Application application = applicationDao.getApplicationDetails(request.getApplicationReference());
		return application.getAccountOwners().stream().filter(AccountOwner::getStatus).collect(Collectors.toList());
	}

	@Override
	public APIResponse<String> resumeApi(NanoServiceRequest nanoServiceRequest) {
		return null;
	}
}
