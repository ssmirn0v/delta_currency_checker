package com.alfa.testtask.delta_currency_checker.exception;


public class IntegrationServiceException extends RuntimeException{

    private String serviceName;

    public IntegrationServiceException(String serviceName, String msg) {
        super(msg);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
