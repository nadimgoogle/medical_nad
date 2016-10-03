/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.umls;

/**
 *
 * @author MyTEK
 */
public class relation {
    String Cui,relatedIdName;

    public String getCui() {
        return Cui;
    }

    public void setCui(String Cui) {
        this.Cui = Cui;
    }

    public String getRelatedIdName() {
        return relatedIdName;
    }

    public void setRelatedIdName(String relatedIdName) {
        this.relatedIdName = relatedIdName;
    }
    
}
