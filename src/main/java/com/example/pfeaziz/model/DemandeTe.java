package com.example.pfeaziz.model;

import jakarta.persistence.Entity;
@Entity
public class DemandeTe extends Demande {

    private String teStatus;

    public String getTeStatus() {
        return teStatus;
    }

    public void setTeStatus(String teStatus) {
        this.teStatus = teStatus;
    }
}
