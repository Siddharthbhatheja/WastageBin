package com.example.wastagebin;

public class APIs {
    public static String baseUrl="http://13.95.0.92:8084/";
    public static String createUser=baseUrl+"users/create";


    public static String createAndPostWastage=baseUrl+"detector/wastages";



    public static String resolverBaseUrl="http://13.95.0.92:8085/";




    public static String getWastageResolver=resolverBaseUrl+"resolver/wastages";




    public static String resolverOffer=resolverBaseUrl+"resolver/offers";

    public static String getResolverOffers=resolverBaseUrl+"resolver/offers";


    public static String financierBaseURL="http://13.95.0.92:8086/";
    public static String getOffersFinancier=financierBaseURL+"financer/offers";


    public static String baseUsersUrl="http://13.95.0.92:8083/"+"users";
}
