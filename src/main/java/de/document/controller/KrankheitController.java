/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.controller;

import de.document.entity.Document;
import de.document.entity.Krankheit;
import de.document.service.KrankheitService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bilel-PC
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/krankheit")
public class KrankheitController {

    KrankheitService service = new KrankheitService();

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Document saveKrankheit(@RequestBody Krankheit request) {
                System.out.println("KrankheitController");
                
        System.out.println(request);
        System.out.println("the list");
        
              
              // System.out.println(request.getUmls().getName());
               //System.out.println(request.getUebersicht().getText());
               //System.out.println(request.getUebersicht());
        
        return service.save(request);

    }

    @RequestMapping(value = "/new")
    public Krankheit newKrankheit() {

        Krankheit entity = this.service.create();
        return entity;
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET})
    public List query() {

        List entity = this.service.readAll();
        System.out.println(entity.size());

        return entity;
    }

    @RequestMapping(value = "/{name}", method = {RequestMethod.GET})
    public ResponseEntity read(@PathVariable("name") String name) {

        Krankheit entity = this.service.read(name);
        System.out.println("get service");
        System.out.println(entity.getUmlsInfo());
                
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(value = "/versionnig/bearbeiten", method = {RequestMethod.POST})
    public ResponseEntity versionnigBearbeiten(@RequestBody Krankheit request) {

        this.service.versionnigBearbeiten(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{title}", method = {RequestMethod.DELETE})
    public ResponseEntity delete(@PathVariable("title") String title) {

        this.service.delete(title);
        return ResponseEntity.ok().build();
    }
}
