/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.service;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import de.document.entity.Krankheit;
import de.document.entity.Prozedur;
import de.document.jenaspring.JenaTemplate;
import de.document.jenaspring.SparqlTemplate;
import de.document.jenaspring.TextSearch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bilel-PC
 */
@Service
public class SearchService {

    JenaTemplate temp = new JenaTemplate();
    TextSearch search = new TextSearch();
    SparqlTemplate sparqlTemp = new SparqlTemplate();
    ICDNummerService icdService = new ICDNummerService();
    String NS = "http://document/";
    String url = "TDB\\test";

    @SuppressWarnings("empty-statement")
    public HashMap searchText(String word) throws IOException, ParseException {
        if (sparqlTemp.getModel() == null) {
            this.connectSparqlTemp();
        }
       // sparqlTemp.getModel().write(System.out);

        org.apache.jena.query.Dataset ds = search.createCode();
//        try{
        List list = search.queryData(ds, word, url);
        List<Krankheit> listKr = new ArrayList<>();
        List<Krankheit> listKrHaupt = new ArrayList<>();
        List<Krankheit> listKrNeben = new ArrayList<>();

        List<Prozedur> listPr = new ArrayList<>();
        List<Prozedur> listPrHaupt = new ArrayList<>();
        List<Prozedur> listPrNeben = new ArrayList<>();

        for (Iterator it = list.iterator(); it.hasNext();) {
            String l = (String) it.next();

            String sparql = "PREFIX doc: <http://document/KR/>"
                    + "SELECT ?autor ?date ?title ?notes WHERE {"
                    + " OPTIONAL { <" + l + "> doc:date ?date}. "
                    + "  <" + l + "> doc:title ?title. "
                    + " OPTIONAL { <" + l + "> doc:autor ?autor}. "
                    + " OPTIONAL { <" + l + "> doc:notes ?notes}. "
                    + "}";
            List<Krankheit> listKrankheit = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
                QuerySolution sln = rs.nextSolution();

                Krankheit krankheit = new Krankheit();

                if (sln.get("autor") != null) {
                    krankheit.setAutor(sln.get("autor").toString());
                }
                if (sln.get("title") != null) {
                    krankheit.setTitle(sln.get("title").toString());
                }

                if (sln.get("date") != null) {
                    krankheit.setDate(sln.get("date").toString());
                }
                if (sln.get("notes") != null) {
                    krankheit.setNotes(sln.get("notes").toString());
                }

                return krankheit;

            });
            if (!listKrankheit.isEmpty()) {
                if (listKrankheit.get(0).getNotes() != null) {
                    if (icdService.searchHauptICDNummer(listKrankheit.get(0).getNotes())) {
                        listKrHaupt.add(listKrankheit.get(0));
                    } else if (icdService.searchGefahrlichICDNummer(listKrankheit.get(0).getNotes())) {
                        listKrNeben.add(listKrankheit.get(0));
                    } else {
                        listKr.add(listKrankheit.get(0));
                    }
                } else {
                    listKr.add(listKrankheit.get(0));
                }
            }
            String PR = "PREFIX doc: <http://document/PR/>"
                    + "SELECT ?autor ?title ?date ?notes WHERE {"
                    + " OPTIONAL { <" + l + "> doc:date ?date}. "
                    + " <" + l + "> doc:title ?title. "
                    + " OPTIONAL { <" + l + "> doc:autor ?autor}. "                   
                    + " OPTIONAL { <" + l + "> doc:notes ?notes}. "
                    + "}";
            List<Prozedur> listProzedur = sparqlTemp.execSelectList(PR, (ResultSet rs, int rowNum) -> {
                QuerySolution sln = rs.nextSolution();

                Prozedur prozedur = new Prozedur();

                if (sln.get("autor") != null) {
                    prozedur.setAutor(sln.get("autor").toString());
                }
                if (sln.get("title") != null) {
                    prozedur.setTitle(sln.get("title").toString());
                }
                if (sln.get("date") != null) {
                    prozedur.setDate(sln.get("date").toString());
                }
                if (sln.get("notes") != null) {
                    prozedur.setNotes(sln.get("notes").toString());
                }

                return prozedur;

            });

            if (!listProzedur.isEmpty()) {
                if (listProzedur.get(0).getNotes() != null) {
                    if (icdService.searchHauptICDNummer(listProzedur.get(0).getNotes())) {
                        listPrHaupt.add(listProzedur.get(0));
                    } else if (icdService.searchGefahrlichICDNummer(listProzedur.get(0).getNotes())) {
                        listPrNeben.add(listProzedur.get(0));
                    } else {
                        listPr.add(listProzedur.get(0));
                    }
                } else {
                    listPr.add(listProzedur.get(0));
                }
            }

        }
        HashMap h = new HashMap();
        h.put("krankheiten", listKr);
        h.put("HauptKrankheiten", listKrHaupt);
        h.put("NebenKrankheiten", listKrNeben);
        h.put("prozeduren", listPr);
        h.put("HauptProzeduren", listPrHaupt);
        h.put("NebenProzeduren", listPrNeben);
        return h;
    }

    public void connectJenaTemp() {
        if (temp.getModel() == null) {
            Dataset dataset = TDBFactory.createDataset(url);
            Model model = dataset.getDefaultModel();
            temp.setModel(model);

        }
    }

    public void connectSparqlTemp() {
        if (sparqlTemp.getModel() == null) {
            Dataset dataset = TDBFactory.createDataset(url);
            Model model = dataset.getDefaultModel();
            sparqlTemp.setModel(model);

        }
    }

    public Model getModel() {
        Dataset dataset = TDBFactory.createDataset(url);
        Model model = dataset.getDefaultModel();
        return model;
    }

}
