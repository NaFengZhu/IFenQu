package com.ifenqu.app.model;

import java.io.Serializable;

public class ReceiptBusiness implements Serializable {
    private boolean isIndividual;
    private String companyTitle;
    private String tax;

    public boolean isIndividual() {
        return isIndividual;
    }

    public void setIndividual(boolean individual) {
        isIndividual = individual;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
