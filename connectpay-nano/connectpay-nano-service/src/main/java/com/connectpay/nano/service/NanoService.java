
package com.connectpay.nano.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.connectpay.dto.AccountOwner;
import com.connectpay.pojo.bean.request.NanoServiceRequest;
import com.connectpay.utils.APIResponse;

@Transactional
public interface NanoService {
	APIResponse<String> createCorporate(NanoServiceRequest nanoServiceRequest);

	APIResponse<String> createPerson(List<AccountOwner> ownerList);

	APIResponse<String> addAddressCorporate(NanoServiceRequest nanoServiceRequest);

	APIResponse<String> addAuthorizedPersonCorporate(List<AccountOwner> ownerList);

	APIResponse<String> addDocuments(List<AccountOwner> ownerList, String partyId);

	APIResponse<String> addPersonContact(List<AccountOwner> ownerList, String partyId);

	APIResponse<String> completePersonCreation(List<AccountOwner> ownerList, String partyId);

	APIResponse<String> completeCorporateCreation(NanoServiceRequest nanoServiceRequest);

	APIResponse<String> addCorporateContacts(NanoServiceRequest nanoServiceRequest);

	APIResponse<String> addAddressPerson(List<AccountOwner> ownerList, String partyId);

	List<AccountOwner> getAccountOwners(NanoServiceRequest nanoServiceRequest);

	APIResponse<String> resumeApi(NanoServiceRequest nanoServiceRequest);
}
