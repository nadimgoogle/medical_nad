/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.jenaspring;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.query.*;
import org.apache.jena.query.text.EntityDefinition;
import org.apache.jena.query.text.TextDatasetFactory;
import org.apache.jena.query.text.TextIndexConfig;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.tdb.TDBFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Build a text search dataset
 */
public class TextSearch {

    static {
        LogCtl.setLog4j();
    }
    static Logger log = LoggerFactory.getLogger("JenaTextExample");

    public Dataset createCode() {
        // Build a text dataset by code.
        // Here , in-memory base data and in-memeory Lucene index

        // Base data
        Dataset ds1 = DatasetFactory.create();

        // Define the index mapping 
        Model model = ds1.getDefaultModel();
        Property k2 = model.createProperty("http://document/KR/uebersicht/notfall");
        Property k3 = model.createProperty("http://document/KR/diagnostik/text");
        Property k4 = model.createProperty("http://document/KR/uebersicht/text");
        Property k5 = model.createProperty("http://document/KR/therapie/text");
        Property k6 = model.createProperty("http://document/KR/beratung/text");
        Property k7 = model.createProperty("http://document/KR/diagnostik/notfall");
        Property k8 = model.createProperty("http://document/KR/uebersicht/notfall");
        Property k9 = model.createProperty("http://document/KR/therapie/notfall");
        Property k10 = model.createProperty("http://document/KR/beratung/notfall");
        Property k11 = model.createProperty("http://document/KR/notes");

        Property p2 = model.createProperty("http://document/PR/uebersicht/notfall");
        Property p3 = model.createProperty("http://document/PR/diagnostik/text");
        Property p4 = model.createProperty("http://document/PR/uebersicht/text");
        Property p5 = model.createProperty("http://document/PR/therapie/text");
        Property p6 = model.createProperty("http://document/PR/beratung/text");
        Property p7 = model.createProperty("http://document/PR/diagnostik/notfall");
        Property p8 = model.createProperty("http://document/PR/uebersicht/notfall");
        Property p9 = model.createProperty("http://document/PR/therapie/notfall");
        Property p10 = model.createProperty("http://document/PR/beratung/notfall");
        Property p11 = model.createProperty("http://document/PR/notes");
        Property pr1 = model.createProperty("http://document/PR/");
        Property pr2 = model.createProperty("http://document/PR/title");
        Property pr3 = model.createProperty("http://document/PR/autor");
        Property pr4 = model.createProperty("http://document/PR/label");
        Property kr1 = model.createProperty("http://document/KR/");
        Property kr2 = model.createProperty("http://document/KR/title");
        Property kr3 = model.createProperty("http://document/KR/autor");
        Property kr4 = model.createProperty("http://document/KR/label");

        EntityDefinition entDef = new EntityDefinition("uri", "text");
        entDef.setUidField("uid");
        entDef.setPrimaryPredicate(pr1.asNode());
        entDef.setPrimaryPredicate(pr2.asNode());
        entDef.setPrimaryPredicate(pr3.asNode());
        entDef.setPrimaryPredicate(pr4.asNode());
        entDef.setPrimaryPredicate(kr1.asNode());
        entDef.setPrimaryPredicate(kr2.asNode());
        entDef.setPrimaryPredicate(kr3.asNode());
        entDef.setPrimaryPredicate(kr4.asNode());
        entDef.setPrimaryPredicate(p2.asNode());
        entDef.setPrimaryPredicate(p3.asNode());
        entDef.setPrimaryPredicate(p4.asNode());
        entDef.setPrimaryPredicate(p5.asNode());
        entDef.setPrimaryPredicate(p6.asNode());
        entDef.setPrimaryPredicate(p7.asNode());
        entDef.setPrimaryPredicate(p8.asNode());
        entDef.setPrimaryPredicate(p9.asNode());
        entDef.setPrimaryPredicate(p10.asNode());
        entDef.setPrimaryPredicate(p11.asNode());

        entDef.setPrimaryPredicate(k2.asNode());
        entDef.setPrimaryPredicate(k3.asNode());
        entDef.setPrimaryPredicate(k4.asNode());
        entDef.setPrimaryPredicate(k5.asNode());
        entDef.setPrimaryPredicate(k6.asNode());
        entDef.setPrimaryPredicate(k7.asNode());
        entDef.setPrimaryPredicate(k8.asNode());
        entDef.setPrimaryPredicate(k9.asNode());
        entDef.setPrimaryPredicate(k10.asNode());
        entDef.setPrimaryPredicate(k11.asNode());

        // Lucene, in memory.
        Directory dir = new RAMDirectory();

        // Join together into a dataset
        Dataset ds = TextDatasetFactory.createLucene(ds1, dir, new TextIndexConfig(entDef));
        return ds;
    }

    public List queryData(Dataset dataset, String searchWord, String tdbUrl) {

        String pre = StrUtils.strjoinNL("PREFIX doc: <http://document/KR/>", "PREFIX text: <http://jena.apache.org/text#>", "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");

        String qs;
//        qs = StrUtils.strjoinNL("SELECT ?s ",
//                " { ?s  text:query (doc:title '" + searchWord + "') ;"
//                + "   doc:title ?label }");
        qs = StrUtils.strjoinNL("SELECT ?s ",
                " { ?s  text:query  '" + searchWord + "' ;}");
        Dataset ds2 = TDBFactory.createDataset(tdbUrl);

        Model model = dataset.getDefaultModel();
        model.add(ds2.getDefaultModel());

        dataset.begin(ReadWrite.READ);
        List list = new ArrayList();

        try {
            Query q = QueryFactory.create(pre + "\n" + qs);
            QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                QuerySolution soln = results.nextSolution();
                RDFNode s = soln.get("s");// Get a result variable by name.

                if (!list.contains(s.toString())) {
                    list.add(s.toString());
                }
            }
        } finally {
            dataset.end();
        }
        return list;

    }

}
