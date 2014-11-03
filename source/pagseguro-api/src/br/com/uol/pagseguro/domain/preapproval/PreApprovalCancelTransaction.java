package br.com.uol.pagseguro.domain.preapproval;

import java.util.Date;

public class PreApprovalCancelTransaction {
    /** Pre-approval status */
    private String status;

    /** Pre-approval cancel date */
    private Date date;

    /**
     * Initializes a new instance of the PreApprovalCancelTransaction class
     */
    public PreApprovalCancelTransaction() {

    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the initialDate
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the cancel date
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder()//
                .append("PreApprovalCancelTransaction [")//
                .append("status=\"")//
                .append(status + "\"")//
                .append(",date=")//
                .append(date)//
                .append("]");
        return builder.toString();
    }
}
