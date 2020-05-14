package com.home.ecommerce.Security;

public class LoginSuccessResponse {

    private  boolean success;
    private String jwt;

    public LoginSuccessResponse(boolean success,String jwt){
        this.success = success;
        this.jwt = jwt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
