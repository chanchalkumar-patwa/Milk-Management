/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Collection {
    private int entryId;
    private int farmerId;
    private String farmerName;
    private LocalDate entryDate;
    private String shift;
    private double quantityLiters;
    private double fatPercentage;
    private double snfPercentage;
    private double ratePerLiter;
    private double totalAmount;

    // Getters and Setters
    public int getEntryId() { return entryId; }
    public void setEntryId(int entryId) { this.entryId = entryId; }

    public int getFarmerId() { return farmerId; }
    public void setFarmerId(int farmerId) { this.farmerId = farmerId; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public double getQuantityLiters() { return quantityLiters; }
    public void setQuantityLiters(double quantityLiters) { this.quantityLiters = quantityLiters; }

    public double getFatPercentage() { return fatPercentage; }
    public void setFatPercentage(double fatPercentage) { this.fatPercentage = fatPercentage; }

    public double getSnfPercentage() { return snfPercentage; }
    public void setSnfPercentage(double snfPercentage) { this.snfPercentage = snfPercentage; }

    public double getRatePerLiter() { return ratePerLiter; }
    public void setRatePerLiter(double ratePerLiter) { this.ratePerLiter = ratePerLiter; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
