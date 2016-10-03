/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.entity;

/**
 *
 * @author MyTEK
 */
public class Umls {
    private String Ui;
    private String  name;
    private String definition;
    private int NumberOfAtom;
    private String SemanticType;

    public String getSemanticType() {
        return SemanticType;
    }

    public void setSemanticType(String SemanticType) {
        this.SemanticType = SemanticType;
    }

    public Umls(String name) {
        this.name = name;
    }
    

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getNumberOfAtom() {
        return NumberOfAtom;
    }

    public void setNumberOfAtom(int NumberOfAtom) {
        this.NumberOfAtom = NumberOfAtom;
    }
    

    public String getUi() {
        return Ui;
    }

    public void setUi(String ui) {
        this.Ui = ui;
    }

    public Umls(){
        
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  
    
}
