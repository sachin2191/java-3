package br.com.uol.pagseguro.example.preapproval;

import br.com.uol.pagseguro.domain.preapproval.RecurrenceCancelTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.RecurrenceService;

public class CancelRecurrence {
    /**
     * Class with a main method to illustrate the usage of the domain class Recurrence
     */
    public static void main(String[] args) {

        // Substitute the code below with a valid recurrence code for your account
        cancelByCode("898F8425BF1A4632A20C826B0DBCDE75");
    }

    private static void cancelByCode(String recurrenceCode) {

        RecurrenceCancelTransaction cancelTransaction = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
        	cancelTransaction = RecurrenceService.cancelRecurrenceByCode(PagSeguroConfig.getAccountCredentials(),
                    recurrenceCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (cancelTransaction != null)
            printRecurrence(cancelTransaction);
    }

    private static void printRecurrence(RecurrenceCancelTransaction cancelTransaction) {

        System.out.println("code: " + cancelTransaction.getCode());
        System.out.println("date: " + cancelTransaction.getDate());
    }
    
    private CancelRecurrence() {
    	
    }
}
