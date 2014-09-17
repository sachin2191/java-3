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

import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestTransaction;
import br.com.uol.pagseguro.enums.PreApprovalPeriod;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.paymentrequest.PaymentRequestService;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

/**
 * 
 * Represents a pre-approval transaction
 *
 */
public class PreApproval {
	
	/**
     * The initial date that the pre-approval will be sent
     */
	private String initialDate;
	
	/** 
     * Payment request that will be sent by the pre-approval
     */
    private PaymentRequest paymentRequest;
    
	/**
     * Number of payment requests that will be sent
     */
    private Integer paymentRequestsQuantity;

    /**
     * Period in which the pre-approval will live
     */
    private PreApprovalPeriod period;

    /**
     * Initializes a new instance of the PreApproval class
     */
	public PreApproval(){
		
	}
	
	
	/**
	 * Initializes a new instance of the PreApproval class with the specified arguments
	 * 
	 * @param initialDate
	 * @param paymentRequest
	 * @param paymentRequestsQuantity
	 * @param period
	 */
	public PreApproval(String initialDate, PaymentRequest paymentRequest,
			Integer paymentRequestsQuantity, PreApprovalPeriod period) {
		super();
		this.initialDate = initialDate;
		this.paymentRequest = paymentRequest;
		this.paymentRequestsQuantity = paymentRequestsQuantity;
		this.period = period;
	}
	
	/**
	 * @return the initialDate
	 */
	public String getInitialDate() {
		return initialDate;
	}

	/**
	 * Sets the initialDate
	 * 
	 * @param initialDate
	 */
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	/**
	 * @return the paymentRequest
	 */
	public PaymentRequest getPaymentRequest() {
		return paymentRequest;
	}

	/**
	 * Sets the paymentRequest
	 * 
	 * @param paymentRequest
	 */
	public void setPaymentRequest(PaymentRequest paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	/**
	 * @return the paymentRequestsQuantity
	 */
	public Integer getPaymentRequestsQuantity() {
		return paymentRequestsQuantity;
	}

	/**
	 * Sets the paymentRequestsQuantity
	 * 
	 * @param paymentRequestsQuantity
	 */
	public void setPaymentRequestsQuantity(Integer paymentRequestsQuantity) {
		this.paymentRequestsQuantity = paymentRequestsQuantity;
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
     * Calls the PagSeguro web service and register this pre-approval request
     * 
     * @param credentials
     * @return The payment request code
     * @throws PagSeguroServiceException
     */
    public String register(Credentials credentials) throws PagSeguroServiceException {
        return PreApprovalService.createPreApproval(credentials, this);
    }
    
    /**
     * Calls the PagSeguro web service and return a payment request
     * 
     * @param credentials
     * @param paymentRequestCode
     * @return The payment request
     * @throws PagSeguroServiceException
     */
    public PaymentRequestTransaction search(Credentials credentials, String paymentRequestCode) throws PagSeguroServiceException {
        return PaymentRequestService.findByCode(credentials, paymentRequestCode);
    }

	/**
	 * @return string
	 */
	@Override
	public String toString() {
		return "PreApproval [initialDate=" + initialDate + ", paymentRequest="
				+ paymentRequest + ", paymentRequestsQuantity="
				+ paymentRequestsQuantity + ", period=" + period + "]";
	}
}
