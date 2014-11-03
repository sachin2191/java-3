package br.com.uol.pagseguro.example.preapproval;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.domain.preapproval.Recurrence;
import br.com.uol.pagseguro.enums.RecurrencePeriod;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreateRecurrence {
    /**
     * Class with a main method to illustrate the usage of the domain class Recurrence
     */
    public static void main(String[] args) {

        Recurrence recurrence = new Recurrence();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        recurrence.setInitialDate( //
                simpleDateFormat.format(new Date()) // today
                );

        PaymentRequest paymentRequest = new PaymentRequest();

        Sender sender = new Sender( //
                "Sender name test", // name
                "test@test.com" // email
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

        recurrence.setPaymentRequest(paymentRequest);

        // payment requests quantity that will be sent to the sender
        recurrence.setPaymentRequestsQuantity(new Integer(3));

        // payment requests period will be sent to the sender
        recurrence.setPeriod(RecurrencePeriod.MONTHLY);

        try {
            // Set your account credentials on src/pagseguro-config.properties
            String recurrenceCode = recurrence.register(PagSeguroConfig.getAccountCredentials());
            System.out.println(recurrenceCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private CreateRecurrence() {

    }
}
