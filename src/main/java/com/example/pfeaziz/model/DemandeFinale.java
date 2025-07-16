package com.example.pfeaziz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class DemandeFinale extends Demande {

    private String finalDecision;
    private String approvedDate;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private App_user manager;

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public App_user getManager() {
        return manager;
    }

    public void setManager(App_user manager) {
        this.manager = manager;
    }
}