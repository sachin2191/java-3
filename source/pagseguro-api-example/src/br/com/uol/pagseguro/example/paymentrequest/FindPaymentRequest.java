package br.com.uol.pagseguro.example.paymentrequest;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.paymentrequest.PaymentRequestService;

public class FindPaymentRequest {
	/**
     * Class with a main method to illustrate the usage of the domain class PaymentRequest
     */
    public static void main(String[] args) {

    	// Substitute the code below with a valid payment request code for your account
    	findByCode("2818C7F7A57345D6B26B33614D3A66AA");
    }
    
    private static void findByCode(String paymentRequestCode) {

    	PaymentRequestTransaction paymentRequestTransaction = null;

    	try {

    	    // Set your account credentials on src/pagseguro-config.properties
    	    paymentRequestTransaction = PaymentRequestService.findByCode(PagSeguroConfig.getAccountCredentials(),
    	            paymentRequestCode);

    	} catch (PagSeguroServiceException e) {
    	    System.err.println(e.getMessage());
    	}

    	if (paymentRequestTransaction != null)
    	    printPaymentRequest(paymentRequestTransaction);
    	
    }
     
    private static void printPaymentRequest(PaymentRequestTransaction paymentRequestTransaction) {
    	
    	System.out.println("date: " + paymentRequestTransaction.getDate());
        System.out.println("code: " + paymentRequestTransaction.getCode());
        System.out.println("reference: " + paymentRequestTransaction.getReference());
        System.out.println("recoveryCode: " + paymentRequestTransaction.getRecoveryCode());
        System.out.println("type: " + paymentRequestTransaction.getType());
        System.out.println("status: " + paymentRequestTransaction.getStatus());
        System.out.println("expiration: " + paymentRequestTransaction.getExpiration());
        System.out.println("due: " + paymentRequestTransaction.getDue());

        System.out.println("lastEventDate: " + paymentRequestTransaction.getLastEventDate());
        System.out.println("paymentMethodType: " + paymentRequestTransaction.getPaymentMethod().getType());
        System.out.println("paymentMethodCode: " + paymentRequestTransaction.getPaymentMethod().getCode());

        System.out.println("grossAmount: " + paymentRequestTransaction.getGrossAmount());
        System.out.println("paidAmount: " + paymentRequestTransaction.getPaidAmount());
        System.out.println("discountAmount: " + paymentRequestTransaction.getDiscountAmount());
        System.out.println("netAmount: " + paymentRequestTransaction.getNetAmount());
        System.out.println("extraAmount: " + paymentRequestTransaction.getExtraAmount());
        
        System.out.println("receiverFeesInstallmentFeeAmount: " + paymentRequestTransaction.getReceiverFees().getInstallmentFeeAmount());
        System.out.println("receiverFeesOperationalFeeAmount: " + paymentRequestTransaction.getReceiverFees().getOperationalFeeAmount());
        System.out.println("receiverFeesIntermediationRateAmount: " + paymentRequestTransaction.getReceiverFees().getIntermediationRateAmount());
        System.out.println("receiverFeesIntermediationFeeAmount: " + paymentRequestTransaction.getReceiverFees().getIntermediationFeeAmount());
        System.out.println("receiverFeesCommissionFeeAmount: " + paymentRequestTransaction.getReceiverFees().getCommissionFeeAmount());
        System.out.println("receiverFeesEfrete: " + paymentRequestTransaction.getReceiverFees().getEfrete());
        
        System.out.println("description: " + paymentRequestTransaction.getDescription());
        System.out.println("escrowEndDate: " + paymentRequestTransaction.getEscrowEndDate());
        System.out.println("installmentCount: " + paymentRequestTransaction.getInstallmentCount());
        
        System.out.println("itemCount: " + paymentRequestTransaction.getItemCount());

        for (PaymentRequestItem item : paymentRequestTransaction.getItems()) {
        	System.out.println("itemId: " + item.getId());
            System.out.println("itemDescription: " + item.getDescription());
            System.out.println("itemQuantity: " + item.getQuantity());
            System.out.println("itemAmount: " + item.getAmount());
		}
        
        System.out.println("senderName: " + paymentRequestTransaction.getSender().getName());
        System.out.println("senderEmail: " + paymentRequestTransaction.getSender().getEmail());

        if (paymentRequestTransaction.getSender() != null) {
            System.out.println("senderPhoneAreaCode: " + paymentRequestTransaction.getSender().getPhone().getAreaCode());
            System.out.println("senderPhoneNumber: " + paymentRequestTransaction.getSender().getPhone().getNumber());
        }

        if (paymentRequestTransaction.getShipping() != null) {
            if (paymentRequestTransaction.getShipping().getAddress() != null) {
                System.out.println("shippingAddressStreet: " + paymentRequestTransaction.getShipping().getAddress().getStreet());
                System.out.println("shippingAddressNumber: " + paymentRequestTransaction.getShipping().getAddress().getNumber());
                System.out.println("shippingAddressComplement: "
                        + paymentRequestTransaction.getShipping().getAddress().getComplement());
                System.out.println("shippingAddressDistrict: " + paymentRequestTransaction.getShipping().getAddress().getDistrict());
                System.out.println("shippingAddressCity: " + paymentRequestTransaction.getShipping().getAddress().getCity());
                System.out.println("shippingAddressState: " + paymentRequestTransaction.getShipping().getAddress().getState());
                System.out.println("shippingAddressCountry: " + paymentRequestTransaction.getShipping().getAddress().getCountry());
                System.out.println("shippingAddressPostalCode: "
                        + paymentRequestTransaction.getShipping().getAddress().getPostalCode());
            }
            
            System.out.println("shippingType: " + paymentRequestTransaction.getShipping().getType());
            
            if(paymentRequestTransaction.getShipping().getCost() != null) {
            	System.out.println("shippingCost: " + paymentRequestTransaction.getShipping().getCost());
            }
            
            if(paymentRequestTransaction.getShipping().getPaymentRequestShippingPackage() != null) {
            	System.out.println("shippingPackageWeight: " + paymentRequestTransaction.getShipping().getPaymentRequestShippingPackage().getWeight());
            	System.out.println("shippingPackageWidth: " + paymentRequestTransaction.getShipping().getPaymentRequestShippingPackage().getWidth());
            	System.out.println("shippingPackageHeight: " + paymentRequestTransaction.getShipping().getPaymentRequestShippingPackage().getHeight());
            	System.out.println("shippingPackageLength: " + paymentRequestTransaction.getShipping().getPaymentRequestShippingPackage().getLength());
            }
        }
    }
    
    private FindPaymentRequest() {
    	
    }
}
