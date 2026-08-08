/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Distribution {
    private int distributionId;
    private Integer customerId;
    private String customerName;
    private String contactNumber;
    private Date distributionDate;
    private String shift;
    private double quantity;
    private double rate;
    private double total;

    // Getters & Setters
    public int getDistributionId() { return distributionId; }
    public void setDistributionId(int distributionId) { this.distributionId = distributionId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Date getDistributionDate() { return distributionDate; }
    public void setDistributionDate(Date distributionDate) { this.distributionDate = distributionDate; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
