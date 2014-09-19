/*
 ************************************************************************
 Copyright [2014] [PagSeguro Internet Ltda.]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ************************************************************************
 */

package br.com.uol.pagseguro.domain.preapproval;

import java.util.Date;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.enums.PreApprovalPeriod;
import br.com.uol.pagseguro.enums.PreApprovalStatus;

/**
 * Represents a PagSeguro pre-approval transaction
 */
public class PreApprovalTransaction {

    /** Pre-approval code */
    private String code;

    /** Pre-approval status */
    private PreApprovalStatus status;

    /** Pre-approval period */
    private PreApprovalPeriod period;

    /** Payment requests quantity */
    private Integer paymentRequestsQuantity;

    /** Pre-approval initial date */
    private Date initialDate;

    /** Transaction payment request */
    private PaymentRequest paymentRequest;

    /**
     * Initializes a new instance of the PreApprovalTransaction class
     */
    public PreApprovalTransaction() {

        this.paymentRequest = new PaymentRequest();
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the status
     */
    public PreApprovalStatus getStatus() {
        return status;
    }

    /**
     * Sets the status
     * 
     * @param status
     */
    public void setStatus(PreApprovalStatus status) {
        this.status = status;
    }

    /**
     * @return the period
     */
    public PreApprovalPeriod getPeriod() {
        return period;
    }

    /**
     * Sets the period
     * 
     * @param period
     */
    public void setPeriod(PreApprovalPeriod period) {
        this.period = period;
    }

    /**
     * @return the paymentRequestsQuantity
     */
    public Integer getPaymentRequestsQuantity() {
        return paymentRequestsQuantity;
    }

    /**
     * Sets the payment requests quantity
     * 
     * @param paymentRequestsQuantity
     */
    public void setPaymentRequestsQuantity(Integer paymentRequestsQuantity) {
        this.paymentRequestsQuantity = paymentRequestsQuantity;
    }

    /**
     * @return the initialDate
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the initial date
     * 
     * @param initialDate
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * @return the paymentRequest
     */
    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    /**
     * Sets the payment request
     * 
     * @param paymentRequest
     */
    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PreApprovalTransaction [code=" + code + ", status=" + status + ", period=" + period
                + ", paymentRequestQuantity=" + paymentRequestsQuantity + ", initialDate=" + initialDate
                + ", paymentRequest=" + paymentRequest + "]";
    }
}