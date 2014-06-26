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
package br.com.uol.pagseguro.example;

import java.util.Map;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.direct.PaymentMethod;
import br.com.uol.pagseguro.domain.direct.PaymentMethods;
import br.com.uol.pagseguro.enums.PaymentMethodCode;
import br.com.uol.pagseguro.enums.PaymentMethodType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.PaymentMethodService;

/**
 * Class with a main method to illustrate the usage of the InstallmentService to get the installments available
 */
public class GetPaymentMethods {

    public static void main(String[] args) {
        PaymentMethods paymentMethods;
        String publicKey = "abc";

        try {
            final AccountCredentials accountCredentials = PagSeguroConfig.getAccountCredentials();

            /**
             * Gets a full list of available payment methods
             */
            paymentMethods = PaymentMethodService.getPaymentMethods(accountCredentials, //
                    publicKey);

            for (Map.Entry<PaymentMethodType, Map<Integer, PaymentMethod>> paymentMethodsByType : paymentMethods
                    .getPaymentMethods().entrySet()) {
                System.out.println("--------------- " + paymentMethodsByType.getKey() + " ---------------");

                for (Map.Entry<Integer, PaymentMethod> paymentMethodsByCode : paymentMethodsByType.getValue()
                        .entrySet()) {
                    System.out.println("" + paymentMethodsByCode.getValue());
                }

                System.out.println("");
            }

            /**
             * verify if a payment method is available
             */
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.BANRISUL_ONLINE_TRANSFER));
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.BANCO_BRASIL_DIRECT_DEPOSIT));
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.DINERS_CREDIT_CARD));
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.VALECARD_CREDIT_CARD));
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.UNKNOWN_CODE));
            System.out.println(paymentMethods.isAvailable(PaymentMethodCode.AMEX_CREDIT_CARD));

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
    }

}
