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

package br.com.uol.pagseguro.service;

import java.math.BigDecimal;
import java.net.HttpURLConnection;

import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.installment.Installments;
import br.com.uol.pagseguro.enums.HttpStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.parser.direct.InstallmentsParser;
import br.com.uol.pagseguro.utils.HttpConnection;

/**
 * 
 * Class Payment Service
 */
public class InstallmentService {

    private InstallmentService() {
    }

    /**
     * @var Log
     */
    private static Log log = new Log(InstallmentService.class);

    /**
     * Build Installment Request Url
     * 
     * @param ConnectionData
     *            connectionData
     * @return string
     * @throws PagSeguroServiceException
     */
    private static String buildInstallmentsRequestUrl(ConnectionData connectionData) throws PagSeguroServiceException {
        return connectionData.getInstallmentsUrl() + "?" + connectionData.getCredentialsUrlQuery();
    }

    public static Installments getInstallments(Credentials credentials, //
            BigDecimal amount) //
            throws PagSeguroServiceException {
        return getInstallments(credentials, amount, null);
    }

    public static Installments getInstallments(Credentials credentials, //
            BigDecimal amount, //
            String cardBrand) //
            throws PagSeguroServiceException {
        log.info(String.format("InstallmentService.getInstallmentsAvailable( %s ) - begin", cardBrand + " - " + amount));

        ConnectionData connectionData = new ConnectionData(credentials);

        String url = InstallmentService.buildInstallmentsRequestUrl(connectionData) + "&amount=" + amount.toString();

        if (cardBrand != null) {
            url += "&cardBrand=" + cardBrand;
        }

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.get(url, connectionData.getServiceTimeout(),
                connectionData.getCharset());

        try {

            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());
            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {

                log.info(String.format("InstallmentService.getInstallmentsAvailable( %1s ) - end  %2s )", cardBrand,
                        amount));

                return InstallmentsParser.readTransaction(response.getInputStream());

            } else if (HttpURLConnection.HTTP_UNAUTHORIZED == httpCodeStatus.getCode().intValue()) {

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus);

                log.error(String.format("InstallmentService.getInstallmentsAvailable( %1s ) - error %2s", cardBrand,
                        exception.getMessage()));

                throw exception;

            } else {

                throw new PagSeguroServiceException(httpCodeStatus);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            log.error(String.format("PaymentService.getInstallmentsAvailable( %1s ) - error %2s", cardBrand,
                    e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);

        } finally {
            response.disconnect();
        }

    }
}