package com.example.pfeaziz.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DemandeTe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operateur;
    private String date;
    private String time;
    private String status;      // Status field (conforme or non conforme)
    private String startcontrole;
    private String finishcontrole;
    private String dureeDeLaTache;
    private Boolean startClicked; // Changed from String to Boolean
    private Boolean finishClicked; // Changed from String to Boolean
    private Boolean resultatClicked;
    private String sn;
    private String programme;
    private String controleur;
    private String machine;
    private String dureeAttente;
    private Boolean urgent;
    private Boolean hidden ;
    private String ilot;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the operateur
     */
    public String getOperateur() {
        return operateur;
    }

    /**
     * @param operateur the operateur to set
     */
    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }



    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the startcontrole
     */
    public String getStartcontrole() {
        return startcontrole;
    }

    /**
     * @param startcontrole the startcontrole to set
     */
    public void setStartcontrole(String startcontrole) {
        this.startcontrole = startcontrole;
    }

    /**
     * @return the finishcontrole
     */
    public String getFinishcontrole() {
        return finishcontrole;
    }

    /**
     * @param finishcontrole the finishcontrole to set
     */
    public void setFinishcontrole(String finishcontrole) {
        this.finishcontrole = finishcontrole;
    }

    /**
     * @return the dureeDeLaTache
     */
    public String getDureeDeLaTache() {
        return dureeDeLaTache;
    }

    /**
     * @param dureeDeLaTache the dureeDeLaTache to set
     */
    public void setDureeDeLaTache(String dureeDeLaTache) {
        this.dureeDeLaTache = dureeDeLaTache;
    }

    /**
     * @return the startClicked
     */
    public Boolean getStartClicked() {
        return startClicked;
    }

    /**
     * @param startClicked the startClicked to set
     */
    public void setStartClicked(Boolean startClicked) {
        this.startClicked = startClicked;
    }

    /**
     * @return the finishClicked
     */
    public Boolean getFinishClicked() {
        return finishClicked;
    }

    /**
     * @param finishClicked the finishClicked to set
     */
    public void setFinishClicked(Boolean finishClicked) {
        this.finishClicked = finishClicked;
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
     * @return the programme
     */
    public String getProgramme() {
        return programme;
    }

    /**
     * @param programme the programme to set
     */
    public void setProgramme(String programme) {
        this.programme = programme;
    }

    /**
     * @return the controleur
     */
    public String getControleur() {
        return controleur;
    }

    /**
     * @param controleur the controleur to set
     */
    public void setControleur(String controleur) {
        this.controleur = controleur;
    }

    /**
     * @return the machine
     */
    public String getMachine() {
        return machine;
    }

    /**
     * @param machine the machine to set
     */
    public void setMachine(String machine) {
        this.machine = machine;
    }

    /**
     * @return the dureeAttente
     */
    public String getDureeAttente() {
        return dureeAttente;
    }

    /**
     * @param dureeAttente the dureeAttente to set
     */
    public void setDureeAttente(String dureeAttente) {
        this.dureeAttente = dureeAttente;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the urgent
     */
    public Boolean getUrgent() {
        return urgent;
    }

    /**
     * @param urgent the urgent to set
     */
    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
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

    /**
     * @return the ilot
     */
    public String getIlot() {
        return ilot;
    }

    /**
     * @param ilot the ilot to set
     */
    public void setIlot(String ilot) {
        this.ilot = ilot;
    }


}
