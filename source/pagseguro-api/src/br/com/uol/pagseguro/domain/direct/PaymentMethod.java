package br.com.uol.pagseguro.domain.direct;

import br.com.uol.pagseguro.enums.PaymentMethodStatus;

/**
 * Payment Method
 */
public class PaymentMethod {

    private Integer code;
    private String name;
    private String displayName;
    private PaymentMethodStatus status;

    /**
     * Initializes a new instance of the PaymentMethod class
     */
    public PaymentMethod() {

    }

    /**
     * Initializes a new instance of the PaymentMethod class
     * 
     * @param code
     * @param name
     * @param displayName
     * @param status
     */
    public PaymentMethod(Integer code, String name, String displayName, PaymentMethodStatus status) {
        super();
        this.code = code;
        this.name = name;
        this.displayName = displayName;
        this.status = status;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the status
     */
    public PaymentMethodStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(PaymentMethodStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentMethod [code=" + code + ", name=" + name + ", displayName=" + displayName + ", status=" + status
                + "]";
    }

}
