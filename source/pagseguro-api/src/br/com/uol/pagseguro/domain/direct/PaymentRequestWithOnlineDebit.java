package br.com.uol.pagseguro.domain.direct;

import java.util.Map;

/**
 * Represents the eletronic fund transfer payment method
 */
public class PaymentRequestWithOnlineDebit extends PaymentRequest {

    private String bankName;

    /**
     * Initializes a new instance of the AbstractPaymentMethod class
     */
    public PaymentRequestWithOnlineDebit() {
    }

    /**
     * Initializes a new instance of the AbstractPaymentMethod class
     * 
     * @param bankName
     */
    public PaymentRequestWithOnlineDebit(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the bank name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     *            the bank name to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public Map<Object, Object> getMap() {
        final Map<Object, Object> data = super.getMap();

        data.put("paymentMethod", "ONLINE_DEBIT");

        if (bankName != null) {
            data.put("bankName", bankName);
        }

        return data;
    }

    @Override
    public String toString() {
        return "PaymentMethodEft [bankName=" + bankName + "]";
    }

}
