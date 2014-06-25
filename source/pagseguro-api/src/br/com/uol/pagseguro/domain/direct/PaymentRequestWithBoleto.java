package br.com.uol.pagseguro.domain.direct;

import java.util.Map;

/**
 * Represents the boleto payment method
 */
public class PaymentRequestWithBoleto extends PaymentRequest {

    /** Dynamic Payment Method Message */
    private String dynamicPaymentMethodMessage;

    /**
     * Initializes a new instance of the PaymentMethodBoleto class
     */
    public PaymentRequestWithBoleto() {
    }

    /**
     * Initializes a new instance of the PaymentMethodBoleto class
     * 
     * @param dynamicPaymentMethodMessage
     */
    public PaymentRequestWithBoleto(String dynamicPaymentMethodMessage) {
        this.dynamicPaymentMethodMessage = dynamicPaymentMethodMessage;
    }

    /**
     * @return the dynamicPaymentMethodMessageBoleto
     */
    public String getDynamicPaymentMethodMessage() {
        return dynamicPaymentMethodMessage;
    }

    /**
     * @param dynamicPaymentMethodMessageBoleto
     *            the dynamicPaymentMethodMessageBoleto to set
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

    @Override
    public String toString() {
        return "PaymentMethodBoleto [dynamicPaymentMethodMessageBoleto=" + dynamicPaymentMethodMessage + "]";
    }

}
