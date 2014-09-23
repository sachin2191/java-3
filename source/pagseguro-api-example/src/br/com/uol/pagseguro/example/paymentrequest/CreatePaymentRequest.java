package br.com.uol.pagseguro.example.paymentrequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestSender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreatePaymentRequest {
    /**
     * Class with a main method to illustrate the usage of the domain class PaymentRequest
     */
    public static void main(String[] args) {

        PaymentRequest paymentRequest = new PaymentRequest();

        PaymentRequestSender sender = new PaymentRequestSender( //
                "test@test.com", // email
                "Sender name test" // name
        );

        paymentRequest.setSender(sender);

        paymentRequest.setName("Payment request test");
        paymentRequest.setDescription("Payment request description test");

        PaymentRequestItem item = new PaymentRequestItem( //
                null, // id
                "Item description test", // description
                new BigDecimal("10.00"), // amount
                new Integer(1) // quantity
        );

        List<PaymentRequestItem> listItems = new ArrayList<PaymentRequestItem>();
        listItems.add(item);

        paymentRequest.setItems(listItems);

        PaymentRequestShipping shipping = new PaymentRequestShipping( //
                new BigDecimal("5.67") // cost
        );

        paymentRequest.setShipping(shipping);

        paymentRequest.setExpiration(new Integer(30));

        // Sets a reference code for this payment request, it's useful to identify this payment in future notifications
        paymentRequest.setReference("REF1234");

        try {
            // Set your account credentials on src/pagseguro-config.properties
            String paymentRequestCode = paymentRequest.register(PagSeguroConfig.getAccountCredentials());
            System.out.println(paymentRequestCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private CreatePaymentRequest() {

    }
}
