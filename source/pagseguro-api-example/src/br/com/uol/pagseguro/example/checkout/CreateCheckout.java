package br.com.uol.pagseguro.example.checkout;

import java.math.BigDecimal;

import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.MetaDataItemKey;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreateCheckout {
    /**
     * Class with a main method to illustrate the usage of the domain class Checkout
     */
    public static void main(String[] args) {

        Checkout checkout = new Checkout();

        checkout.addItem("0001", //
                "Notebook Lilás", //
                Integer.valueOf(1), //
                new BigDecimal("2430.00"), //
                new Long(1000), null);

        checkout.addItem("0002", //
                "Notebook Rosa", //
                Integer.valueOf(2), //
                new BigDecimal("2560.00"), //
                new Long(750), null);

        checkout.setShippingAddress("BRA", //
                "SP", //
                "Sao Paulo", //
                "Jardim Paulistano", //
                "01452002", //
                "Av. Brig. Faria Lima", //
                "1384", //
                "5o andar");

        checkout.setShippingType(ShippingType.SEDEX);

        checkout.setShippingCost(new BigDecimal("2.02"));

        checkout.setSender("João Comprador", //
                "comprador@uol.com.br", //
                "11", //
                "56273440", //
                DocumentType.CPF, //
                "000.000.001-91");

        checkout.setCurrency(Currency.BRL);

        // Sets a reference code for this payment request, it's useful to
        // identify this payment in future notifications
        checkout.setReference("REF1234");

        checkout.setNotificationURL("http://www.meusite.com.br/notification");

        checkout.setRedirectURL("http://www.meusite.com.br/redir");

        // Another way to set checkout parameters
        checkout.addParameter("senderBornDate", //
                "07/05/1981");

        checkout.addIndexedParameter("itemId", //
                "0003", //
                3);

        checkout.addIndexedParameter("itemDescription", //
                "Notebook Preto", //
                3);

        checkout.addIndexedParameter("itemQuantity", //
                "1", //
                3);

        checkout.addIndexedParameter("itemAmount", //
                "200.00", //
                3);

        checkout.addIndexedParameter("itemWeight", //
                "320", //
                3);

        checkout.addMetaDataItem(MetaDataItemKey.PASSENGER_CPF, //
                "15600944276", //
                1);

        checkout.addMetaDataItem(MetaDataItemKey.GAME_NAME, //
                "DOTA");

        checkout.addMetaDataItem(MetaDataItemKey.PASSENGER_PASSPORT, //
                "23456", //
                1);

        try {

            Boolean onlyCheckoutCode = false;

            // Set your account credentials on src/pagseguro-config.properties
            String checkoutURL = checkout.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode);

            System.out.println(checkoutURL);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private CreateCheckout() {

    }
}