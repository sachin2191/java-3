package br.com.uol.pagseguro.example.preapproval;

import java.util.HashMap;

import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

public class CancelPreApproval {
    /**
     * Class with a main method to illustrate the usage of the domain class PreApproval
     */
    public static void main(String[] args) {

        // Substitute the code below with a valid pre-approval code for your account
        cancelByCode("92213919B48B4E85BAAFFB0D81BEE8EA");
    }
    
    private static void cancelByCode(String preApprovalCode) {
        
        HashMap<String, String> cancelReturn = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            cancelReturn = PreApprovalService.cancelPreApprovalByCode(PagSeguroConfig.getAccountCredentials(),
                    preApprovalCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (cancelReturn != null)
            printPreApproval(cancelReturn);
    }
    
    private static void printPreApproval(HashMap<String, String> cancelReturn) {
        
        System.out.println("code: " + cancelReturn.get("code"));
        System.out.println("date: " + cancelReturn.get("date"));
        System.out.println("status: " + cancelReturn.get("status"));
    }
}
