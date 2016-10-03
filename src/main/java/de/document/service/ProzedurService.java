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
import de.document.jenaspring.JenaTemplate;
import de.document.jenaspring.SparqlTemplate;
import de.document.entity.Prozedur;
import de.document.entity.TextModel;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bilel-PC
 */
@Service
public class ProzedurService {

    JenaTemplate temp = new JenaTemplate();
    SparqlTemplate sparqlTemp = new SparqlTemplate();
    String NS = "http://document/PR/";
    String url = "C:\\Users\\MyTEK\\Documents\\NetBeansProjects\\Medical-application\\TDB\\test";
    private String reTitle;

    public String linkText(String text) {
        System.out.println(text.replaceAll("\\\"", ""));
        return text.replaceAll("\\\"", "");
    }

    ;
     public Prozedur save(Prozedur entry) {

        try {
            entry = (Prozedur) BeanUtils.cloneBean(entry);
            if (temp.getModel() != null) {

                if (temp.getModel().isClosed()) {
                    this.connectJenaTemp();
                }
            } else {
                this.connectJenaTemp();
            }
            // System.out.println(temp.getModel().isClosed());
            //temp.removeResource(NS + "prozedur/" + entry.getTitle());
            if (entry.getTitle() != null) {

                reTitle = entry.getTitle().replaceAll(" ", "_");
            }
            temp.removeResource(NS + reTitle);

            if (entry.getTitle() != null) {
                // temp.addResource(NS + reTitle, NS + "type", NS + "prozedur/" + entry.getTitle());
                temp.add(NS + reTitle, NS + "title", entry.getTitle());
                temp.add(NS + reTitle, NS + "label", "prozedur");
            }
            if (entry.getAutor() != null) {
                temp.add(NS + reTitle, NS + "autor", entry.getAutor());
            }
            if (entry.getDate() != null) {
                temp.add(NS + reTitle, NS + "date", entry.getDate().substring(0, 10));
            }
            if (entry.getUebersicht() != null) {
                if (entry.getUebersicht().getNotfall() != null) {
                    temp.add(NS + reTitle, NS + "uebersicht/notfall", linkText(entry.getUebersicht().getNotfall()));
                }
                if (entry.getUebersicht().getText() != null) {
                    temp.add(NS + reTitle, NS + "uebersicht/text", linkText(entry.getUebersicht().getText()));
                }
            }
            if (entry.getDiagnostik() != null) {

                if (entry.getDiagnostik().getText() != null) {
                    temp.add(NS + reTitle, NS + "diagnostik/text", linkText(entry.getDiagnostik().getText()));
                }
                if (entry.getDiagnostik().getNotfall() != null) {
                    temp.add(NS + reTitle, NS + "diagnostik/notfall", linkText(entry.getDiagnostik().getNotfall()));
                }
            }
            if (entry.getBeratung() != null) {
                if (entry.getBeratung().getNotfall() != null) {
                    temp.add(NS + reTitle, NS + "beratung/notfall", linkText(entry.getBeratung().getNotfall()));
                }
                if (entry.getBeratung().getText() != null) {
                    temp.add(NS + reTitle, NS + "beratung/text", linkText(entry.getBeratung().getText()));
                }

            }
            if (entry.getTherapie() != null) {
                if (entry.getTherapie().getNotfall() != null) {
                    temp.add(NS + reTitle, NS + "therapie/notfall", linkText(entry.getTherapie().getNotfall()));
                }
                if (entry.getTherapie().getText() != null) {
                    temp.add(NS + reTitle, NS + "therapie/text", linkText(entry.getTherapie().getText()));
                }
            }

            if (entry.getNotes() != null) {
                temp.add(NS + reTitle, NS + "notes", linkText(entry.getNotes()));
            }

            if (!temp.getModel().isClosed()) {
                temp.getModel().close();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return entry;

    }

    public Prozedur create() {
        Prozedur prozedur = new Prozedur();
        TextModel uebersicht = new TextModel();
        TextModel therapie = new TextModel();
        TextModel beratung = new TextModel();
        TextModel diagnostik = new TextModel();
        prozedur.setUebersicht(uebersicht);
        prozedur.setBeratung(beratung);
        prozedur.setDiagnostik(diagnostik);
        prozedur.setTherapie(therapie);
        return prozedur;
    }

    public Prozedur read(String title) {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }

        String sparql = "PREFIX doc: <http://document/PR/>"
                + "PREFIX ueber: <http://document/PR/uebersicht/>"
                + "PREFIX pro: <http://document/PR/>"
                + "PREFIX diag: <http://document/PR/diagnostik/>"
                + "PREFIX th: <http://document/PR/therapie/>"
                + "PREFIX ber: <http://document/PR/beratung/>"
                + "PREFIX no: <http://document/PR/notes/>"
                + "SELECT ?title ?autor ?date "
                + "?ueberNotfall ?ueberText "
                + "?diagText ?diagNotfall "
                + "?thText ?thNotfall  "
                + "?berText ?berNotfall "
                + "?notes WHERE {"
                + " ?x doc:label 'prozedur'. "
                + " OPTIONAL { ?x doc:date ?date}. "
                + " ?x doc:title '" + title + "'. "
                + " OPTIONAL { ?x doc:autor ?autor}. "
                + " OPTIONAL { ?x ueber:notfall ?ueberNotfall}. "
                + " OPTIONAL { ?x ueber:text ?ueberText}. "
                + " OPTIONAL { ?x diag:notfall ?diagNotfall}. "
                + " OPTIONAL { ?x diag:text ?diagText}. "
                + " OPTIONAL { ?x th:notfall ?thNotfall}. "
                + " OPTIONAL { ?x th:text ?thText}. "
                + " OPTIONAL { ?x ber:notfall ?berNotfall}. "
                + " OPTIONAL { ?x ber:text ?berText}. "
                + " OPTIONAL { ?x doc:notes ?notes}. "
                + "}";
        List<Prozedur> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            Prozedur prozedur = new Prozedur();
            TextModel uebersicht = new TextModel();
            TextModel therapie = new TextModel();
            TextModel beratung = new TextModel();
            TextModel diagnostik = new TextModel();
            if (sln.get("notes") != null) {
                prozedur.setNotes(sln.get("notes").toString());
            }

            if (sln.get("berText") != null) {
                beratung.setText(sln.get("berText").toString());
            }
            if (sln.get("berNotfall") != null) {
                beratung.setNotfall(sln.get("berNotfall").toString());
            }

            if (sln.get("thNotfall") != null) {
                therapie.setNotfall(sln.get("thNotfall").toString());
            }
            if (sln.get("thText") != null) {
                therapie.setText(sln.get("thText").toString());
            }

            if (sln.get("diagText") != null) {
                diagnostik.setText(sln.get("diagText").toString());
            }
            if (sln.get("diagNotfall") != null) {
                diagnostik.setNotfall(sln.get("diagNotfall").toString());
            }

            if (sln.get("autor") != null) {
                prozedur.setAutor(sln.get("autor").toString());
            }
            if (sln.get("prozedurTitle") != null) {

                prozedur.setTitle(sln.get("prozedurTitle").toString());
            }
            prozedur.setTitle(title);

            if (sln.get("date") != null) {
                System.out.println(sln.get("date").toString());
                prozedur.setDate(sln.get("date").toString());
            }

            if (sln.get("ueberText") != null) {
                uebersicht.setText(sln.get("ueberText").toString());
            }
            if (sln.get("ueberNotfall") != null) {
                uebersicht.setNotfall(sln.get("ueberNotfall").toString());
            }

            prozedur.setUebersicht(uebersicht);
            prozedur.setBeratung(beratung);
            prozedur.setDiagnostik(diagnostik);
            prozedur.setTherapie(therapie);
            prozedur.setUebersicht(uebersicht);
            return prozedur;

        });
        return list.get(0);
    }
 
    public void versionnigBearbeiten(Prozedur prozedur ) {
        Prozedur kr = this.read(prozedur.getTitle());
        kr.setTherapie(prozedur.getTherapie());
       this.save(kr);
    }

    
    public List<Prozedur> readTherapie() {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }

        String sparql = "PREFIX doc: <http://document/PR/>"
                + "PREFIX th: <http://document/PR/therapie/>"
                + "SELECT ?title ?thNotfall ?thText  WHERE {"
                + " ?x doc:label 'prozedur'. "
                + " ?x doc:title ?title. "
                + " OPTIONAL { ?x th:notfall ?thNotfall}. "
                + " OPTIONAL { ?x th:text ?thText}. "
                + "}";
        List<Prozedur> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            Prozedur prozedur = new Prozedur();
            TextModel therapie = new TextModel();

            if (sln.get("title") != null) {
                prozedur.setTitle(sln.get("title").toString());
            }
            if (sln.get("thNotfall") != null) {
                therapie.setNotfall(sln.get("thNotfall").toString());
            }
            if (sln.get("thText") != null) {
                therapie.setText(sln.get("thText").toString());
            }
            prozedur.setTherapie(therapie);

            return prozedur;

        });
        return list;
    }

    public List<Prozedur> readAll() {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }

        String sparql = "PREFIX doc: <http://document/PR/>"
                + "SELECT ?title ?autor ?date ?notes WHERE {"
                + " ?x doc:label 'prozedur'. "
                + " OPTIONAL { ?x doc:date ?date}. "
                + " ?x doc:title ?title. "
                + " OPTIONAL { ?x doc:autor ?autor}. "
                + " OPTIONAL { ?x doc:notes ?notes}. "
                + "}";
        List<Prozedur> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            Prozedur prozedur = new Prozedur();

            if (sln.get("autor") != null) {
                prozedur.setAutor(sln.get("autor").toString());
            }
            if (sln.get("title") != null) {
                prozedur.setTitle(sln.get("title").toString());
            }
            if (sln.get("date") != null) {
                prozedur.setDate(sln.get("date").toString().substring(0, 10));
            }
            if (sln.get("notes") != null) {
                prozedur.setNotes(sln.get("notes").toString());
            }
            return prozedur;

        });
        return list;
    }

    public void delete(String entry) {
        if (temp.getModel() != null) {

            if (temp.getModel().isClosed()) {
                this.connectJenaTemp();
            }
        } else {
            this.connectJenaTemp();
        }

        temp.removeResource(NS + entry);

        //  temp.getModel().write(System.out);
    }

    public void connectJenaTemp() {
        Dataset dataset = TDBFactory.createDataset(url);
        Model model = dataset.getDefaultModel();
        temp.setModel(model);

    }

    public void connectSparqlTemp() {
        Dataset dataset = TDBFactory.createDataset(url);
        Model model = dataset.getDefaultModel();
        sparqlTemp.setModel(model);

    }

    public Model getModel() {
        Dataset dataset = TDBFactory.createDataset(url);
        Model model = dataset.getDefaultModel();
        return model;
    }
}
