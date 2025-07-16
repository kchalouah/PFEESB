package com.example.pfeaziz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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