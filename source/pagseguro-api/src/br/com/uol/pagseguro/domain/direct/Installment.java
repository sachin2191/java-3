package br.com.uol.pagseguro.domain.direct;

import java.math.BigDecimal;

/**
 * Represents the definition of intallment of a credit card payment
 */
public class Installment {

    /** Quantity of installments */
    private Integer quantity;

    /** value of each installment */
    private BigDecimal value;

    /**
     * Initializes a new instance of the Installment class
     */
    public Installment() {

    }

    /**
     * Initializes a new instance of the Installment class
     * 
     * @param quantity
     * @param value
     */
    public Installment(Integer quantity, BigDecimal value) {
        super();
        this.quantity = quantity;
        this.value = value;
    }

    /**
     * @return the quantity of installments
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity of installments to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the value of eatch installment
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value
     *            the value of eatch installment to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Installment [quantity=" + quantity + ", value=" + value + "]";
    }

}
