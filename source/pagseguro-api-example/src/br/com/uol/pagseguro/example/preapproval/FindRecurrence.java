package br.com.uol.pagseguro.example.preapproval;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.preapproval.RecurrenceTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.RecurrenceService;

public class FindRecurrence {
	/**
	 * Class with a main method to illustrate the usage of the domain class Recurrence
	 */
    public static void main(String[] args) {

        // Substitute the code below with a valid recurrence code for your account
        findByCode("B297862B013C40B4BCABA5F3D9CD8C3F");
    }

    private static void findByCode(String recurrenceCode) {

        RecurrenceTransaction recurrenceTransaction = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            recurrenceTransaction = RecurrenceService.findByCode(PagSeguroConfig.getAccountCredentials(),
                    recurrenceCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (recurrenceTransaction != null)
            printRecurrence(recurrenceTransaction);

    }

    private static void printRecurrence(RecurrenceTransaction recurrenceTransaction) {

        System.out.println("code: " + recurrenceTransaction.getCode());
        System.out.println("status: " + recurrenceTransaction.getStatus());
        System.out.println("period: " + recurrenceTransaction.getPeriod().toString());
        System.out.println("paymentRequestsQuantity: " + recurrenceTransaction.getPaymentRequestsQuantity());
        System.out.println("initialDate: " + recurrenceTransaction.getInitialDate());

        if (recurrenceTransaction.getPaymentRequest().getSender() != null) {

            System.out.println("paymentRequestSenderName: "
                    + recurrenceTransaction.getPaymentRequest().getSender().getName());
            System.out.println("paymentRequestSenderEmail: "
                    + recurrenceTransaction.getPaymentRequest().getSender().getEmail());
        }

        if (recurrenceTransaction.getPaymentRequest().getItems() != null) {

            System.out.println("itemCount: " + recurrenceTransaction.getPaymentRequest().getItems().size());

            for (PaymentRequestItem item : recurrenceTransaction.getPaymentRequest().getItems()) {
                System.out.println("itemId: " + item.getId());
                System.out.println("itemDescription: " + item.getDescription());
                System.out.println("itemQuantity: " + item.getQuantity());
                System.out.println("itemAmount: " + item.getAmount());
            }
        }

        if (recurrenceTransaction.getPaymentRequest().getShipping() != null) {

            System.out.println("shippingType: " + recurrenceTransaction.getPaymentRequest().getShipping().getShippingType());

            if (recurrenceTransaction.getPaymentRequest().getShipping().getCost() != null) {
                System.out.println("shippingCost: "
                        + recurrenceTransaction.getPaymentRequest().getShipping().getCost());
            }

            if (recurrenceTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage() != null) {
                System.out.println("shippingPackageWeight: "
                        + recurrenceTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage()
                                .getWeight());
                System.out.println("shippingPackageWidth: "
                        + recurrenceTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage()
                                .getWidth());
                System.out.println("shippingPackageHeight: "
                        + recurrenceTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage()
                                .getHeight());
                System.out.println("shippingPackageLength: "
                        + recurrenceTransaction.getPaymentRequest().getShipping().getPaymentRequestShippingPackage()
                                .getLength());
            }
        }

        System.out.println("paymentRequestName: " + recurrenceTransaction.getPaymentRequest().getName());
        System.out.println("paymentRequestDescription: " + recurrenceTransaction.getPaymentRequest().getDescription());
        System.out.println("paymentRequestExpiration: " + recurrenceTransaction.getPaymentRequest().getExpiration());
        System.out.println("paymentRequestReference: " + recurrenceTransaction.getPaymentRequest().getReference());
        System.out.println("paymentRequestDue: " + recurrenceTransaction.getPaymentRequest().getDue());
    }

    private FindRecurrence() {

    }
}
