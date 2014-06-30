package br.com.uol.pagseguro.domain.direct;

import java.util.Map;

/**
 * Represents the payment request of the boleto
 */
public class BoletoPaymentRequest extends PaymentRequest {

    /**
     * Dynamic payment method message
     */
    private String dynamicPaymentMethodMessage;

    /**
     * Initializes a new instance of the PaymentRequestWithBoleto class
     */
    public BoletoPaymentRequest() {

    }

    /**
     * @return the dynamicPaymentMethodMessage
     */
    public String getDynamicPaymentMethodMessage() {
        return dynamicPaymentMethodMessage;
    }

    /**
     * @param dynamicPaymentMethodMessage
     *            the dynamicPaymentMethodMessage to set
     */
    public void setDynamicPaymentMethodMessage(String dynamicPaymentMethodMessage) {
        this.dynamicPaymentMethodMessage = dynamicPaymentMethodMessage;
    }

    @Override
    public Map<Object, Object> getMap() {
        final Map<Object, Object> data = super.getMap();

        data.put("paymentMethod", "BOLETO");

        if (dynamicPaymentMethodMessage != null) {
            data.put("dynamicPaymentMethodMessageBoleto", dynamicPaymentMethodMessage);
        }

        return data;
    }

}
