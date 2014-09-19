/*
 * ***********************************************************************
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
 * ***********************************************************************
 */

package br.com.uol.pagseguro.parser.preapproval;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequest;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestItem;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestSender;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShipping;
import br.com.uol.pagseguro.domain.paymentrequest.PaymentRequestShippingPackage;
import br.com.uol.pagseguro.domain.preapproval.PreApproval;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalTransaction;
import br.com.uol.pagseguro.enums.PreApprovalPeriod;
import br.com.uol.pagseguro.enums.PreApprovalStatus;
import br.com.uol.pagseguro.helper.PagSeguroUtil;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.logs.Logger;
import br.com.uol.pagseguro.parser.paymentrequest.PaymentRequestParser;
import br.com.uol.pagseguro.xmlparser.XMLParserUtils;

/**
 * Parses pre-approval requests and responses
 */
public class PreApprovalParser {

    /**
     * PagSeguro Log tool
     * 
     * @see Logger
     */
    private static Log log = new Log(PreApprovalParser.class);

    public static String PRE_APPROVAL_INITIAL_DATE = "initialDate";
    public static String PRE_APPROVAL_PAYMENT_REQUESTS_QUANTITY = "paymentRequestsQuantity";
    public static String PRE_APPROVAL_PERIOD = "period";

    private PreApprovalParser() {

    }

    /**
     * 
     * @param preApproval
     * @return mixed
     */
    public static Map<Object, Object> getData(PreApproval preApproval) {
        Map<Object, Object> data = new HashMap<Object, Object>();

        /**
         * Set pre-approval initial date
         */
        if (preApproval.getInitialDate() != null)
            data.put(PRE_APPROVAL_INITIAL_DATE, preApproval.getInitialDate());

        /**
         * Set payment request
         */
        if (preApproval.getPaymentRequest() != null) {
            /**
             * Set payment request name
             */
            if (preApproval.getPaymentRequest().getName() != null)
                data.put(PaymentRequestParser.PAYMENT_REQUEST_NAME, preApproval.getPaymentRequest().getName());

            /**
             * Set payment request reference
             */
            if (preApproval.getPaymentRequest().getReference() != null)
                data.put(PaymentRequestParser.PAYMENT_REQUEST_REFERENCE, preApproval.getPaymentRequest().getReference());

            /**
             * Set payment request expiration days
             */
            if (preApproval.getPaymentRequest().getExpiration() != null)
                data.put(PaymentRequestParser.PAYMENT_REQUEST_EXPIRATION, preApproval.getPaymentRequest()
                        .getExpiration());

            /**
             * Set payment request due days
             */
            if (preApproval.getPaymentRequest().getDue() != null)
                data.put(PaymentRequestParser.PAYMENT_REQUEST_DUE, preApproval.getPaymentRequest().getDue());

            /**
             * Set payment request sender information
             * 
             * @see PaymentRequestSender
             */
            if (preApproval.getPaymentRequest().getSender() != null) {

                /**
                 * Set payment request sender email
                 */
                if (preApproval.getPaymentRequest().getSender().getEmail() != null)
                    data.put(PaymentRequestParser.PAYMENT_REQUEST_SENDER_EMAIL, preApproval.getPaymentRequest()
                            .getSender().getEmail());

                /**
                 * Set payment request sender name
                 */
                if (preApproval.getPaymentRequest().getSender().getName() != null)
                    data.put(PaymentRequestParser.PAYMENT_REQUEST_SENDER_NAME, preApproval.getPaymentRequest()
                            .getSender().getName());
            }

            /**
             * Set payment request items
             * 
             * @see PaymentRequestItem
             */
            if (preApproval.getPaymentRequest().getItems() != null
                    && !preApproval.getPaymentRequest().getItems().isEmpty()) {
                Integer count = 1;

                for (PaymentRequestItem item : preApproval.getPaymentRequest().getItems()) {
                    if (item.getDescription() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_ITEM_PREFIX + count.toString()
                                + PaymentRequestParser.PAYMENT_REQUEST_ITEM_DESCRIPTION, item.getDescription());

                    if (item.getAmount() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_ITEM_PREFIX + count.toString()
                                + PaymentRequestParser.PAYMENT_REQUEST_ITEM_AMOUNT, item.getAmount());

                    if (item.getQuantity() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_ITEM_PREFIX + count.toString()
                                + PaymentRequestParser.PAYMENT_REQUEST_ITEM_QUANTITY, item.getQuantity());

                    if (item.getId() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_ITEM_PREFIX + count.toString()
                                + PaymentRequestParser.PAYMENT_REQUEST_ITEM_ID, item.getId());

                    count++;
                }
            }

            /**
             * Set payment request shipping
             * 
             * @see PaymentRequestShipping
             */
            if (preApproval.getPaymentRequest().getShipping() != null) {
                if (preApproval.getPaymentRequest().getShipping().getCost() != null)
                    data.put(PaymentRequestParser.PAYMENT_REQUEST_SHIPPING_COST, preApproval.getPaymentRequest()
                            .getShipping().getCost());

                if (preApproval.getPaymentRequest().getShipping().getPaymentRequestShippingPackage() != null) {
                    if (preApproval.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_SHIPPING_PACKAGE_WIDTH, preApproval
                                .getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth());

                    if (preApproval.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getHeight() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_SHIPPING_PACKAGE_HEIGHT, preApproval
                                .getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth());

                    if (preApproval.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getLength() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_SHIPPING_PACKAGE_LENGTH, preApproval
                                .getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth());

                    if (preApproval.getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWeight() != null)
                        data.put(PaymentRequestParser.PAYMENT_REQUEST_SHIPPING_PACKAGE_WEIGHT, preApproval
                                .getPaymentRequest().getShipping().getPaymentRequestShippingPackage().getWidth());
                }
            }
        }

        /**
         * Set payment requests quantity
         */
        if (preApproval.getPaymentRequestsQuantity() != null)
            data.put(PRE_APPROVAL_PAYMENT_REQUESTS_QUANTITY, preApproval.getPaymentRequestsQuantity());

        /**
         * Set period
         */
        if (preApproval.getPeriod() != null)
            data.put(PRE_APPROVAL_PERIOD, preApproval.getPeriod().toString());

        return data;
    }

    /**
     * Reads the pre-approval request code when the request is successful
     * 
     * @param connection
     * @return payment request code
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static String readSuccessXml(HttpURLConnection connection) throws ParserConfigurationException,
            SAXException, IOException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(connection.getInputStream());
        Element paymentRequestReturnElement = doc.getDocumentElement();

        return XMLParserUtils.getTagValue("code", paymentRequestReturnElement);
    }

    /**
     * Reads the pre-approval cancel information
     * 
     * @param connection
     * @return payment request code
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static HashMap<String, String> readCancelXml(InputStream xmlInputStream)
            throws ParserConfigurationException, SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        Element paymentRequestReturnElement = doc.getDocumentElement();
        HashMap<String, String> cancelReturn = new HashMap<String, String>();
        String tagValue = null;

        // parsing <recurrenceCancel><code>
        tagValue = XMLParserUtils.getTagValue("code", paymentRequestReturnElement);
        if (tagValue != null)
            cancelReturn.put("code", tagValue);

        // parsing <recurrenceCancel><date>
        tagValue = XMLParserUtils.getTagValue("date", paymentRequestReturnElement);
        if (tagValue != null)
            cancelReturn.put("date", tagValue);

        // parsing <recurrenceCancel><status>
        tagValue = XMLParserUtils.getTagValue("status", paymentRequestReturnElement);
        if (tagValue != null)
            cancelReturn.put("status", tagValue);

        return cancelReturn;
    }

    public static PreApprovalTransaction readPreApproval(InputStream xmlInputStream)
            throws ParserConfigurationException, SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        String tagValue = null;

        Element preApprovalElement = doc.getDocumentElement();

        PreApprovalTransaction preApprovalTransaction = new PreApprovalTransaction();

        PreApprovalParser.log.debug("Parsing pre-approval");

        // parsing <recurrence><code>
        tagValue = XMLParserUtils.getTagValue("code", preApprovalElement);
        if (tagValue != null) {
            preApprovalTransaction.setCode(tagValue);
        }

        // parsing <recurrence><status>
        tagValue = XMLParserUtils.getTagValue("status", preApprovalElement);
        if (tagValue != null) {
            preApprovalTransaction.setStatus(PreApprovalStatus.fromValue(tagValue.charAt(0)));
        }

        // parsing <recurrence><period>
        tagValue = XMLParserUtils.getTagValue("period", preApprovalElement);
        if (tagValue != null) {
            preApprovalTransaction.setPeriod(PreApprovalPeriod.valueOf(tagValue));
        }

        // parsing <recurrence><paymentRequestsQuantity>
        tagValue = XMLParserUtils.getTagValue("paymentRequestsQuantity", preApprovalElement);
        if (tagValue != null) {
            preApprovalTransaction.setPaymentRequestsQuantity(Integer.valueOf(tagValue));
        }

        // parsing <recurrence><initialDate>
        tagValue = XMLParserUtils.getTagValue("initialDate", preApprovalElement);
        if (tagValue != null) {
            preApprovalTransaction.setInitialDate(PagSeguroUtil.parse(tagValue));
        }

        // parsing <recurrence><paymentRequest>
        Element paymentRequestElement = XMLParserUtils.getElement("paymentRequest", preApprovalElement);
        if (paymentRequestElement != null) {
            PaymentRequest paymentRequest = new PaymentRequest();

            // parsing <recurrence><paymentRequest><sender>
            Element senderElement = XMLParserUtils.getElement("sender", paymentRequestElement);
            if (senderElement != null) {

                PaymentRequestSender sender = new PaymentRequestSender();

                // setting <recurrence><paymentRequest><sender><email>
                tagValue = XMLParserUtils.getTagValue("email", senderElement);
                if (tagValue != null) {
                    sender.setEmail(tagValue);
                }

                // setting <recurrence><paymentRequest><sender><name>
                tagValue = XMLParserUtils.getTagValue("name", senderElement);
                if (tagValue != null) {
                    sender.setName(tagValue);
                }

                paymentRequest.setSender(sender);
            }

            // parsing <recurrence><paymentRequest><items>
            Element itemsElement = XMLParserUtils.getElement("items", paymentRequestElement);
            if (itemsElement != null) {
                List<Element> itElements = XMLParserUtils.getElements("item", itemsElement);
                List<PaymentRequestItem> items = new ArrayList<PaymentRequestItem>();

                for (Element itElement : itElements) {

                    // setting <recurrence><paymentRequest><items><item>
                    PaymentRequestItem item = new PaymentRequestItem();

                    // setting <recurrence><paymentRequest><items><item><description>
                    tagValue = XMLParserUtils.getTagValue("description", itElement);
                    if (tagValue != null) {
                        item.setDescription(tagValue);
                    }

                    // setting <recurrence><paymentRequest><items><item><amount>
                    tagValue = XMLParserUtils.getTagValue("amount", itElement);
                    if (tagValue != null) {
                        item.setAmount(new BigDecimal(tagValue));
                    }

                    // setting <recurrence><paymentRequest><items><item><quantity>
                    tagValue = XMLParserUtils.getTagValue("quantity", itElement);
                    if (tagValue != null) {
                        item.setQuantity(Integer.valueOf(tagValue));
                    }

                    // setting <recurrence<paymentRequest><items><item><id>
                    tagValue = XMLParserUtils.getTagValue("id", itElement);
                    if (tagValue != null) {
                        item.setId(tagValue);
                    }

                    // adding item for items list
                    items.add(item);
                }

                paymentRequest.setItems(items);
            }

            // parsing <recurrence><paymentRequest><shipping>
            Element shippingElement = XMLParserUtils.getElement("shipping", paymentRequestElement);
            if (shippingElement != null) {

                // creating new PaymentRequestShipping object
                PaymentRequestShipping shipping = new PaymentRequestShipping();

                // setting <recurrence><paymentRequest><shipping><cost>
                tagValue = XMLParserUtils.getTagValue("cost", shippingElement);
                if (tagValue != null) {
                    shipping.setCost(new BigDecimal(tagValue));
                }

                // parsing <recurrence><paymentRequest><shipping><package>
                Element packageElement = XMLParserUtils.getElement("package", shippingElement);
                if (packageElement != null) {

                    // creating new PaymentRequestShippingPackage object
                    PaymentRequestShippingPackage shippingPackage = new PaymentRequestShippingPackage();

                    // setting <recurrence><paymentRequest><shipping><package><weight>
                    tagValue = XMLParserUtils.getTagValue("weight", packageElement);
                    if (tagValue != null) {
                        shippingPackage.setWeight(Integer.valueOf(tagValue));
                    }

                    // setting <recurrence><paymentRequest><shipping><package><width>
                    tagValue = XMLParserUtils.getTagValue("width", packageElement);
                    if (tagValue != null) {
                        shippingPackage.setWidth(Integer.valueOf(tagValue));
                    }

                    // setting <recurrence><paymentRequest><shipping><package><height>
                    tagValue = XMLParserUtils.getTagValue("height", packageElement);
                    if (tagValue != null) {
                        shippingPackage.setHeight(Integer.valueOf(tagValue));
                    }

                    // setting <recurrence><paymentRequest><shipping><package><length>
                    tagValue = XMLParserUtils.getTagValue("length", packageElement);
                    if (tagValue != null) {
                        shippingPackage.setLength(Integer.valueOf(tagValue));
                    }

                    shipping.setPaymentRequestPackage(shippingPackage);
                }

                // setting <recurrence><paymentRequest><shipping>
                paymentRequest.setShipping(shipping);
            }

            // parsing <recurrence><paymentRequest><name>
            tagValue = XMLParserUtils.getTagValue("name", paymentRequestElement);
            if (tagValue != null) {
                paymentRequest.setName(tagValue);
            }

            // parsing <recurrence><paymentRequest><description>
            tagValue = XMLParserUtils.getTagValue("description", paymentRequestElement);
            if (tagValue != null) {
                paymentRequest.setDescription(tagValue);
            }

            // parsing <recurrence><paymentRequest><expiration>
            tagValue = XMLParserUtils.getTagValue("expiration", paymentRequestElement);
            if (tagValue != null) {
                paymentRequest.setExpiration(Integer.valueOf(tagValue));
            }

            // parsing <recurrence><paymentRequest><reference>
            tagValue = XMLParserUtils.getTagValue("reference", paymentRequestElement);
            if (tagValue != null) {
                paymentRequest.setReference(tagValue);
            }

            // parsing <recurrence><paymentRequest><due>
            tagValue = XMLParserUtils.getTagValue("due", paymentRequestElement);
            if (tagValue != null) {
                paymentRequest.setDue(Integer.valueOf(tagValue));
            }

            preApprovalTransaction.setPaymentRequest(paymentRequest);
        }

        PreApprovalParser.log.debug("Parsing pre-approval success: " + preApprovalTransaction.getCode());

        return preApprovalTransaction;
    }
}