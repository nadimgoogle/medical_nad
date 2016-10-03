/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.entity;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Bilel-PC
 */
public class Prozedur extends Document implements Serializable, Cloneable{

    private TextModel uebersicht;
    private TextModel diagnostik;
    private TextModel therapie;
    private TextModel beratung;
    private String notes;

    public TextModel getUebersicht() {
        return uebersicht;
    }

    public void setUebersicht(TextModel uebersicht) {
        this.uebersicht = uebersicht;
    }

 

    public TextModel getTherapie() {
        return therapie;
    }

    public void setTherapie(TextModel therapie) {
        this.therapie = therapie;
    }

    public TextModel getDiagnostik() {
        return diagnostik;
    }

    public void setDiagnostik(TextModel diagnostik) {
        this.diagnostik = diagnostik;
    }

    public TextModel getBeratung() {
        return beratung;
    }

    public void setBeratung(TextModel beratung) {
        this.beratung = beratung;
    }



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    
@Override
    public Krankheit clone() throws CloneNotSupportedException {
        try {
            return (Krankheit) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }    
}

