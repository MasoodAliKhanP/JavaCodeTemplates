package com.connectpay.ib.payments.service.helper;

import org.springframework.stereotype.Component;

import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.bean.PaymentDetails;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentServiceHelper.
 */
@Component
public class PaymentServiceHelper {

    /**
     * Builds the payment details.
     *
     * @param creditorDetails       the creditor details
     * @param paymentServiceRequest the payment service request
     * @return the payment details
     */
    public PaymentDetails buildPaymentDetails(CreditorDetails creditorDetails,
            PaymentServiceRequest paymentServiceRequest) {

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(creditorDetails.getAccountNumber());
        paymentDetails.setBankAddress(creditorDetails.getBankAddress());
        paymentDetails.setBankCode(creditorDetails.getBankCode());
        paymentDetails.setBankName(creditorDetails.getBankName());
        paymentDetails.setBeneficiaryName(creditorDetails.getName());
        paymentDetails.setCreditorDetaildId(creditorDetails.getId());
        paymentDetails.setDebitorIban(paymentServiceRequest.getDebitorIban());
        paymentDetails.setEmail(paymentServiceRequest.getEmail());
        paymentDetails.setFeeAccountCurrency(paymentServiceRequest.getFeeAccountCurrency());
        paymentDetails.setFeeAccountNumber(paymentServiceRequest.getFeeAccountNumber());
        paymentDetails.setPartyId(paymentServiceRequest.getPartyId());
        paymentDetails.setPaymentAmount(paymentServiceRequest.getPaymentAmount());
        paymentDetails.setPaymentCurrency(paymentServiceRequest.getPaymentCurrency());
        paymentDetails.setPaymentStatus(paymentServiceRequest.getPaymentStatus());
        paymentDetails.setPaymentReference(paymentServiceRequest.getPaymentReference());
        paymentDetails.setPaymentStatusId(paymentServiceRequest.getPaymentStatusId());
        paymentDetails.setStructurePayment(paymentServiceRequest.getStructurePayment());
        paymentDetails.setStructurePaymentDetails(paymentServiceRequest.getStructurePaymentDetails());
        paymentDetails.setUnStructurePayment(paymentServiceRequest.getUnStructurePayment());
        paymentDetails.setUnStructurePaymentDetails(paymentServiceRequest.getUnStructurePaymentDetails());
        paymentDetails.setServiceCharge(paymentServiceRequest.getServiceCharge());

        return paymentDetails;
    }

}
