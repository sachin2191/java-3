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

import java.util.List;

import br.com.uol.pagseguro.domain.Authorization;
import br.com.uol.pagseguro.domain.Permission;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.NotificationService;

public class ReceiveAuthorizationNotifications {

    public static void main(String[] args) {

        // The notificationCode received by your system
        String notificationCode = "FF422A1EE6AEE6AEEFB444AB9F963C2EF0B7";

        Authorization authorization = null;

        try {
        	
        	/* Set your account credentials on src/pagseguro-config.properties
			 * You can create an payment using an application credential and set an authorizationCode 
			 * ApplicationCredentials applicationCredentials = PagSeguroConfig.getApplicationCredentials();
             * applicationCredentials.setAuthorizationCode("your_authorizationCode");
			 */
        	
            authorization = NotificationService.checkAuthorization(PagSeguroConfig.getApplicationCredentials(),
                    notificationCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (authorization != null) {
            System.out.println("code: " + authorization.getCode());
            System.out.println("reference: " + authorization.getReference());
            List<Permission> permissions = authorization.getPermissions();
            for (Permission permission : permissions) {
				System.out.println("Permission " + permission.getPermission() + " - Status: " + permission.getStatus());
			}
        }

    }

    private ReceiveAuthorizationNotifications() {
    }
}