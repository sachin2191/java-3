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
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.direct.InstallmentXml;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.logs.Logger;
import br.com.uol.pagseguro.xmlparser.XMLParserUtils;

/**
 * Parses a transaction XML in a List of <b>InstallmentXml</b> object
 * 
 * @see InstallmentXml
 */
public class InstallmentsParser {

    private InstallmentsParser() {
    }

    /**
     * PagSeguro Log tool
     * 
     * @see Logger
     */
    private static Log log = new Log(InstallmentsParser.class);

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
    public static List<InstallmentXml> readTransaction(InputStream xmlInputStream) throws ParserConfigurationException,
            SAXException, IOException, ParseException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(xmlInputStream);
        Document doc = dBuilder.parse(is);

        String tagValue = null;

        Element installmentsElement = doc.getDocumentElement();
        Transaction transaction = new Transaction();
        List<InstallmentXml> installments = new ArrayList<InstallmentXml>();

        InstallmentsParser.log.debug("Parsing transaction");

        // setting <installments><installment>
        // Element itemsElement = XMLParserUtils.getElement("installments", installmentsElement);
        if (installmentsElement != null) {
            List<Element> itElements = XMLParserUtils.getElements("installment", installmentsElement);

            for (int i = 0; i < itElements.size(); i++) {
                Element itElement = itElements.get(i);

                // setting <transaction><items><item>
                InstallmentXml installment = new InstallmentXml();

                // setting <transaction><items><item><id>
                tagValue = XMLParserUtils.getTagValue("cardBrand", itElement);
                if (tagValue != null) {
                    installment.setCardBrand(tagValue);
                }

                // setting <transaction><items><item><description>
                tagValue = XMLParserUtils.getTagValue("quantity", itElement);
                if (tagValue != null) {
                    installment.setQuantity(new BigDecimal(tagValue));
                }

                // setting <transaction><items><item><quantity>
                tagValue = XMLParserUtils.getTagValue("amount", itElement);
                if (tagValue != null) {
                    installment.setAmount(new BigDecimal(tagValue));
                }

                // setting <transaction><items><item><amount>
                tagValue = XMLParserUtils.getTagValue("totalAmount", itElement);
                if (tagValue != null) {
                    installment.setTotalAmount(new BigDecimal(tagValue));
                }

                // setting <transaction><items><item><amount>
                tagValue = XMLParserUtils.getTagValue("interestFree", itElement);
                if (tagValue != null) {
                    installment.setInterestFree(Boolean.valueOf(tagValue));
                }

                // adding item for items list
                installments.add(installment);
            }

        }

        InstallmentsParser.log.debug("Parsing transaction success: " + transaction.getCode());

        return installments;

    }
}
