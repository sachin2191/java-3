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

package br.com.uol.pagseguro.parser.preapproval;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Receiver;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShippingPackage;
import br.com.uol.pagseguro.domain.preapproval.PreApproval;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalCancelTransaction;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequest;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequestTransaction;
import br.com.uol.pagseguro.helper.PagSeguroUtil;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.xmlparser.XMLParserUtils;

/**
 * Parses pre-approval requests and responses
 */
public class PreApprovalParser {

    /**
     * PagSeguro Log tool
     *
     * @see br.com.uol.pagseguro.logs.Logger
     */
    private static final Log log = new Log(PreApprovalParser.class);

    private static final String SENDER_NAME = "senderName";
    private static final String SENDER_EMAIL = "senderEmail";
    private static final String SENDER_PHONE_AREA_CODE = "senderAreaCode";
    private static final String SENDER_PHONE = "senderPhone";
    private static final String SENDER_ADDRESS_STREET = "senderStreet";
    private static final String SENDER_ADDRESS_NUMBER = "senderNumber";
    private static final String SENDER_ADDRESS_COMPLEMENT = "senderComplement";
    private static final String SENDER_ADDRESS_DISTRICT = "senderDistrict";
    private static final String SENDER_ADDRESS_CITY = "senderCity";
    private static final String SENDER_ADDRESS_STATE = "senderState";
    private static final String SENDER_ADDRESS_COUNTRY = "senderCountry";
    private static final String SENDER_ADDRESS_POSTAL_CODE = "senderPostalCode";
    private static final String RECEIVER_EMAIL = "receiverEmail";
    private static final String REFERENCE = "reference";
    private static final String DETAILS = "preApprovalDetails";
    private static final String PERIOD = "preApprovalPeriod";
    private static final String MAX_AMOUNT_PER_PERIOD = "preApprovalMaxAmountPerPeriod";
    private static final String AMOUNT_PER_PAYMENT = "preApprovalAmountPerPayment";
    private static final String MAX_AMOUNT_PER_PAYMENT = "preApprovalMaxAmountPerPayment";
    private static final String MAX_TOTAL_AMOUNT = "preApprovalMaxTotalAmount";
    private static final String MAX_PAYMENTS_PER_PERIOD = "preApprovalMaxPaymentsPerPeriod";
    private static final String INITIAL_DATE = "preApprovalInitialDate";
    private static final String FINAL_DATE = "preApprovalFinalDate";
    private static final String DAY_OF_YEAR = "preApprovalDayOfYear";
    private static final String DAY_OF_MONTH = "preApprovalDayOfMonth";
    private static final String DAY_OF_WEEK = "preApprovalDayOfWeek";
    private static final String NAME = "preApprovalName";
    private static final String CANCEL_URL = "preApprovalCancelUrl";
    private static final String CHARGE = "preApprovalCharge";
    private static final String MAX_USES = "maxUses";
    private static final String PAYMENT_DUE = "paymentDue";
    private static final String PAYMENT_EXPIRATION = "paymentExpiration";
    private static final String SHIPPING_COST = "shippingCost";
    private static final String SHIPPING_PACKAGE_HEIGHT = "shippingPackageHeight";
    private static final String SHIPPING_PACKAGE_WIDTH = "shippingPackageLength";
    private static final String SHIPPING_PACKAGE_LENGTH = "shippingPackageWeight";
    private static final String SHIPPING_PACKAGE_WEIGHT = "shippingPackageWidth";

    private PreApprovalParser() {

    }

    /**
     *
     * @param preApprovalRequest
     * @return mixed
     */
    public static Map<Object, Object> getData(PreApprovalRequest preApprovalRequest) {
        Map<Object, Object> data = new HashMap<Object, Object>();

        /*
         * Set sender
         */
        if (preApprovalRequest.getSender() != null) {
            Sender sender = preApprovalRequest.getSender();

            if (sender.getName() != null) {
                data.put(SENDER_NAME, sender.getName());
            }

            if (sender.getEmail() != null) {
                data.put(SENDER_EMAIL, sender.getEmail());
            }

            /*
             * Set sender phone
             */
            if (sender.getPhone() != null) {
                Phone phone = sender.getPhone();

                if (phone.getAreaCode() != null) {
                    data.put(SENDER_PHONE_AREA_CODE, phone.getAreaCode());
                }

                if (phone.getNumber() != null) {
                    data.put(SENDER_PHONE, phone.getNumber());
                }
            }

            /*
             * Set sender address
             */
            if (preApprovalRequest.getSenderAddress() != null) {
                Address address = preApprovalRequest.getSenderAddress();

                if (address.getNumber() != null) {
                    data.put(SENDER_ADDRESS_NUMBER, address.getNumber());
                }

                if (address.getCity() != null) {
                    data.put(SENDER_ADDRESS_CITY, address.getCity());
                }

                if (address.getComplement() != null) {
                    data.put(SENDER_ADDRESS_COMPLEMENT, address.getComplement());
                }

                if (address.getCountry() != null) {
                    data.put(SENDER_ADDRESS_COUNTRY, address.getCountry());
                }

                if (address.getDistrict() != null) {
                    data.put(SENDER_ADDRESS_DISTRICT, address.getDistrict());
                }

                if (address.getPostalCode() != null) {
                    data.put(SENDER_ADDRESS_POSTAL_CODE, address.getPostalCode());
                }

                if (address.getState() != null) {
                    data.put(SENDER_ADDRESS_STATE, address.getState());
                }

                if (address.getStreet() != null) {
                    data.put(SENDER_ADDRESS_STREET, address.getStreet());
                }
            }
        }

        /*
         * Set receiver
         */
        if (preApprovalRequest.getReceiver() != null) {
            Receiver receiver = preApprovalRequest.getReceiver();

            if (receiver.getEmail() != null) {
                data.put(RECEIVER_EMAIL, receiver.getEmail());
            }
        }

        /*
         * Set reference
         */
        if (preApprovalRequest.getReference() != null) {
            data.put(REFERENCE, preApprovalRequest.getReference());
        }

        /*
         * Set maxUses
         */
        if (preApprovalRequest.getMaxUses() != null) {
            data.put(MAX_USES, preApprovalRequest.getMaxUses());
        }

        /*
         * Set paymentDue
         */
        if (preApprovalRequest.getPaymentDue() != null) {
            data.put(PAYMENT_DUE, preApprovalRequest.getPaymentDue());
        }

        /*
         * Set paymentExpiration
         */
        if (preApprovalRequest.getPaymentExpiration() != null) {
            data.put(PAYMENT_EXPIRATION, preApprovalRequest.getPaymentExpiration());
        }

        /*
         * Set paymentRequestShipping
         */

        if (preApprovalRequest.getPaymentRequestShipping() != null) {
            PaymentRequestShipping paymentRequestShipping = preApprovalRequest.getPaymentRequestShipping();

            // Shipping cost
            if (paymentRequestShipping.getCost() != null) {
                data.put(SHIPPING_COST, paymentRequestShipping.getCost());
            }

            // Shipping package
            if (paymentRequestShipping.getPaymentRequestShippingPackage() != null) {
                PaymentRequestShippingPackage paymentRequestShippingPackage = paymentRequestShipping
                        .getPaymentRequestShippingPackage();

                if (paymentRequestShippingPackage.getWidth() != null) {
                    data.put(SHIPPING_PACKAGE_WIDTH, paymentRequestShippingPackage.getWidth());
                }

                if (paymentRequestShippingPackage.getHeight() != null) {
                    data.put(SHIPPING_PACKAGE_HEIGHT, paymentRequestShippingPackage.getWidth());
                }

                if (paymentRequestShippingPackage.getLength() != null) {
                    data.put(SHIPPING_PACKAGE_LENGTH, paymentRequestShippingPackage.getWidth());
                }

                if (paymentRequestShippingPackage.getWeight() != null) {
                    data.put(SHIPPING_PACKAGE_WEIGHT, paymentRequestShippingPackage.getWidth());
                }
            }
        }

        /*
         * Set preApproval
         */
        if (preApprovalRequest.getPreApproval() != null) {
            PreApproval preApproval = preApprovalRequest.getPreApproval();

            if (preApproval.getManagementType() != null) {
                data.put(CHARGE, preApproval.getManagementType().name().toLowerCase());
            }

            if (preApproval.getName() != null) {
                data.put(NAME, preApproval.getName());
            }

            if (preApproval.getDetails() != null) {
                data.put(DETAILS, preApproval.getDetails());
            }

            if (preApproval.getAmountPerPayment() != null) {
                data.put(AMOUNT_PER_PAYMENT, preApproval.getAmountPerPayment());
            }

            if (preApproval.getMaxAmountPerPayment() != null) {
                data.put(MAX_AMOUNT_PER_PAYMENT, preApproval.getMaxAmountPerPayment());

            }

            if (preApproval.getPeriod() != null) {
                data.put(PERIOD, preApproval.getPeriod().name());
            }

            if (preApproval.getDayOfWeek() != null) {
                data.put(DAY_OF_WEEK, preApproval.getDayOfWeek().name());
            }

            if (preApproval.getDayOfMonth() != null) {
                data.put(DAY_OF_MONTH, preApproval.getDayOfMonth());
            }

            if (preApproval.getDayOfYear() != null) {
                data.put(DAY_OF_YEAR, preApproval.getDayOfYear());
            }

            if (preApproval.getMaxPaymentsPerPeriod() != null) {
                data.put(MAX_PAYMENTS_PER_PERIOD, preApproval.getMaxPaymentsPerPeriod());
            }

            if (preApproval.getMaxAmountPerPeriod() != null) {
                data.put(MAX_AMOUNT_PER_PERIOD, preApproval.getMaxAmountPerPeriod());
            }

            if (preApproval.getInitialDate() != null) {
                data.put(INITIAL_DATE, preApproval.getInitialDate());
            }

            if (preApproval.getFinalDate() != null) {
                data.put(FINAL_DATE, preApproval.getFinalDate());
            }

            if (preApproval.getMaxTotalAmount() != null) {
                data.put(MAX_TOTAL_AMOUNT, preApproval.getMaxTotalAmount());
            }

            if (preApproval.getCancelURL() != null) {
                data.put(CANCEL_URL, preApproval.getCancelURL());
            }
        }

        return data;
    }

    /**
     * Reads the pre-approval request code when the it is successful
     *
     * @param connection
     * @return pre-approval request code
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public static String readSuccessXml(HttpURLConnection connection) throws ParserConfigurationException,
            SAXException, IOException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(connection.getInputStream());
        Element preApprovalrequestReturnElement = doc.getDocumentElement();

        return XMLParserUtils.getTagValue("code", preApprovalrequestReturnElement);
    }

    public static PreApprovalRequestTransaction readPreApprovalRequest(InputStream xmlInputStream)
            throws ParserConfigurationException, SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        String tagValue = null;

        Element preApprovalRequestElement = doc.getDocumentElement();

        PreApprovalRequestTransaction preApprovalRequestTransaction = new PreApprovalRequestTransaction();

        PreApprovalParser.log.debug("Parsing pre-approval request");

        // parsing <preApproval><name>
        tagValue = XMLParserUtils.getTagValue("name", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setName(tagValue);
        }

        // parsing <preApproval><code>
        tagValue = XMLParserUtils.getTagValue("code", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setCode(tagValue);
        }

        // parsing <preApproval><date>
        tagValue = XMLParserUtils.getTagValue("date", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setCreationDate(tagValue);
        }

        // parsing <preApproval><tracker>
        tagValue = XMLParserUtils.getTagValue("tracker", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setTracker(tagValue);
        }

        // parsing <preApproval><status>
        tagValue = XMLParserUtils.getTagValue("status", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setStatus(tagValue);
        }

        // parsing <preApproval><reference>
        tagValue = XMLParserUtils.getTagValue("reference", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setReference(tagValue);
        }

        // parsing <preApproval><lastEventDate>
        tagValue = XMLParserUtils.getTagValue("lastEventDate", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setLastEventDate(tagValue);
        }

        // parsing <preApproval><charge>
        tagValue = XMLParserUtils.getTagValue("charge", preApprovalRequestElement);
        if (tagValue != null) {
            preApprovalRequestTransaction.setManagementType(tagValue);
        }

        // parsing <preApproval><sender>
        Element senderElement = XMLParserUtils.getElement("sender", preApprovalRequestElement);
        if (senderElement != null) {
            Sender sender = new Sender();

            // setting <preApproval><sender><name>
            tagValue = XMLParserUtils.getTagValue("name", senderElement);
            if (tagValue != null) {
                sender.setName(tagValue);
            }

            // setting <preApproval><sender><email>
            tagValue = XMLParserUtils.getTagValue("email", senderElement);
            if (tagValue != null) {
                sender.setEmail(tagValue);
            }

            preApprovalRequestTransaction.setSender(sender);
        }

        PreApprovalParser.log.debug("Parsing pre-approval request success: " + preApprovalRequestTransaction.getCode());

        return preApprovalRequestTransaction;
    }

    /**
     * Reads the recurrence cancel information
     *
     * @return payment request code
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static PreApprovalCancelTransaction readCancelXml(InputStream xmlInputStream)
            throws ParserConfigurationException, SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        Element preApprovalReturnElement = doc.getDocumentElement();
        PreApprovalCancelTransaction preApprovalCancelTransaction = new PreApprovalCancelTransaction();
        String tagValue = null;

        // parsing <result><status>
        tagValue = XMLParserUtils.getTagValue("status", preApprovalReturnElement);
        if (tagValue != null)
            preApprovalCancelTransaction.setStatus(tagValue);

        // parsing <result><date>
        tagValue = XMLParserUtils.getTagValue("date", preApprovalReturnElement);
        if (tagValue != null)
            preApprovalCancelTransaction.setDate(PagSeguroUtil.parse(tagValue));

        return preApprovalCancelTransaction;
    }
}
