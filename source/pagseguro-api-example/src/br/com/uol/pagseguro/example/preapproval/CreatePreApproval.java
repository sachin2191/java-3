package br.com.uol.pagseguro.example.preapproval;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestSender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.domain.preapproval.PreApproval;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalConstants;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreatePreApproval {
	/**
     * Class with a main method to illustrate the usage of the domain class PreApproval
     */
    public static void main(String[] args) {

    	PreApproval preApproval = new PreApproval();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	preApproval.setInitialDate( //
    	        simpleDateFormat.format(new Date()) // today
    			);
    	
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
    	
    	paymentRequest.setExpiration(30);
    	
    	// Sets a reference code for this payment request, it's useful to identify this payment in future notifications
        paymentRequest.setReference("REF1234");

    	preApproval.setPaymentRequest(paymentRequest);
    	
    	// payment requests quantity that will be sent to the sender
    	preApproval.setPaymentRequestsQuantity(3);
    	
    	// payment requests period will be sent to the sender
    	preApproval.setPeriod(PreApprovalConstants.PRE_APPROVAL_PERIOD_MONTHLY);
    	
        try {
            // Set your account credentials on src/pagseguro-config.properties
            String preApprovalURL = preApproval.register(PagSeguroConfig.getAccountCredentials());
            System.out.println(preApprovalURL);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private CreatePreApproval() {
    	
    }
}
