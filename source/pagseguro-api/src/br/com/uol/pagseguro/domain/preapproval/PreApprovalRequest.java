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

import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.Receiver;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

/**
 *
 * Represents a pre-approval request
 *
 */
public class PreApprovalRequest {

    /**
     * Party that will be sending the money
     */
    private Sender sender;

    /**
     * Sender address
     *
     * Optional
     */
    private Address senderAddress;

    /**
     * Party that will receive the money
     *
     * Optional
     */
    private Receiver receiver;

    /**
     * Identifier of the pre-approval
     *
     * Optional
     */
    private String reference;

    /**
     * Represents a pre-approval
     */
    private PreApproval preApproval;

    /**
     * Pre-approval request due days
     */
    private Integer paymentDue;

    /**
     * Pre-approval request expiration days
     */
    private Integer paymentExpiration;

    /**
     * Maximum number of requests of a pre-approval
     */
    private Integer maxUses;

    /**
     * Pre-approval request shipping information
     */
    private PaymentRequestShipping paymentRequestShipping;

    /**
     * Initializes a new instance of the PreApprovalRequest class
     */
    public PreApprovalRequest() {

    }

    /**
     * Initializes a new instance of the PreApprovalRequest class with the specified arguments
     *
     * @param sender
     * @param preApproval
     * @param paymentDue
     * @param paymentExpiration
     */
    public PreApprovalRequest(final Sender sender, final PreApproval preApproval, final Integer paymentDue,
            final Integer paymentExpiration) {

        this.sender = sender;
        this.preApproval = preApproval;
        this.paymentDue = paymentDue;
        this.paymentExpiration = paymentExpiration;
    }

    /**
     * Initializes a new instance of the PreApprovalRequest class with the specified arguments
     *
     * @param sender
     * @param senderAddress
     * @param receiver
     * @param reference
     * @param preApproval
     * @param maxUses
     */
    public PreApprovalRequest(final Sender sender, final Address senderAddress, final Receiver receiver,
            final String reference, final PreApproval preApproval, final Integer maxUses, final Integer paymentDue,
            final Integer paymentExpiration, final PaymentRequestShipping paymentRequestShipping) {

        this.sender = sender;
        this.senderAddress = senderAddress;
        this.receiver = receiver;
        this.reference = reference;
        this.preApproval = preApproval;
        this.maxUses = maxUses;
        this.paymentDue = paymentDue;
        this.paymentExpiration = paymentExpiration;
        this.paymentRequestShipping = paymentRequestShipping;
    }

    /**
     * @return sender
     */
    public Sender getSender() {

        return sender;
    }

    /**
     * Sets sender
     *
     * @param sender
     */
    public void setSender(final Sender sender) {

        this.sender = sender;
    }

    /**
     * @return sender address
     */
    public Address getSenderAddress() {

        return senderAddress;
    }

    /**
     * Sets sender address
     *
     * @param senderAddress
     */
    public void setSenderAddress(final Address senderAddress) {

        this.senderAddress = senderAddress;
    }

    /**
     * @return receiver
     */
    public Receiver getReceiver() {

        return receiver;
    }

    /**
     * Sets receiver
     *
     * @param receiver
     */
    public void setReceiver(final Receiver receiver) {

        this.receiver = receiver;
    }

    /**
     * @return reference
     */
    public String getReference() {

        return reference;
    }

    /**
     * Sets reference
     *
     * @param reference
     */
    public void setReference(final String reference) {

        this.reference = reference;
    }

    /**
     * @return preApproval
     */
    public PreApproval getPreApproval() {

        return preApproval;
    }

    /**
     * Sets preApproval
     *
     * @param preApproval
     */
    public void setPreApproval(final PreApproval preApproval) {

        this.preApproval = preApproval;
    }

    /**
     * @return maxUses
     */
    public Integer getMaxUses() {

        return maxUses;
    }

    /**
     * Sets maxUses
     *
     * @param maxUses
     */
    public void setMaxUses(final Integer maxUses) {

        this.maxUses = maxUses;
    }

    /**
     * @return maxUses
     */
    public Integer getPaymentDue() {

        return paymentDue;
    }

    /**
     * Sets paymentDue
     *
     * @param paymentDue
     */
    public void setPaymentDue(final Integer paymentDue) {

        this.paymentDue = paymentDue;
    }

    /**
     * @return paymentExpiration
     */
    public Integer getPaymentExpiration() {

        return paymentExpiration;
    }

    /**
     * Sets paymentExpiration
     *
     * @param paymentExpiration
     */
    public void setPaymentExpiration(final Integer paymentExpiration) {

        this.paymentExpiration = paymentExpiration;
    }

    /**
     * @return paymentRequestShipping
     */
    public PaymentRequestShipping getPaymentRequestShipping() {

        return paymentRequestShipping;
    }

    /**
     * Sets paymentRequestShipping
     *
     * @param paymentRequestShipping
     */
    public void setPaymentRequestShipping(final PaymentRequestShipping paymentRequestShipping) {

        this.paymentRequestShipping = paymentRequestShipping;
    }

    /**
     * Calls the PagSeguro web service and register this pre-approval request
     *
     * @param credentials
     * @return The pre-approval request code
     * @throws br.com.uol.pagseguro.exception.PagSeguroServiceException
     */
    public String register(Credentials credentials) throws PagSeguroServiceException {
        return PreApprovalService.createPreApprovalRequest(credentials, this);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder()//
                .append("PreApprovalRequest [")//
                .append("sender=")//
                .append(sender)//
                .append(",senderAddress=")//
                .append(senderAddress)//
                .append(",receiver=")//
                .append(receiver)//
                .append(",reference=\"")//
                .append(reference + "\"")//
                .append(",preApproval=")//
                .append(preApproval)//
                .append(",paymentDue=")//
                .append(paymentDue)//
                .append(",paymentExpiration=")//
                .append(paymentExpiration)//
                .append(",maxUses=")//
                .append(maxUses)//
                .append(",paymentRequestShipping=")//
                .append(paymentRequestShipping)//
                .append("]");
        return builder.toString();
    }
}
