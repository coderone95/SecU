package com.coderone95.secu.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankBean {

    @JsonProperty("sec_name")
    private String secName;

    @JsonProperty("ifsc_code")
    private String ifscCode;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("branch_name")
    private String branch;

    @JsonProperty("icmr_code")
    private String icmrCode;

    @JsonProperty("banking_url")
    private String bankingURL;

    @JsonProperty("net_banking_username")
    private String username;

    @JsonProperty("net_banking_password")
    private String password;

    @JsonProperty("other_data")
    private String otherData;

    @JsonProperty("account_holder")
    private String accountHolder;

}
