package com.example.wastagebin;

public class PojoClassFinancier {

    String id, resolverId,offerStatus,location,offerId;
    int daysRequired;
    double cost;



    public PojoClassFinancier(String id, String resolverId, int daysRequired, double cost,String offerStatus,String location,String offerId) {
        this.id = id;
        this.resolverId = resolverId;
        this.daysRequired = daysRequired;
        this.cost = cost;
        this.offerStatus=offerStatus;
        this.location=location;
        this.offerId=offerId;
    }

    public String getLocation() {
        return location;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PojoClassFinancier() {

    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResolverId() {
        return resolverId;
    }

    public void setResolverId(String resolverId) {
        this.resolverId = resolverId;
    }

    public int getDaysRequired() {
        return daysRequired;
    }

    public void setDaysRequired(int daysRequired) {
        this.daysRequired = daysRequired;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

