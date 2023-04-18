package com.example.moblie_labs_19521987;

import java.math.BigDecimal;

public class PersonalSalary {
    private String fullName;
    private long grossSalary;
    private final long dependentCost  = 11000000;

    PersonalSalary(String fullName, int grossSalary){
        this.fullName = fullName;
        this.grossSalary = grossSalary;
    }

    public double netSalary(){
        double netSalary=0;
        double temp = grossSalary*(1-0.105);
        if(temp <= dependentCost)
            return temp;
        netSalary = dependentCost + (temp - dependentCost)*(1-0.05);
        return netSalary;
    }

    public String getInformation(){
        if(fullName.length() == 0 && grossSalary == 0)
            return "";
        return fullName + " - Net Salary: " + new BigDecimal(netSalary()).toPlainString();
    }
}
