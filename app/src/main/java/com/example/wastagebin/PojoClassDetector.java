package com.example.wastagebin;

public class PojoClassDetector {
String description, country, city, state,address,zipcode,status;
double latitude, longitude;
String id;

    public PojoClassDetector(String description, String country, String city, String state, double latitude, double longitude,String id,String address,String zipcode,String status) {
        this.description = description;
        this.country = country;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id=id;
        this.address=address;
        this.zipcode=zipcode;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PojoClassDetector(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

