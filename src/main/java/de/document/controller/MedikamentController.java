/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.controller;

import de.document.entity.Med;
import de.document.entity.Medikament;
import de.document.service.MedikamentService;
import de.document.util.IdService;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Bilel-PC
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/medikament")
public class MedikamentController {

    private final MedikamentService service = new MedikamentService();

    @RequestMapping(value = "/query")
    public List<Medikament> readAll() {
        List<Medikament> list = service.readAllRoteListe();
        return list;
    }

    @RequestMapping(value = "/version")
    public List<String> readVersionNeben() {
        List<String> list = service.readVersion();
        return list;
    }

    @RequestMapping(value = "/read")
    public List<Medikament> readDefaultNeben() {
        List<Medikament> list = service.readDefault();
        return list;
    }

//    @RequestMapping(value = "/compare")
//    public HashMap comparator() {
//        List<Medikament> list = service.read("2014");
//        List<Medikament> list2 = service.read("2013");
//        HashMap result = service.comparator(list, list2);
//
//        return result;
//    }
    @RequestMapping(value = "/save/{version}", headers = "Content-Type= multipart/form-data", method = {RequestMethod.POST})
    public HashMap save(@PathVariable("version") String version, @RequestParam("file") MultipartFile file) throws Throwable {
        return service.readFileMedikament(file, version);

    }

    @RequestMapping(value = "/version/read", method = {RequestMethod.POST})
    public List<Medikament> read(@RequestBody String version) {
        return service.read(version);

    }

    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public Medikament readMedikament(@RequestBody String pzn) {
        return service.readMedikament(pzn);
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public void saveMedikament(@RequestBody Medikament medikament) throws Throwable {
        service.saveMedikament(medikament);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public void updateMedikament(@RequestBody Medikament medikament) throws Throwable {
        service.updateMedikament(medikament);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public void deleteMedikament(@RequestBody Medikament medikament) throws Throwable {
        service.delete(medikament);
    }

    @RequestMapping(value = "/search/used/", method = {RequestMethod.POST})
    public List searchUsedListMedikament(@RequestBody Object listMedikament) throws IOException, ParseException {
        List request = new ArrayList();
        List<Medikament> request2 = new ArrayList();
        request = (List) listMedikament;
        for (Object item : request) {
            LinkedHashMap itemx = (LinkedHashMap) item;
            request2.add(new Medikament(itemx.get("bezeichnung").toString(), itemx.get("pzn").toString(), itemx.get("einheit").toString(),
                    itemx.get("roteListe").toString(), itemx.get("darr").toString(), itemx.get("inhaltsstoff").toString()));
        }
        System.out.println(request2);
        if (!request2.isEmpty()) {
            return service.searchUsedMedikament(request2);
        }
        return null;
    }

    @RequestMapping(value = "/search/used/medikament", method = {RequestMethod.POST})
    public Med searchUsedMedikament(@RequestBody Medikament medikament) throws IOException, ParseException {
        List<Medikament> request = new ArrayList();
        request.add(medikament);
        List<Med> result = service.searchUsedMedikament(request);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }

    }
}
