package br.com.uol.pagseguro.example.preapproval;

import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.preapproval.PreApprovalRequestTransaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.preapproval.PreApprovalService;

public class FindPreApprovalRequest {
    /**
     * Class with a main method to illustrate the usage of the domain class PreApprovalRequest
     */
    public static void main(String[] args) {

        // Substitute the code below with a valid pre-approval request code for your account
        findRequestByCode("31174C01D3444C8AA9B597638D515E83");
    }

    private static void findRequestByCode(String preApprovalRequestCode) {

        PreApprovalRequestTransaction preApprovalRequestTransaction = null;

        try {

            // Set your account credentials on src/pagseguro-config.properties
            preApprovalRequestTransaction = PreApprovalService.findByCode(PagSeguroConfig.getAccountCredentials(),
                    preApprovalRequestCode);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }

        if (preApprovalRequestTransaction != null)
            printPreApprovalRequest(preApprovalRequestTransaction);

    }

    private static void printPreApprovalRequest(PreApprovalRequestTransaction preApprovalRequestTransaction) {
        System.out.println("name: " + preApprovalRequestTransaction.getName());
        System.out.println("code: " + preApprovalRequestTransaction.getCode());
        System.out.println("date: " + preApprovalRequestTransaction.getCreationDate());
        System.out.println("status: " + preApprovalRequestTransaction.getStatus());
        System.out.println("reference: " + preApprovalRequestTransaction.getReference());
        System.out.println("charge: " + preApprovalRequestTransaction.getManagementType());

        if (preApprovalRequestTransaction.getSender() != null) {
            Sender sender = preApprovalRequestTransaction.getSender();

            System.out.println("senderName: " + sender.getName());
            System.out.println("senderEmail: " + sender.getEmail());
        }
    }

    private FindPreApprovalRequest() {

    }
}
