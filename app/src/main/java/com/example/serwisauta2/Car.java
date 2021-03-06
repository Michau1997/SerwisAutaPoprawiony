package com.example.serwisauta2;

public class Car {

    private String car_name;
    private String mileage;
    private String vin;
    private String date_inspection;
    private String date_policy;
    private String oil_mileage;
    private String oil_date;
    private String filters_mileage;
    private String filters_date;
    private String brakes_mileage;
    private String brakes_date;
    private String tires_mileage;
    private String tires_date;
    private String timing_mileage;
    private String timing_date;
    private String spark_plugs_cables_mileage;
    private String spark_plugs_cables_date;
    private String profileImageUrl;
    private String id;

    Car()
    {

    }


    public Car(String car_name, String mileage, String vin, String date_inspection, String date_policy, String oil_mileage, String oil_date, String filters_mileage, String filters_date, String brakes_mileage, String brakes_date, String tires_mileage, String tires_date, String timing_mileage, String timing_date, String spark_plugs_cables_mileage, String spark_plugs_cables_date, String profileImageUrl, String id) {
        this.car_name = car_name;
        this.mileage = mileage;
        this.vin = vin;
        this.date_inspection = date_inspection;
        this.date_policy = date_policy;
        this.oil_mileage = oil_mileage;
        this.oil_date = oil_date;
        this.filters_mileage = filters_mileage;
        this.filters_date = filters_date;
        this.brakes_mileage = brakes_mileage;
        this.brakes_date = brakes_date;
        this.tires_mileage = tires_mileage;
        this.tires_date = tires_date;
        this.timing_mileage = timing_mileage;
        this.timing_date = timing_date;
        this.spark_plugs_cables_mileage = spark_plugs_cables_mileage;
        this.spark_plugs_cables_date = spark_plugs_cables_date;
        this.profileImageUrl = profileImageUrl;
        this.id = id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDate_inspection() {
        return date_inspection;
    }

    public void setDate_inspection(String date_inspection) {
        this.date_inspection = date_inspection;
    }

    public String getDate_policy() {
        return date_policy;
    }

    public void setDate_policy(String date_policy) {
        this.date_policy = date_policy;
    }

    public String getOil_mileage() {
        return oil_mileage;
    }

    public void setOil_mileage(String oil_mileage) {
        this.oil_mileage = oil_mileage;
    }

    public String getOil_date() {
        return oil_date;
    }

    public void setOil_date(String oil_date) {
        this.oil_date = oil_date;
    }

    public String getFilters_mileage() {
        return filters_mileage;
    }

    public void setFilters_mileage(String filters_mileage) {
        this.filters_mileage = filters_mileage;
    }

    public String getFilters_date() {
        return filters_date;
    }

    public void setFilters_date(String filters_date) {
        this.filters_date = filters_date;
    }

    public String getBrakes_mileage() {
        return brakes_mileage;
    }

    public void setBrakes_mileage(String brakes_mileage) {
        this.brakes_mileage = brakes_mileage;
    }

    public String getBrakes_date() {
        return brakes_date;
    }

    public void setBrakes_date(String brakes_date) {
        this.brakes_date = brakes_date;
    }

    public String getTires_mileage() {
        return tires_mileage;
    }

    public void setTires_mileage(String tires_mileage) {
        this.tires_mileage = tires_mileage;
    }

    public String getTires_date() {
        return tires_date;
    }

    public void setTires_date(String tires_date) {
        this.tires_date = tires_date;
    }

    public String getTiming_mileage() {
        return timing_mileage;
    }

    public void setTiming_mileage(String timing_mileage) {
        this.timing_mileage = timing_mileage;
    }

    public String getTiming_date() {
        return timing_date;
    }

    public void setTiming_date(String timing_date) {
        this.timing_date = timing_date;
    }

    public String getSpark_plugs_cables_mileage() {
        return spark_plugs_cables_mileage;
    }

    public void setSpark_plugs_cables_mileage(String spark_plugs_cables_mileage) {
        this.spark_plugs_cables_mileage = spark_plugs_cables_mileage;
    }

    public String getSpark_plugs_cables_date() {
        return spark_plugs_cables_date;
    }

    public void setSpark_plugs_cables_date(String spark_plugs_cables_date) {
        this.spark_plugs_cables_date = spark_plugs_cables_date;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}