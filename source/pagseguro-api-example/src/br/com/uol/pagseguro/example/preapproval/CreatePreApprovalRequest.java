package br.com.uol.pagseguro.example.preapproval;

import java.math.BigDecimal;
import java.util.Calendar;

import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.domain.preapproval.PreApproval;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequest;
import br.com.uol.pagseguro.enums.PreApprovalPeriod;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreatePreApprovalRequest {
    /**
     * Class with a main method to illustrate the usage of the domain class PreApprovalRequest
     */
    public static void main(String[] args) {

        // Pre-approval request information
        PreApprovalRequest preApprovalRequest = new PreApprovalRequest();

        // Pre-approval sender information
        Sender sender = new Sender();
        sender.setName("Sender test name");
        sender.setEmail("sender@test.email");

        preApprovalRequest.setSender(sender);

        preApprovalRequest.setReference("R123456");

        // Pre-approval information
        PreApproval preApproval = new PreApproval();

        preApproval.setName("Pre-approval name test");

        preApproval.setAmountPerPayment( //
                new BigDecimal("100.00") //
                );

        preApproval.setPeriod(PreApprovalPeriod.WEEKLY);

        // Set initialDate as today
        Calendar initialDateAsCalendar = Calendar.getInstance();
        preApproval.setInitialDate(initialDateAsCalendar.getTime());

        preApprovalRequest.setPreApproval(preApproval);

        preApprovalRequest.setMaxUses( //
                new Integer(2) //
                );

        preApprovalRequest.setPaymentDue( //
                new Integer(5) //
                );
        preApprovalRequest.setPaymentExpiration( //
                new Integer(7) //
                );

        // Shipping information
        PaymentRequestShipping paymentRequestShipping = new PaymentRequestShipping();
        paymentRequestShipping.setCost( //
                new BigDecimal("10.00") //
                );

        preApprovalRequest.setPaymentRequestShipping(paymentRequestShipping);

        try {
            // Set your account credentials on src/pagseguro-config.properties
            String preApprovalCode = preApprovalRequest.register(PagSeguroConfig.getAccountCredentials());
            System.out.println(preApprovalCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private CreatePreApprovalRequest() {

    }
}
