package br.com.uol.pagseguro.domain.direct;

import java.math.BigDecimal;

/**
 * Represents the option of intallment of a credit card payment returned by PagSeguro API
 */
public class InstallmentXml {

    /** Credit card brand */
    private String cardBrand;

    /** Quantity of installments */
    private BigDecimal quantity;

    /** Value of eatch installment */
    private BigDecimal amount;

    /** Total value of installments */
    private BigDecimal totalAmount;

    /** Indicates if is an interest free transaction */
    private Boolean interestFree;

    /**
     * Initializes a new instance of the InstallmentXml class
     */
    public InstallmentXml() {

    }

    /**
     * Initializes a new instance of the InstallmentXml class
     * 
     * @param cardBrand
     * @param quantity
     * @param amount
     * @param totalAmount
     * @param interestree
     */
    public InstallmentXml(String cardBrand, BigDecimal quantity, BigDecimal amount, BigDecimal totalAmount,
            Boolean interestFree) {
        super();
        this.cardBrand = cardBrand;
        this.quantity = quantity;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.interestFree = interestFree;
    }

    /**
     * @return the cardBrand
     */
    public String getCardBrand() {
        return cardBrand;
    }

    /**
     * @param cardBrand
     *            the cardBrand to set
     */
    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    /**
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     *            the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the interestFree
     */
    public Boolean getInterestFree() {
        return interestFree;
    }

    /**
     * @param interestFree
     *            the interestFree to set
     */
    public void setInterestFree(Boolean interestFree) {
        this.interestFree = interestFree;
    }

    @Override
    public String toString() {
        return "InstallmentXml [cardBrand=" + cardBrand + ", quantity=" + quantity + ", amount=" + amount
                + ", totalAmount=" + totalAmount + ", interestFree=" + interestFree + "]";
    }

}
