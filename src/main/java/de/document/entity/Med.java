/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.entity;

import java.util.List;

/**
 *
 * @author Bilel-PC
 */
public class Med {
    Medikament medikament;
    List<Krankheit> krankheits;
    List<Prozedur> prozedurs;

    public Medikament getMedikament() {
        return medikament;
    }

    public void setMedikament(Medikament medikament) {
        this.medikament = medikament;
    }

    public List<Krankheit> getKrankheits() {
        return krankheits;
    }

    public void setKrankheits(List<Krankheit> krankheits) {
        this.krankheits = krankheits;
    }

    public List<Prozedur> getProzedurs() {
        return prozedurs;
    }

    public void setProzedurs(List<Prozedur> prozedurs) {
        this.prozedurs = prozedurs;
    }
    
}
