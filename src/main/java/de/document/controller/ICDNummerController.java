/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.controller;

import de.document.entity.Document;
import de.document.entity.ICDNummer;
import de.document.entity.Krankheit;
import de.document.entity.Prozedur;
import de.document.service.ICDNummerService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/icdnummer")
public class ICDNummerController {

    ICDNummerService service = new ICDNummerService();

    @RequestMapping(value = "/save")
    public void saveAll() {
        service.saveAll();

    }
//    @RequestMapping(value = "/save/neben/{version}", method = {RequestMethod.GET})
//    public void saveNeben(@PathVariable("version") String version) {
//        service.saveNebenDiagnose(version);
//
//    }

    @RequestMapping(value = "/save/haupt")
    public void saveHaupt() {
        service.saveHauptDiagnose();

    }

    @RequestMapping(value = "/query")
    public List<ICDNummer> readAll() {
        List<ICDNummer> list = service.readAll();
        return list;
    }
//    @RequestMapping(value = "/version")
//    public List<String> readVersionNeben() {
//        List<String> list = service.readVersionNeben();
//        return list;
//    }
//    @RequestMapping(value = "/read/neben")
//    public List<ICDNummer> readDefaultNeben() {
//        List<ICDNummer> list = service.readDefaultNeben();
//        return list;
//    }
//    @RequestMapping(value = "/compare")
//    public HashMap comparator() {
//        List<ICDNummer> list = service.readDefaultNeben();
//        List<ICDNummer> list2 = service.readAll();
//        HashMap result = service.comparator(list, list2);
//
//        return result;
//    }

    @RequestMapping(value = "/read/", method = {RequestMethod.POST})
    public ResponseEntity read(@RequestBody String code) {
        //code = code.replace("-", ".");
        
        ICDNummer entity = this.service.read(code);
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(value = "/save/gesamt", method = {RequestMethod.POST})
    public ICDNummer saveGesamt(@RequestBody ICDNummer request) {
        return service.saveGesamt(request);

    }

    @RequestMapping(value = "/update/gesamt", method = {RequestMethod.POST})
    public ICDNummer updateGesamt(@RequestBody ICDNummer request) {
        return service.updateGesamt(request);

    }

    @RequestMapping(value = "/read/haupt")
    public List<ICDNummer> readHaupt() {
        List<ICDNummer> list = service.readHaupt();
        return list;
    }

    @RequestMapping(value = "/read/gefaehrlich")
    public List<ICDNummer> readGefaeh() {
        List<ICDNummer> list = service.readGefaehrlich();
        return list;
    }

    @RequestMapping(value = "/search/used/" , method = {RequestMethod.POST})
    public HashMap searchUsedIcd(@RequestBody String code) throws ParseException, IOException {
        HashMap list = service.searchUsedICDNummer(code);
        return list;
    }

    @RequestMapping(value = "/id/", method = {RequestMethod.POST})
    public String deleteHaupt(@RequestBody String code) {

        String x = this.service.readId(code);
        String y = x.replaceAll("http://ICDNummer/", "");
        return y;
    }

    @RequestMapping(value = "/delete/gesamt/", method = {RequestMethod.POST})
    public ResponseEntity delete(@RequestBody String code) {
        this.service.delete(code);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/notes/bearbeiten", method = {RequestMethod.POST})
    public void notesBearbeiten(@RequestBody Object request) {

        this.service.notesBearbeiten(request);

    }

    @RequestMapping(value = "/notes/entfernen", method = {RequestMethod.POST})
    public void notesEntfernen(@RequestBody Object request) {

        this.service.notesEntfernen(request);

    }
}
