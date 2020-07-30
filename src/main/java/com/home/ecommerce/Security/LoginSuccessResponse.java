package com.home.ecommerce.Security;

public class LoginSuccessResponse {

    private  boolean success;
    private String jwt;
    private String role;

    public LoginSuccessResponse(boolean success,String jwt,String role){
        this.success = success;
        this.jwt = jwt;
        this.role =role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
