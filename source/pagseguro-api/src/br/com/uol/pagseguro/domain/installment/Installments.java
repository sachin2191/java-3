package br.com.uol.pagseguro.domain.installment;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents the option of installment of a credit card payment returned by PagSeguro API
 */
public class Installments {

    /**
     * Installments
     */
    private final Map<String, List<Installment>> installments;

    /**
     * Initializes a new instance of the Installments class
     * 
     * @param installments
     */
    public Installments(Map<String, List<Installment>> installments) {
        this.installments = installments;
    }

    public List<Installment> get(String cardBrand) {
        final List<Installment> installmentList = installments.get(cardBrand);
        if (installmentList == null || installmentList.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(installmentList);
        return Collections.unmodifiableList(installmentList);
    }

}
