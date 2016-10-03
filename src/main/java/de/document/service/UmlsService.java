/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.service;

import static de.document.controller.UmlsController.umlsterm;
import de.document.entity.Umls;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Service;

/**
 *
 * @author MyTEK
 */
@Service
public class UmlsService {
     public List<Umls> readAll() {
        List<Umls> umlsList = new ArrayList<>();
        
        
        
        try (Scanner s = new Scanner((Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("Umls"+umlsterm)))) {
            while (s.hasNextLine()) {
                umlsList.add(new Umls(s.nextLine()));
            }}
        
           
        
       
      /* 
        Umls ob= new Umls();
        Umls ob1= new Umls(); 
        ob.setName("name");
        ob1.setName("name1");
        System.out.print(ob);
        }*/
        return umlsList;
    }
    
}
