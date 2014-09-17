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

package br.com.uol.pagseguro.enums;

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
