/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.service;

import de.document.entity.ICDNummer;
import de.document.entity.Krankheit;
import de.document.entity.Prozedur;
import de.document.jenaspring.JenaTemplate;
import de.document.jenaspring.SparqlTemplate;
import de.document.util.IdService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bilel-PC
 */
@Service
public class ICDNummerService {

    private final JenaTemplate temp = new JenaTemplate();
    private final SparqlTemplate sparqlTemp = new SparqlTemplate();
    private final String NS = "http://ICDNummer/";
    private final String NSHaupt = "http://ICDNummer/haupt/";
    private final String NSGefah = "http://ICDNummer/gefaehrlich/";
    private final String url = "C:\\Users\\MyTEK\\Documents\\NetBeansProjects\\Medical-application\\TDB\\test2";
    KrankheitService krankheitService = new KrankheitService();
    ProzedurService prozedurService = new ProzedurService();

    public ICDNummer saveGesamt(ICDNummer entry) {

        try {
            entry = (ICDNummer) BeanUtils.cloneBean(entry);
            if (temp.getModel() != null) {

                if (temp.getModel().isClosed()) {
                    this.connectJenaTemp();
                }
            } else {
                this.connectJenaTemp();
            }
            // System.out.println(temp.getModel().isClosed());
            //temp.removeResource(NS + "krankheit/" + entry.getTitle());

            entry.setId(IdService.next());
            temp.addResource(NS, NS + "has", NS + entry.getId());
            temp.add(NS + entry.getId(), NS + "code", entry.getCode());
            temp.add(NS + entry.getId(), NS + "diagnose", entry.getDiagnose());
            temp.add(NS + entry.getId(), NS + "type", entry.getType());

            if (!temp.getModel().isClosed()) {
                temp.getModel().close();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return entry;

    }

    public ICDNummer updateGesamt(ICDNummer entry) {

        try {
            entry = (ICDNummer) BeanUtils.cloneBean(entry);
            if (temp.getModel() != null) {

                if (temp.getModel().isClosed()) {
                    this.connectJenaTemp();
                }
            } else {
                this.connectJenaTemp();
            }
            // System.out.println(temp.getModel().isClosed());
            //temp.removeResource(NS + "krankheit/" + entry.getTitle());

            temp.removeResource(NS + entry.getId());
            temp.addResource(NS, NS + "has", NS + entry.getId());
            temp.add(NS + entry.getId(), NS + "code", entry.getCode());
            temp.add(NS + entry.getId(), NS + "diagnose", entry.getDiagnose());
            temp.add(NS + entry.getId(), NS + "type", entry.getType());

            if (!temp.getModel().isClosed()) {
                temp.getModel().close();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return entry;

    }

    public void delete(String entry) {
        if (temp.getModel() != null) {

            if (temp.getModel().isClosed()) {
                this.connectJenaTemp();
            }
        } else {
            this.connectJenaTemp();
        }
        temp.removeResource(NS + readId(entry));
    }

    public List<ICDNummer> readAll() {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }
        //   sparqlTemp.getModel().write(System.out);

        String sparql = "PREFIX icd: <http://ICDNummer/>"
                + "SELECT ?x ?code ?diagnose ?type  WHERE {"
                + " ?x icd:code ?code. "
                + " ?x icd:diagnose ?diagnose. "
                + " ?x icd:type ?type. "
                + "}";
        List<ICDNummer> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            ICDNummer icdNummer = new ICDNummer();

            if (sln.get("code") != null) {
                icdNummer.setCode(sln.get("code").toString());
            }
            if (sln.get("diagnose") != null) {
                icdNummer.setDiagnose(sln.get("diagnose").toString());
            }
            if (sln.get("type") != null) {
                icdNummer.setType(sln.get("type").toString());
            }
            if (sln.get("x") != null) {
                String x = sln.get("x").toString();
                icdNummer.setId(x.replaceAll("http://ICDNummer/", ""));

            }
            return icdNummer;

        });
        return list;
    }

    public ICDNummer read(String code) {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }
        String sparql = "PREFIX icd: <http://ICDNummer/>"
                + "SELECT ?diagnose ?type ?x WHERE {"
                + " ?x icd:code '" + code + "'. "
                + " ?x icd:diagnose ?diagnose. "
                + " ?x icd:type ?type. "
                + "}";
        List<ICDNummer> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            ICDNummer icdNummer = new ICDNummer();

            icdNummer.setCode(code);

            if (sln.get("diagnose") != null) {
                icdNummer.setDiagnose(sln.get("diagnose").toString());
            }
            if (sln.get("type") != null) {
                icdNummer.setType(sln.get("type").toString());
            }
            if (sln.get("x") != null) {
                String x = sln.get("x").toString();
                icdNummer.setId(x.replaceAll("http://ICDNummer/", ""));

            }
            return icdNummer;

        });
        return list.get(0);

    }

    public String readId(String code) {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }
        String sparql = "PREFIX icd: <http://ICDNummer/>"
                + "SELECT ?x   WHERE {"
                + " ?x icd:code '" + code + "'. "
                + "}";
        List<String> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            String y = null;

            if (sln.get("x") != null) {
                String x = sln.get("x").toString();
                y = x.replaceAll("http://ICDNummer/", "");

            }

            return y;

        });
        return list.get(0);

    }

    public List<ICDNummer> readHaupt() {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }
        //       sparqlTemp.getModel().write(System.out);

        String sparql = "PREFIX icd: <http://ICDNummer/>"
                + "SELECT ?code ?diagnose  WHERE {"
                + " ?x icd:code ?code. "
                + " ?x icd:diagnose ?diagnose. "
                + " ?x icd:type 'Hauptdiagnose'. "
                + "}";
        List<ICDNummer> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            ICDNummer icdNummer = new ICDNummer();

            if (sln.get("code") != null) {
                icdNummer.setCode(sln.get("code").toString());
            }
            if (sln.get("diagnose") != null) {
                icdNummer.setDiagnose(sln.get("diagnose").toString());
            }

            return icdNummer;

        });
        return list;
    }

    public List<ICDNummer> readGefaehrlich() {

        if (sparqlTemp.getModel() != null) {

            if (sparqlTemp.getModel().isClosed()) {
                this.connectSparqlTemp();
            }
        } else {
            this.connectSparqlTemp();
        }
        //       sparqlTemp.getModel().write(System.out);

        String sparql = "PREFIX icd: <http://ICDNummer/>"
                + "SELECT ?code ?diagnose  WHERE {"
                + " ?x icd:code ?code. "
                + " ?x icd:diagnose ?diagnose. "
                + " ?x icd:type 'Gefährlich'. "
                + "}";
        List<ICDNummer> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
            QuerySolution sln = rs.nextSolution();

            ICDNummer icdNummer = new ICDNummer();

            if (sln.get("code") != null) {
                icdNummer.setCode(sln.get("code").toString());
            }
            if (sln.get("diagnose") != null) {
                icdNummer.setDiagnose(sln.get("diagnose").toString());
            }

            return icdNummer;

        });
        return list;
    }

    public boolean searchHauptICDNummer(String text) throws IOException, ParseException {

        boolean b = false;
        List list = this.readHaupt();
        for (Iterator it = list.iterator(); it.hasNext();) {
            ICDNummer icd = (ICDNummer) it.next();

            int intIndex = text.indexOf(icd.getCode());
            if (intIndex == - 1) {
            } else {
                System.out.println("Found icd at index "
                        + intIndex);
                b = true;

            }
        }
        return b;
    }

    public void notesBearbeiten(Object request) {
        List list = (List) request;
        HashMap h1 = (HashMap) list.get(0);
        String alteCode = (String) h1.get("alteCode");
        HashMap h2 = (HashMap) list.get(1);
        String neuCode = (String) h2.get("neuCode");
        HashMap h3 = (HashMap) list.get(2);
        List<Krankheit> krankheits = (List<Krankheit>) h3.get("krankheits");
        HashMap h4 = (HashMap) list.get(3);
        List<Prozedur> prozedurs = (List<Prozedur>) h4.get("prozedurs");
        for (Iterator it = krankheits.iterator(); it.hasNext();) {
            HashMap hashMap = (HashMap) it.next();
            String title = (String) hashMap.get("title");
            Krankheit kr = this.krankheitService.read(title);
            String notes = kr.getNotes();
            if (notes != null) {
                String note = notes.replaceAll(alteCode, neuCode);
                kr.setNotes(note);
                this.krankheitService.save(kr);
            }
        }
        for (Iterator it = prozedurs.iterator(); it.hasNext();) {
            HashMap hashMap = (HashMap) it.next();
            String title = (String) hashMap.get("title");
            Prozedur pr = this.prozedurService.read(title);
            String notes = pr.getNotes();
            if (notes != null) {
                String note = notes.replaceAll(alteCode, neuCode);
                pr.setNotes(note);
                this.prozedurService.save(pr);
            }
        }
    }

    ;
    public void notesEntfernen(Object request) {
        List list = (List) request;
        HashMap h1 = (HashMap) list.get(0);
        String code = (String) h1.get("code");
        HashMap h2 = (HashMap) list.get(1);
        List<Krankheit> krankheits = (List<Krankheit>) h2.get("krankheits");
        HashMap h3 = (HashMap) list.get(2);
        List<Prozedur> prozedurs = (List<Prozedur>) h3.get("prozedurs");
        for (Iterator it = krankheits.iterator(); it.hasNext();) {
            HashMap hashMap = (HashMap) it.next();
            String title = (String) hashMap.get("title");
            Krankheit kr = this.krankheitService.read(title);
            String notes = kr.getNotes();
            if (notes != null) {
                String note = notes.replaceAll(code+" ", " ");
                kr.setNotes(note);
                this.krankheitService.save(kr);
            }
        }
        for (Iterator it = prozedurs.iterator(); it.hasNext();) {
            HashMap hashMap = (HashMap) it.next();
            String title = (String) hashMap.get("title");
            Prozedur pr = this.prozedurService.read(title);
            String notes = pr.getNotes();
            if (notes != null) {
                String note = notes.replaceAll(code+" ", " ");
                pr.setNotes(note);
                this.prozedurService.save(pr);
            }
        }
    };
    public boolean searchGefahrlichICDNummer(String text) throws IOException, ParseException {

        boolean b = false;
        List list = this.readGefaehrlich();
        for (Iterator it = list.iterator(); it.hasNext();) {
            ICDNummer icd = (ICDNummer) it.next();

            int intIndex = text.indexOf(icd.getCode());
            if (intIndex == - 1) {
            } else {
                System.out.println("Found icd at index "
                        + intIndex);
                b = true;

            }
        }
        return b;
    }

    public HashMap searchUsedICDNummer(String code) throws IOException, ParseException {
        List listKrankheits = krankheitService.readAll();
        List listProzedurs = prozedurService.readAll();

        List krankheits = new ArrayList();
        List prozedurs = new ArrayList();
        for (Iterator it = listKrankheits.iterator(); it.hasNext();) {
            Krankheit krankheit = (Krankheit) it.next();
            String note = krankheit.getNotes();
            if (note != null) {
                int intIndex = note.indexOf(code+" ");
                if (intIndex == - 1) {
                } else {
                    System.out.println("Found icd at index "
                            + intIndex);
                    krankheits.add(krankheit);

                }
            }
        }
        for (Iterator itPr = listProzedurs.iterator(); itPr.hasNext();) {
            Prozedur prozedur = (Prozedur) itPr.next();
            String notePr = prozedur.getNotes();
            if (notePr != null) {
                int intIndex = notePr.indexOf(code+" ");
                if (intIndex == - 1) {
                } else {
                    System.out.println("Found icd at prozedur index "
                            + intIndex);
                    prozedurs.add(prozedur);

                }
            }
        }
        HashMap result = new HashMap();
        result.put("krankheits", krankheits);
        result.put("prozedurs", prozedurs);
        return result;
    }

    public void saveAll() {

        String csvFile = "C:\\Users\\Bilel-PC\\Desktop\\ICD.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        try {
            if (temp.getModel() != null) {

                if (temp.getModel().isClosed()) {
                    this.connectJenaTemp();
                }
            } else {
                this.connectJenaTemp();
            }
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                if (i != 0) {
                    String[] ICDNummer = line.split(cvsSplitBy);

                    if (ICDNummer[1].equals("")) {
                        System.out.println(ICDNummer[0]);
                    } else {
                        temp.addResource(NS, NS + "has", NS + ICDNummer[1]);
                        temp.add(NS + ICDNummer[1], NS + "code", ICDNummer[1]);
                        temp.add(NS + ICDNummer[1], NS + "diagnose", ICDNummer[0]);
                    }

                }
                i++;
            }
            //  temp.getModel().write(System.out);
            if (!temp.getModel().isClosed()) {
                temp.getModel().close();
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        System.out.println("Done");
    }

    public void saveHauptDiagnose() {

        String csvFile = "C:\\Users\\Bilel-PC\\Desktop\\ICDHauptdiagnose.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        try {
            if (temp.getModel() != null) {

                if (temp.getModel().isClosed()) {
                    this.connectJenaTemp();
                }
            } else {
                this.connectJenaTemp();
            }
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                if (i != 0) {
                    String[] ICDNummerHaupt = line.split(cvsSplitBy);

                    if (ICDNummerHaupt[1].equals("")) {
                    } else {
                        temp.addResource(NSHaupt, NSHaupt + "has", NSHaupt + ICDNummerHaupt[1]);
                        temp.add(NSHaupt + ICDNummerHaupt[1], NSHaupt + "code", ICDNummerHaupt[1]);
                        temp.add(NSHaupt + ICDNummerHaupt[1], NSHaupt + "diagnose", ICDNummerHaupt[0]);
                    }

                }
                i++;
            }
            //    temp.getModel().write(System.out);
            if (!temp.getModel().isClosed()) {
                temp.getModel().close();
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        System.out.println("Done");

    }

//  public void saveNebenDiagnose(String version) {
//
//        String csvFile = "C:\\Users\\Bilel-PC\\Desktop\\ICDNebendiagnose.csv";
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ";";
//        int i = 0;
//        try {
//            if (temp.getModel() != null) {
//
//                if (temp.getModel().isClosed()) {
//                    this.connectJenaTemp();
//                }
//            } else {
//                this.connectJenaTemp();
//            }
//            br = new BufferedReader(new FileReader(csvFile));
//            while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                if (i != 0) {
//                    String[] ICDNummerNeben = line.split(cvsSplitBy);
//
//                    if ("".equals(ICDNummerNeben[1])) {
//                    } else {
//                        temp.removeProperty(NSNeben, NSNeben + "default");
//                        temp.addResource(NSNeben, NSNeben + "default", NSNeben + version);
//                        temp.addResource(NSNeben, NSNeben + "version", NSNeben + version);
//                        temp.addResource(NSNeben + version, NSNeben +"has", NSNeben + version+"/" + ICDNummerNeben[1]);
//                        temp.add(NSNeben +  version+"/" + ICDNummerNeben[1], NSNeben +"code", ICDNummerNeben[1]);
//                        temp.add(NSNeben +  version+"/" + ICDNummerNeben[1], NSNeben +"diagnose", ICDNummerNeben[0]);
//                    }
//
//                }
//                i++;
//            }
//            // temp.getModel().write(System.out);
//            if (!temp.getModel().isClosed()) {
//                temp.getModel().close();
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        System.out.println("Done");
//
//    }
//
//    public List<String> readVersionNeben() {
//
//        if (sparqlTemp.getModel() != null) {
//
//            if (sparqlTemp.getModel().isClosed()) {
//                this.connectSparqlTemp();
//            }
//        } else {
//            this.connectSparqlTemp();
//        }
//         sparqlTemp.getModel().write(System.out);
//
//        String sparql = "PREFIX icd: <http://ICDNummer/neben/>"
//                + "SELECT ?version  WHERE {"
//                + " ?x icd:version ?version. "
//
//                + "}";
//        List<String> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
//            QuerySolution sln = rs.nextSolution();
//
//            String version = null;
//            if (sln.get("version") != null) {
//                version = sln.get("version").toString();
//            }
//
//            return version;
//
//        });
//        return list;
//    }
//
//    public List<ICDNummer> readDefaultNeben() {
//
//        if (sparqlTemp.getModel() != null) {
//
//            if (sparqlTemp.getModel().isClosed()) {
//                this.connectSparqlTemp();
//            }
//        } else {
//            this.connectSparqlTemp();
//        }
//        // sparqlTemp.getModel().write(System.out);
//
//        String sparql = "PREFIX icd: <http://ICDNummer/neben/>"
//                + "SELECT ?code ?diagnose   WHERE {"
//                + " ?x icd:default ?version. "
//                + " ?version icd:has ?y. "
//                + " ?y icd:diagnose ?diagnose. "
//                + " ?y icd:code ?code. "
//                + "}";
//        List<ICDNummer> list = sparqlTemp.execSelectList(sparql, (ResultSet rs, int rowNum) -> {
//            QuerySolution sln = rs.nextSolution();
//
//            ICDNummer icdNummer = new ICDNummer();
//
//            if (sln.get("code") != null) {
//                icdNummer.setCode(sln.get("code").toString());
//            }
//            if (sln.get("diagnose") != null) {
//                icdNummer.setDiagnose(sln.get("diagnose").toString());
//            }
//
//            return icdNummer;
//
//        });
//        return list;
//    }
//
//    public HashMap comparator(List<ICDNummer> l1, List<ICDNummer> l2) {
//        ArrayList<ICDNummer> cp1 = new ArrayList<>(l1);
//        ArrayList<ICDNummer> cp2 = new ArrayList<>(l2);
//
//        for (ICDNummer icdL2 : l2) {
//            for (ICDNummer icdL1 : l1) {
//                if (icdL2.getCode().equals(icdL1.getCode())) {
//
//                    cp1.remove(icdL1);
//                    cp2.remove(icdL2);
//
//                }
//            }
//        }
//        HashMap result = new HashMap();
//        result.put("list1", cp1);
//        result.put("list2", cp2);
//        return result;
//    };
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
