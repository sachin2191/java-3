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
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;

/**
 * Represents a PagSeguro pre-approval request transaction
 */
public class PreApprovalRequestTransaction {

    /** Pre-approval request name */
    private String name;

    /** Pre-approval request name */
    private String code;

    /** Pre-approval request creation date */
    private String creationDate;

    /** Pre-approval request tracker code */
    private String tracker;

    /** Pre-approval request status */
    private String status;

    /** Pre-approval request last event date */
    private String lastEventDate;

    /** Pre-approval request reference */
    private String reference;

    /** Pre-approval request management type */
    private String managementType;

    /** Pre-approval request sender information */
    private Sender sender;

    /** Pre-approval request sender address information */
    private Address senderAddress;

    /** Pre-approval request sender phone information */
    private Phone senderPhone;
    /**
     * Initializes a new instance of the PreApprovalRequestTransaction class
     */
    public PreApprovalRequestTransaction() {

    }

    /**
     * Initializes a new instance of the PreApprovalRequestTransaction class with the specified arguments
     *
     * @param name
     * @param code
     * @param creationDate
     * @param tracker
     * @param status
     * @param lastEventDate
     * @param reference
     * @param managementType
     * @param sender
     */
    public PreApprovalRequestTransaction(final String name, final String code, final String creationDate,
            final String tracker, final String status, final String lastEventDate, final String reference,
            final String managementType, final Sender sender, final Address senderAddress, final Phone senderPhone) {

        this.name = name;
        this.code = code;
        this.creationDate = creationDate;
        this.tracker = tracker;
        this.status = status;
        this.lastEventDate = lastEventDate;
        this.reference = reference;
        this.managementType = managementType;
        this.sender = sender;
        this.senderAddress = senderAddress;
        this.senderPhone = senderPhone;
    }

    /**
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets pre-approval request name
     *
     * @param name
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * @return code
     */
    public String getCode() {

        return code;
    }

    /**
     * Sets pre-approval request code
     *
     * @param code
     */
    public void setCode(final String code) {

        this.code = code;
    }

    /**
     * @return creationDate
     */
    public String getCreationDate() {

        return creationDate;
    }

    /**
     * Sets pre-approval request creationDate
     *
     * @param creationDate
     */
    public void setCreationDate(final String creationDate) {

        this.creationDate = creationDate;
    }

    /**
     * @return tracker
     */
    public String getTracker() {

        return tracker;
    }

    /**
     * Sets pre-approval request tracker code
     *
     * @param tracker
     */
    public void setTracker(final String tracker) {

        this.tracker = tracker;
    }

    /**
     * @return status
     */
    public String getStatus() {

        return status;
    }

    /**
     * Sets pre-approval request status
     *
     * @param status
     */
    public void setStatus(final String status) {

        this.status = status;
    }

    /**
     * @return lastEventDate
     */
    public String getLastEventDate() {

        return lastEventDate;
    }

    /**
     * Sets pre-approval request last event date
     *
     * @param lastEventDate
     */
    public void setLastEventDate(final String lastEventDate) {

        this.lastEventDate = lastEventDate;
    }

    /**
     * @return reference
     */
    public String getReference() {

        return reference;
    }

    /**
     * Sets pre-approval request reference
     *
     * @param reference
     */
    public void setReference(final String reference) {

        this.reference = reference;
    }

    /**
     * @return managementType
     */
    public String getManagementType() {

        return managementType;
    }

    /**
     * Sets pre-approval request management type
     *
     * @param managementType
     */
    public void setManagementType(final String managementType) {

        this.managementType = managementType;
    }

    /**
     * @return sender
     */
    public Sender getSender() {

        return sender;
    }

    /**
     * Sets pre-approval request sender information
     *
     * @param sender
     */
    public void setSender(final Sender sender) {

        this.sender = sender;
    }

    /**
     * @return senderAddress
     */
    public Address getSenderAddress() {

        return senderAddress;
    }

    /**
     * Sets pre-approval request sender address information
     *
     * @param senderAddress
     */
    public void setSenderAddress(final Address senderAddress) {

        this.senderAddress = senderAddress;
    }

    /**
     * @return senderPhone
     */
    public Phone getSenderPhone() {

        return senderPhone;
    }

    /**
     * Sets pre-approval request sender phone information
     *
     * @param senderPhone
     */
    public void setSenderPhone(final Phone senderPhone) {

        this.senderPhone = senderPhone;
    }
}
