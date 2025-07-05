package com.example.pfeaziz.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Demande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "operateur_id")
    private App_user operateur;
    private String of_demande;

    @ManyToOne
    @JoinColumn(name = "ilot_id")
    private Ilot ilot;
    @ManyToOne
    @JoinColumn(name = "metier_id")
    private Metier metier;
    private String date_demande;
    private String time_demande;

    @ManyToOne
    @JoinColumn(name = "controleur_id")
    private App_user controleur;
    private String status;      // Status field (conforme or non conforme)
    private String etq;         // Optional field for ETQ (null if status is conforme)
    private String defaut;      // Optional field for Defaut (null if status is conforme)
    private String startcontrole;
    private String finishcontrole;
    private String dureeDeLaTache;
    private Boolean startClicked; // Changed from String to Boolean
    private Boolean finishClicked; // Changed from String to Boolean
    private Boolean resultatClicked;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;
    private Boolean urgent;
    private String sn;
    private String nombre_produit_controle;
    private String quantite_controle_metier;
    private String quantite_non_conforme;
    private Boolean hidden ;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public App_user getOperateur() {
        return operateur;
    }

    public void setOperateur(App_user operateur) {
        this.operateur = operateur;
    }

    public Ilot getIlot() {
        return ilot;
    }

    public void setIlot(Ilot ilot) {
        this.ilot = ilot;
    }

    public Metier getMetier() {
        return metier;
    }

    public void setMetier(Metier metier) {
        this.metier = metier;
    }

    public String getDate() {
        return date_demande;
    }

    public void setDate(String date) {
        this.date_demande = date;
    }

    public String getTime() {
        return time_demande;
    }

    public void setTime(String time) {
        this.time_demande = time;
    }

    public App_user getControleur() {
        return controleur;
    }

    public void setControleur(App_user controleur) {
        this.controleur = controleur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtq() {
        return etq;
    }

    public void setEtq(String etq) {
        this.etq = etq;
    }

    public String getDefaut() {
        return defaut;
    }

    public void setDefaut(String defaut) {
        this.defaut = defaut;
    }

    public String getStartcontrole() {
        return startcontrole;
    }

    public void setStartcontrole(String startcontrole) {
        this.startcontrole = startcontrole;
    }

    public String getFinishcontrole() {
        return finishcontrole;
    }

    public void setFinishcontrole(String finishcontrole) {
        this.finishcontrole = finishcontrole;
    }

    public String getDureeDeLaTache() {
        return dureeDeLaTache;
    }

    public void setDureeDeLaTache(String dureeDeLaTache) {
        this.dureeDeLaTache = dureeDeLaTache;
    }

    public Boolean getStartClicked() {
        return startClicked;
    }

    public void setStartClicked(Boolean startClicked) {
        this.startClicked = startClicked;
    }

    public Boolean getFinishClicked() {
        return finishClicked;
    }

    public void setFinishClicked(Boolean finishClicked) {
        this.finishClicked = finishClicked;
    }



    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    /**
     * @return the sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn the sn to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }


    /**
     * @return the resultatClicked
     */
    public Boolean getResultatClicked() {
        return resultatClicked;
    }

    /**
     * @param resultatClicked the resultatClicked to set
     */
    public void setResultatClicked(Boolean resultatClicked) {
        this.resultatClicked = resultatClicked;
    }

    /**
     * @return the of_demande
     */
    public String getOf_demande() {
        return of_demande;
    }

    /**
     * @param of_demande the of_demande to set
     */
    public void setOf_demande(String of_demande) {
        this.of_demande = of_demande;
    }

    /**
     * @return the nombre_produit_controle
     */
    public String getNombre_produit_controle() {
        return nombre_produit_controle;
    }

    /**
     * @param nombre_produit_controle the nombre_produit_controle to set
     */
    public void setNombre_produit_controle(String nombre_produit_controle) {
        this.nombre_produit_controle = nombre_produit_controle;
    }

    /**
     * @return the quantite_controle_metier
     */
    public String getQuantite_controle_metier() {
        return quantite_controle_metier;
    }

    /**
     * @param quantite_controle_metier the quantite_controle_metier to set
     */
    public void setQuantite_controle_metier(String quantite_controle_metier) {
        this.quantite_controle_metier = quantite_controle_metier;
    }

    /**
     * @return the quantite_non_conforme
     */
    public String getQuantite_non_conforme() {
        return quantite_non_conforme;
    }

    /**
     * @param quantite_non_conforme the quantite_non_conforme to set
     */
    public void setQuantite_non_conforme(String quantite_non_conforme) {
        this.quantite_non_conforme = quantite_non_conforme;
    }

    /**
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
