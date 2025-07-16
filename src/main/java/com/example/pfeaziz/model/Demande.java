package com.example.pfeaziz.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String of_demande;
    private String date_demande;
    private String status; // "en cours", "planifié", etc.
    private Integer duree_en_minutes;

    private String etq;
    private Boolean started;
    private Boolean finished;
    private Integer nombre_produit_controle;

    @ManyToOne
    @JoinColumn(name = "ilot_id")
    private Ilot ilot;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "operateur_id")
    private App_user operateur;

    @ManyToOne
    @JoinColumn(name = "controleur_id")
    private App_user controleur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOf_demande() {
        return of_demande;
    }

    public void setOf_demande(String of_demande) {
        this.of_demande = of_demande;
    }

    public String getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(String date_demande) {
        this.date_demande = date_demande;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDuree_en_minutes() {
        return duree_en_minutes;
    }

    public void setDuree_en_minutes(Integer duree_en_minutes) {
        this.duree_en_minutes = duree_en_minutes;
    }

    public String getEtq() {
        return etq;
    }

    public void setEtq(String etq) {
        this.etq = etq;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getNombre_produit_controle() {
        return nombre_produit_controle;
    }

    public void setNombre_produit_controle(Integer nombre_produit_controle) {
        this.nombre_produit_controle = nombre_produit_controle;
    }

    public Ilot getIlot() {
        return ilot;
    }

    public void setIlot(Ilot ilot) {
        this.ilot = ilot;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public App_user getOperateur() {
        return operateur;
    }

    public void setOperateur(App_user operateur) {
        this.operateur = operateur;
    }

    public App_user getControleur() {
        return controleur;
    }

    public void setControleur(App_user controleur) {
        this.controleur = controleur;
    }
}
