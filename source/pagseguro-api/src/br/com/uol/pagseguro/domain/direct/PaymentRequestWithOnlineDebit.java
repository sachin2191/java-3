package br.com.uol.pagseguro.domain.direct;

import java.util.Map;

/**
 * Represents the payment request of the online debit
 */
public class PaymentRequestWithOnlineDebit extends PaymentRequest {

    /**
     * Bank name
     */
    private String bankName;

    /**
     * Initializes a new instance of the PaymentRequestWithOnlineDebit class
     */
    public PaymentRequestWithOnlineDebit() {

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

}
