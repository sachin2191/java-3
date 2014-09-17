package br.com.uol.pagseguro.domain.preapproval;

public enum PreApprovalStatus {
    SCHEDULED('S'), INITIATED('I'), PAUSED('P'), FINISHED('F');
    
    private Character id;
    
    private PreApprovalStatus(Character id) {
        this.id = id;
    }
    
    public Character getId() {
        return this.id;
    }
    
    public static PreApprovalStatus fromValue(Character id) {
        for (PreApprovalStatus value : PreApprovalStatus.values()) {
            if(value.getId().equals(id)) {
                return value;
            }
        }
        
        return null;
    }
}
