package br.com.uol.pagseguro.example.preapproval;

import java.util.HashMap;

import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.RecurrenceService;

public class CancelRecurrence {
    /**
     * Class with a main method to illustrate the usage of the domain class Recurrence
     */
    public static void main(String[] args) {

        // Substitute the code below with a valid recurrence code for your account
        cancelByCode("B297862B013C40B4BCABA5F3D9CD8C3F");
    }

    private static void cancelByCode(String recurrenceCode) {

        HashMap<String, String> cancelReturn = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            cancelReturn = RecurrenceService.cancelRecurrenceByCode(PagSeguroConfig.getAccountCredentials(),
                    recurrenceCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (cancelReturn != null)
            printRecurrence(cancelReturn);
    }

    private static void printRecurrence(HashMap<String, String> cancelReturn) {

        System.out.println("code: " + cancelReturn.get("code"));
        System.out.println("date: " + cancelReturn.get("date"));
    }
    
    private CancelRecurrence() {
    	
    }
}
