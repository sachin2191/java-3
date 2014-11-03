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

package br.com.uol.pagseguro.domain;

/**
 * Represents the receiver of a transaction
 */
public class Receiver {

    /**
     * Receiver e-mail
     */
    private String email;

    public Receiver() {

    }

    public Receiver(final String email) {

        this.email = email;
    }

    /**
     * @return email
     */
    public String getEmail() {

        return email;
    }

    /**
     * Sets the email
     *
     * @param email
     */
    public void setEmail(final String email) {

        this.email = email;
    }
}
