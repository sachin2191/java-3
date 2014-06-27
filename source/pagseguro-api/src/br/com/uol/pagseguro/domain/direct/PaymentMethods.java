package br.com.uol.pagseguro.domain.direct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.uol.pagseguro.enums.PaymentMethodCode;
import br.com.uol.pagseguro.enums.PaymentMethodStatus;
import br.com.uol.pagseguro.enums.PaymentMethodType;

/**
 * Represents the payment methods available
 */
public class PaymentMethods {

    /** payment methods */
    private final Map<PaymentMethodType, Map<Integer, PaymentMethod>> paymentMethods;

    /**
     * Initializes a new instance of the PaymentMethods class
     * 
     * @param paymentMethods
     */
    public PaymentMethods(Map<PaymentMethodType, Map<Integer, PaymentMethod>> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    /**
     * @return the paymentMethods
     */
    public Map<PaymentMethodType, Map<Integer, PaymentMethod>> getPaymentMethods() {
        return Collections.unmodifiableMap(paymentMethods);
    }

    /**
     * Verify if the paymentMethod is available
     * 
     * @param paymentMethod
     * @return
     */
    public boolean isAvailable(PaymentMethodCode paymentMethod) {

        Map<Integer, PaymentMethod> paymentMethodsByType = this.paymentMethods.get(paymentMethod.getType());

        if (paymentMethodsByType != null) {
            PaymentMethod paymentMethodByCode = paymentMethodsByType.get(paymentMethod.getValue());

            if (paymentMethodByCode != null && paymentMethodByCode.getStatus().equals(PaymentMethodStatus.AVAILABLE)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a list of available payment methods of a payment Type
     * 
     * @param paymentMethodType
     * @return a list of PaymentMethod
     */
    public List<PaymentMethod> getPaymentMethodsByType(PaymentMethodType paymentMethodType) {
        return new ArrayList<PaymentMethod>(this.paymentMethods.get(paymentMethodType).values());
    }

}
