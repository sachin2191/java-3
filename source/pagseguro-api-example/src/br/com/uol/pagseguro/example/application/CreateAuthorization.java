package br.com.uol.pagseguro.example.application;

import br.com.uol.pagseguro.domain.authorization.AuthorizationRequest;
import br.com.uol.pagseguro.enums.PermissionType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class CreateAuthorization {
	/**
     * Class with a main method to illustrate the usage of the domain class Authorization
     */
	public static void main(String[] args) {
		AuthorizationRequest authorization = new AuthorizationRequest();
		
		authorization.setReference("REF1234");
		
		authorization.setRedirectURL("http://www.yourstore.com.br/redirect");
		
		authorization.setNotificationURL("http://www.yourstore.com.br/notification");
		
		authorization.addPermission(PermissionType.CREATE_CHECKOUTS.getValue());
		authorization.addPermission(PermissionType.DIRECT_PAYMENT.getValue());
		authorization.addPermission(PermissionType.MANAGE_PAYMENT_PRE_APPROVALS.getValue());

		try {
            // Set your account credentials on src/pagseguro-config.properties
            String authorizationURL = authorization.register(PagSeguroConfig.getApplicationCredentials());
            System.out.println(authorizationURL);

        } catch (PagSeguroServiceException e) {
        	System.err.println(e.getMessage());
        }

	}

}
