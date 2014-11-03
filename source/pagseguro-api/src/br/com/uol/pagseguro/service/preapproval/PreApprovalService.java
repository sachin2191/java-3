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

package br.com.uol.pagseguro.service.preapproval;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.Error;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalCancelTransaction;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequest;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequestTransaction;
import br.com.uol.pagseguro.enums.HttpStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.parser.preapproval.PreApprovalParser;
import br.com.uol.pagseguro.properties.PagSeguroSystem;
import br.com.uol.pagseguro.service.ConnectionData;
import br.com.uol.pagseguro.utils.HttpConnection;
import br.com.uol.pagseguro.xmlparser.ErrorsParser;

/**
 *
 * Class PreApproval Service
 */
public class PreApprovalService {

    private PreApprovalService() {

    }

    /**
     * PagSeguro Log tool
     *
     * @see Log
     */
    private static final Log log = new Log(PreApprovalService.class);

    /**
     * @var String
     */
    private static final String PREFIX = PreApprovalService.class.getSimpleName() + ".";

    /**
     * @var String
     */
    private static final String SUFFIX_BEGIN = "( %1s ) - begin";

    /**
     * @var String
     */
    private static final String SUFFIX_END = " - end %2s )";

    /**
     * @var String
     */
    private static final String SUFFIX_ERROR = " - error %2s )";

    /**
     * @var String
     */
    private static final String REGISTER = "Register";

    /**
     * @var String
     */
    private static final String FIND_BY_CODE = "FindByCode";

    /**
     * @var String
     */
    private static final String CANCEL_BY_CODE = "CancelByCode";

    /**
     *
     * @param connectionData
     * @return string
     * @throws PagSeguroServiceException
     */
    public static String buildPreApprovalRequestUrl(ConnectionData connectionData) throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalRequestUrl() + "?" + connectionData.getCredentialsUrlQuery();
    }

    /**
     * Build Find Url By Code
     *
     * @param connectionData
     * @param preApprovalRequestCode
     * @return string
     * @throws PagSeguroServiceException
     */
    private static String buildPreApprovalRequestFindByCodeUrl(ConnectionData connectionData,
            String preApprovalRequestCode) throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalRequestFindByCodeUrl() + "/" + preApprovalRequestCode + "?"
                + connectionData.getCredentialsUrlQuery();
    }

    /**
     * Build Cancel Url By Code
     *
     * @param connectionData
     * @param preApprovalCode
     * @return string
     * @throws PagSeguroServiceException
     */
    private static String buildPreApprovalCancelByCodeUrl(ConnectionData connectionData, String preApprovalCode)
            throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalCancelByCodeUrl() + "/" + preApprovalCode + "?"
                + connectionData.getCredentialsUrlQuery();
    }

    /**
     *
     * @param credentials
     * @param preApprovalRequest
     * @return string
     * @throws Exception
     */
    public static String createPreApprovalRequest(Credentials credentials, PreApprovalRequest preApprovalRequest)
            throws PagSeguroServiceException {

        log.info(String.format(PREFIX + REGISTER + SUFFIX_BEGIN, preApprovalRequest.toString()));

        ConnectionData connectionData = new ConnectionData(credentials);

        Map<Object, Object> data = PreApprovalParser.getData(preApprovalRequest);

        String url = buildPreApprovalRequestUrl(connectionData);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.post(url, data, connectionData.getServiceTimeout(),
                connectionData.getCharset(), PagSeguroSystem.getAcceptHeaderXML());

        try {
            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());
            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {
                String code = PreApprovalParser.readSuccessXml(response);

                log.info(String.format(PREFIX + REGISTER + SUFFIX_END, preApprovalRequest.toString(), code));

                return code;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {

                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                log.error(String.format(PREFIX + REGISTER + SUFFIX_ERROR, preApprovalRequest.toString(),
                        exception.getMessage()));

                throw exception;

            } else {

                throw new PagSeguroServiceException(httpCodeStatus);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            log.error(String.format(PREFIX + REGISTER + SUFFIX_ERROR, preApprovalRequest.toString(), e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);

        } finally {
            response.disconnect();
        }
    }

    /**
     *
     * @param credentials
     * @param preApprovalRequestCode
     * @return PaymentRequest
     * @throws Exception
     */
    public static PreApprovalRequestTransaction findByCode(Credentials credentials, String preApprovalRequestCode)
            throws PagSeguroServiceException {

        log.info(String.format(PREFIX + FIND_BY_CODE + SUFFIX_BEGIN, preApprovalRequestCode));

        ConnectionData connectionData = new ConnectionData(credentials);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpStatusCode = null;

        HttpURLConnection response = connection.get(
                buildPreApprovalRequestFindByCodeUrl(connectionData, preApprovalRequestCode),
                connectionData.getServiceTimeout(), connectionData.getCharset());

        try {

            httpStatusCode = HttpStatus.fromCode(response.getResponseCode());

            if (HttpURLConnection.HTTP_OK == httpStatusCode.getCode().intValue()) {

                PreApprovalRequestTransaction preApprovalRequestTransaction = PreApprovalParser
                        .readPreApprovalRequest(response.getInputStream());

                log.info(String.format(FIND_BY_CODE, preApprovalRequestCode, preApprovalRequestTransaction.toString()));

                return preApprovalRequestTransaction;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpStatusCode.getCode().intValue()) {

                List<Error> listErrors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpStatusCode, listErrors);

                log.error(String.format(PREFIX + FIND_BY_CODE + SUFFIX_ERROR, preApprovalRequestCode,
                        exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpStatusCode);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            log.error(String.format(PREFIX + FIND_BY_CODE + SUFFIX_END, preApprovalRequestCode, e.getMessage()));

            throw new PagSeguroServiceException(httpStatusCode, e);

        } finally {
            response.disconnect();
        }
    }

    /**
     *
     * @param credentials
     * @param preApprovalCode
     * @return
     * @throws PagSeguroServiceException
     */
    public static PreApprovalCancelTransaction cancelPreApprovalByCode(Credentials credentials, String preApprovalCode)
            throws PagSeguroServiceException {

        log.info(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_BEGIN, preApprovalCode));

        ConnectionData connectionData = new ConnectionData(credentials);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpStatusCode = null;

        HttpURLConnection response = connection.httpRequestMethod(
                buildPreApprovalCancelByCodeUrl(connectionData, preApprovalCode), connectionData.getServiceTimeout(),
                connectionData.getCharset(), "GET");

        try {

            httpStatusCode = HttpStatus.fromCode(response.getResponseCode());

            if (HttpURLConnection.HTTP_OK == httpStatusCode.getCode().intValue()) {

                PreApprovalCancelTransaction cancelTransaction = PreApprovalParser.readCancelXml(response
                        .getInputStream());

                log.info(String.format(CANCEL_BY_CODE, preApprovalCode));

                return cancelTransaction;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpStatusCode.getCode().intValue()) {

                List<Error> listErrors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpStatusCode, listErrors);

                log.error(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_ERROR, preApprovalCode, exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpStatusCode);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            log.error(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_END, preApprovalCode, e.getMessage()));

            throw new PagSeguroServiceException(httpStatusCode, e);

        } finally {
            response.disconnect();
        }
    }
}
