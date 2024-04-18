package com.example.deliveryapplication.restaurant;

public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String operatingHours;
    private String closedDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getClosedDays() {
        return closedDays;
    }

    public void setClosedDays(String closedDays) {
        this.closedDays = closedDays;
    }
}
