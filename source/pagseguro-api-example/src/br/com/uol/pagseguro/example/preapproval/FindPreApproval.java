package br.com.uol.pagseguro.example.preapproval;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

/**
 * Class with a main method to illustrate the usage of the domain class PreApproval
 */
public class FindPreApproval {

    public static void main(String[] args) {

        // Substitute the code below with a valid payment request code for your account
        findByCode("F1DEEA6D76B54267A753D6B87CB6A62D");
    }
    
    private static void findByCode(String preApprovalCode) {

        PreApprovalTransaction preApprovalTransaction = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            preApprovalTransaction = PreApprovalService.findByCode(PagSeguroConfig.getAccountCredentials(),
                    preApprovalCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (preApprovalTransaction != null)
            printPreApproval(preApprovalTransaction);
        
    }
     
    private static void printPreApproval(PreApprovalTransaction preApprovalTransaction) {
        
        System.out.println("code: " + preApprovalTransaction.getCode());
        System.out.println("status: " + preApprovalTransaction.getStatus());
        System.out.println("period: " + preApprovalTransaction.getPeriod().toString());
        System.out.println("paymentRequestsQuantity: " + preApprovalTransaction.getPaymentRequestsQuantity());
        System.out.println("initialDate: " + preApprovalTransaction.getInitialDate());

        if (preApprovalTransaction.getPaymentRequest().getSender() != null) {

            System.out.println("paymentRequestSenderName: " + preApprovalTransaction.getPaymentRequest().getSender().getName());
            System.out.println("paymentRequestSenderEmail: " + preApprovalTransaction.getPaymentRequest().getSender().getEmail());
        }
        
        if(preApprovalTransaction.getPaymentRequest().getItems() != null) {
            
            System.out.println("itemCount: " + preApprovalTransaction.getPaymentRequest().getItems().size());
    
            for (PaymentRequestItem item : preApprovalTransaction.getPaymentRequest().getItems()) {
                System.out.println("itemId: " + item.getId());
                System.out.println("itemDescription: " + item.getDescription());
                System.out.println("itemQuantity: " + item.getQuantity());
                System.out.println("itemAmount: " + item.getAmount());
            }
        }
        
        if (preApprovalTransaction.getPaymentRequest().getShipping() != null) {
            
            System.out.println("shippingType: " + preApprovalTransaction.getPaymentRequest().getShipping().getType());
            
            if(preApprovalTransaction.getPaymentRequest().getShipping().getCost() != null) {
                System.out.println("shippingCost: " + preApprovalTransaction.getPaymentRequest().getShipping().getCost());
            }
            
            if(preApprovalTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage() != null) {
                System.out.println("shippingPackageWeight: " + preApprovalTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWeight());
                System.out.println("shippingPackageWidth: " + preApprovalTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth());
                System.out.println("shippingPackageHeight: " + preApprovalTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getHeight());
                System.out.println("shippingPackageLength: " + preApprovalTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getLength());
            }
        }
        
        System.out.println("paymentRequestName: " + preApprovalTransaction.getPaymentRequest().getName());
        System.out.println("paymentRequestDescription: " + preApprovalTransaction.getPaymentRequest().getDescription());
        System.out.println("paymentRequestExpiration: " + preApprovalTransaction.getPaymentRequest().getExpiration());
        System.out.println("paymentRequestReference: " + preApprovalTransaction.getPaymentRequest().getReference());
        System.out.println("paymentRequestDue: " + preApprovalTransaction.getPaymentRequest().getDue());
    }
    
    private FindPreApproval() {
        
    }
}
