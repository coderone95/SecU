package com.coderone95.secu.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonPropertyOrder({"email_id", "password"})
public class LoginBean {

    @JsonProperty("email_id")
    @NotBlank(message = "Email id cannot be blanked")
    @NotNull(message = "Email id cannot be blanked")
    private String emailId;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blanked")
    @NotNull(message = "Password cannot be blanked")
    private String password;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
