package com.example.pfeaziz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class DemandeDelegue extends Demande {

    @ManyToOne
    @JoinColumn(name = "delegated_to_id")
    private App_user delegatedTo;

    private String delegationDate;

    public App_user getDelegatedTo() {
        return delegatedTo;
    }

    public void setDelegatedTo(App_user delegatedTo) {
        this.delegatedTo = delegatedTo;
    }

    public String getDelegationDate() {
        return delegationDate;
    }

    public void setDelegationDate(String delegationDate) {
        this.delegationDate = delegationDate;
    }
}