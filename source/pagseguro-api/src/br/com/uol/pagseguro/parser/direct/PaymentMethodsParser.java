/*
 ************************************************************************
 Copyright [2011] [PagSeguro Internet Ltda.]

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
package br.com.uol.pagseguro.parser.direct;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
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

import br.com.uol.pagseguro.domain.direct.PaymentMethod;
import br.com.uol.pagseguro.enums.PaymentMethodStatus;
import br.com.uol.pagseguro.enums.PaymentMethodType;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.logs.Logger;
import br.com.uol.pagseguro.xmlparser.XMLParserUtils;

/**
 * Parses a PaymentMethods XML in a List of Map object
 * 
 * @see InstallmentXml
 */
public class PaymentMethodsParser {

    /**
     * PagSeguro Log tool
     * 
     * @see Logger
     */
    private static Log log = new Log(PaymentMethodsParser.class);

    /**
     * Parses the XML response form PagSeguro web services
     * 
     * @param xmlInputStream
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ParseException
     */
    public static Map<PaymentMethodType, Map<Integer, PaymentMethod>> parse(InputStream xmlInputStream)
            throws ParserConfigurationException, SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        String tagValue = null;

        Element paymentMethodsElement = doc.getDocumentElement();
        // List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        Map<PaymentMethodType, Map<Integer, PaymentMethod>> paymentMethods = new HashMap<PaymentMethodType, Map<Integer, PaymentMethod>>();

        PaymentMethodsParser.log.debug("Parsing paymentMethods");

        // setting <installments><installment>
        if (paymentMethodsElement != null) {
            List<Element> itElements = XMLParserUtils.getElements("paymentMethod", paymentMethodsElement);

            for (int i = 0; i < itElements.size(); i++) {
                Element itElement = itElements.get(i);

                // setting <paymentMethods><paymentMethod><type>
                tagValue = XMLParserUtils.getTagValue("type", itElement);
                PaymentMethodType paymentMethodType = PaymentMethodType.fromValue(Integer.parseInt(tagValue));

                List<Element> itElementsOption = XMLParserUtils.getElements("option", itElement);

                for (int j = 0; j < itElementsOption.size(); j++) {
                    Element itElementOption = itElementsOption.get(j);

                    PaymentMethod paymentMethod = new PaymentMethod();

                    // setting <paymentMethods><paymentMethod><options><option><code>
                    tagValue = XMLParserUtils.getTagValue("code", itElementOption);
                    if (tagValue != null) {
                        paymentMethod.setCode(Integer.parseInt(tagValue));
                    }

                    // setting <paymentMethods><paymentMethod><options><option><name>
                    tagValue = XMLParserUtils.getTagValue("name", itElementOption);
                    if (tagValue != null) {
                        paymentMethod.setName(tagValue);
                    }

                    // setting <paymentMethods><paymentMethod><options><option><displayName>
                    tagValue = XMLParserUtils.getTagValue("displayName", itElementOption);
                    if (tagValue != null) {
                        paymentMethod.setDisplayName(tagValue);
                    }

                    // setting <paymentMethods><paymentMethod><options><option><status>
                    tagValue = XMLParserUtils.getTagValue("status", itElementOption);
                    if (tagValue != null) {
                        paymentMethod.setStatus(PaymentMethodStatus.valueOf(tagValue));
                    }

                    // adding item for map
                    Map<Integer, PaymentMethod> paymentMethodsByType = paymentMethods.get(paymentMethodType);

                    if (paymentMethodsByType == null) {
                        paymentMethodsByType = new HashMap<Integer, PaymentMethod>();
                        paymentMethods.put(paymentMethodType, paymentMethodsByType);
                    }

                    paymentMethodsByType.put(paymentMethod.getCode(), paymentMethod);

                }

            }

        }

        log.debug("Parsing transaction success: ");

        return paymentMethods;

    }
}
