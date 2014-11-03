package br.com.uol.pagseguro.example.preapproval;

import br.com.uol.pagseguro.domain.preapproval.PreApprovalCancelTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

public class CancelPreApproval {
    /**
     * Class with a main method to illustrate the usage of the domain class Recurrence
     */
    public static void main(String[] args) {

        // Substitute the code below with a valid recurrence code for your account
        cancelByCode("DC2305D3C84647D2BFD0D831EEA27E69");
    }

    private static void cancelByCode(String recurrenceCode) {

        PreApprovalCancelTransaction preApprovalCancelTransaction = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            preApprovalCancelTransaction = PreApprovalService.cancelPreApprovalByCode(
                    PagSeguroConfig.getAccountCredentials(), recurrenceCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (preApprovalCancelTransaction != null)
            printPreApproval(preApprovalCancelTransaction);
    }

    private static void printPreApproval(PreApprovalCancelTransaction preApprovalCancelTransactionT) {

        System.out.println("status: " + preApprovalCancelTransactionT.getStatus());
        System.out.println("date: " + preApprovalCancelTransactionT.getDate());
    }

    private CancelPreApproval() {

    }
}
