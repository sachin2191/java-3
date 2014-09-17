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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.Error;
import br.com.uol.pagseguro.domain.preapproval.PreApproval;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalTransaction;
import br.com.uol.pagseguro.enums.HttpStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.logs.Log;
import br.com.uol.pagseguro.parser.preapproval.PreApprovalParser;
import br.com.uol.pagseguro.service.ConnectionData;
import br.com.uol.pagseguro.utils.HttpConnection;
import br.com.uol.pagseguro.xmlparser.ErrorsParser;

/**
 * 
 * Class Pre-approval Service
 */
public class PreApprovalService {

    private PreApprovalService() {
    	
    }

    /**
     * PagSeguro Log tool
     * 
     * @see Log
     */
    private static Log log = new Log(PreApprovalService.class);

    /** 
     * @var String
     */
    private static final String PREFIX = "PreApprovalService.";
    
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
     * @param ConnectionData
     *            connectionData
     * @return string
     * @throws PagSeguroServiceException
     */
    public static String buildPreApprovalUrl(ConnectionData connectionData) throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalUrl() + "?" + connectionData.getCredentialsUrlQuery();
    }

    /**
     * Build Find Url By Code
     * 
     * @param ConnectionData
     *            connectionData
     * @param string
     *            preApprovalCode
     * @return string
     * @throws PagSeguroServiceException
     */
    private static String buildPreApprovalFindUrlByCode(ConnectionData connectionData, String preApprovalCode)
            throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalFindByCodeUrl() + "/" + preApprovalCode + "?"
                + connectionData.getCredentialsUrlQuery();
    }
    
    /**
     * Build Cancel Url By Code
     * 
     * @param ConnectionData
     *            connectionData
     * @param string
     *            preApprovalCode
     * @return string
     * @throws PagSeguroServiceException
     */
    private static String buildPreApprovalCancelUrlByCode(ConnectionData connectionData, String preApprovalCode)
            throws PagSeguroServiceException {
        return connectionData.getWSPreApprovalCancelByCodeUrl() + "/" + preApprovalCode + "?"
                + connectionData.getCredentialsUrlQuery();
    }
    
    /**
     * 
     * @param credentials
     * @param preApproval
     * @return string
     * @throws Exception
     */
    public static String createPreApproval(Credentials credentials, PreApproval preApproval) throws PagSeguroServiceException {

    	PreApprovalService.log.info(String.format(PREFIX + REGISTER + SUFFIX_BEGIN, preApproval.toString()));

        ConnectionData connectionData = new ConnectionData(credentials);

        Map<Object, Object> data = PreApprovalParser.getData(preApproval);

        String url = PreApprovalService.buildPreApprovalUrl(connectionData);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.post(url, data, connectionData.getServiceTimeout(),
                connectionData.getCharset());

        try {

            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());
            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {
                String code = PreApprovalParser.readSuccessXml(response);

                PreApprovalService.log.info(String.format(PREFIX + REGISTER + SUFFIX_END,
                        preApproval.toString(), code));

                return code;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {

                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                PreApprovalService.log.error(String.format(PREFIX + REGISTER + SUFFIX_ERROR,
                        preApproval.toString(), exception.getMessage()));

                throw exception;

            } else {

                throw new PagSeguroServiceException(httpCodeStatus);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

        	PreApprovalService.log.error(String.format(PREFIX + REGISTER + SUFFIX_ERROR,
                    preApproval.toString(), e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);

        } finally {
            response.disconnect();
        }
    }
    
    /**
     * 
     * @param credentials
     * @param preApprovalCode
     * @return PaymentRequest
     * @throws Exception
     */
    public static PreApprovalTransaction findByCode(Credentials credentials, String preApprovalCode) throws PagSeguroServiceException {
        
        PreApprovalService.log.info(String.format(PREFIX + FIND_BY_CODE + SUFFIX_BEGIN, preApprovalCode));

        ConnectionData connectionData = new ConnectionData(credentials);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpStatusCode = null;

        HttpURLConnection response = connection.get(
                PreApprovalService.buildPreApprovalFindUrlByCode(connectionData, preApprovalCode),
                connectionData.getServiceTimeout(), connectionData.getCharset());

        try {

            httpStatusCode = HttpStatus.fromCode(response.getResponseCode());

            if (HttpURLConnection.HTTP_OK == httpStatusCode.getCode().intValue()) {

                PreApprovalTransaction preApprovalTransaction = PreApprovalParser.readPreApproval(response.getInputStream());

                PreApprovalService.log.info(String.format(PreApprovalService.FIND_BY_CODE,
                        preApprovalCode, preApprovalTransaction.toString()));

                return preApprovalTransaction;
                
            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpStatusCode.getCode().intValue()) {

                List<Error> listErrors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpStatusCode, listErrors);

                PreApprovalService.log.error(String.format(PREFIX + FIND_BY_CODE + SUFFIX_ERROR,
                        preApprovalCode, exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpStatusCode);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            PreApprovalService.log.error(String.format(PREFIX + FIND_BY_CODE + SUFFIX_END, preApprovalCode,
                    e.getMessage()));

            throw new PagSeguroServiceException(httpStatusCode, e);

        } finally {
            response.disconnect();
        }
    }
    
    /**
     * 
     * @param credentials
     * @param preApproval
     * @return
     * @throws PagSeguroServiceException
     */
    public static HashMap<String, String> cancelPreApprovalByCode(Credentials credentials, String preApprovalCode) throws PagSeguroServiceException {

        PreApprovalService.log.info(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_BEGIN, preApprovalCode));

        ConnectionData connectionData = new ConnectionData(credentials);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpStatusCode = null;

        HttpURLConnection response = connection.httpRequestMethod(
                PreApprovalService.buildPreApprovalCancelUrlByCode(connectionData, preApprovalCode),
                connectionData.getServiceTimeout(), connectionData.getCharset(), "POST");

        try {

            httpStatusCode = HttpStatus.fromCode(response.getResponseCode());

            if (HttpURLConnection.HTTP_OK == httpStatusCode.getCode().intValue()) {

                HashMap<String, String> cancelReturn = PreApprovalParser.readCancelXml(response.getInputStream());

                PreApprovalService.log.info(String.format(PreApprovalService.CANCEL_BY_CODE,
                        preApprovalCode));

                return cancelReturn;
                
            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpStatusCode.getCode().intValue()) {

                List<Error> listErrors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpStatusCode, listErrors);

                PreApprovalService.log.error(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_ERROR,
                        preApprovalCode, exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpStatusCode);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            PreApprovalService.log.error(String.format(PREFIX + CANCEL_BY_CODE + SUFFIX_END, preApprovalCode,
                    e.getMessage()));

            throw new PagSeguroServiceException(httpStatusCode, e);

        } finally {
            response.disconnect();
        }
    }
}