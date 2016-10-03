/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.entity;

/**
 *
 * @author Bilel-PC
 */
public class Medikament {
    
    private String id;
    private String bezeichnung;
    private String pzn;
    private String einheit;
    private String roteListe;
    private String inhaltsstoff;
    private String darr;

    public Medikament() {
    }

    public Medikament(String bezeichnung, String pzn, String einheit, String roteListe,String darr, String inhaltsstoff) {
        this.bezeichnung = bezeichnung;
        this.pzn = pzn;
        this.einheit = einheit;
        this.roteListe = roteListe;
        this.darr = darr;
        this.inhaltsstoff = inhaltsstoff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getDarr() {
        return darr;
    }

    public void setDarr(String darr) {
        this.darr = darr;
    }

    public Medikament(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public String getRoteListe() {
        return roteListe;
    }

    public void setRoteListe(String roteListe) {
        this.roteListe = roteListe;
    }

    public String getInhaltsstoff() {
        return inhaltsstoff;
    }

    public void setInhaltsstoff(String inhaltsstoff) {
        this.inhaltsstoff = inhaltsstoff;
    }
    
    
}
