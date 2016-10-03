/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.entity;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.Response;
import static de.document.controller.UmlsController.umlsterm;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.given;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author MyTEK
 */
public class UmlsList {
    private static BigInteger nextId;
    private static Map<BigInteger, Umls> greetingMap;
    private String term;
    
    
     public UmlsList(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    
    
     public Collection<Umls> list(String term){
         greetingMap=null;
         Collection<Umls> greetings = null;
         
        String version;
        this.term= term;
	RestTicketClient ticketClient = new RestTicketClient("tmar01","Master2016");
	//get a ticket granting ticket for this session.
	String tgt = ticketClient.getTgt();
        
        version = System.getProperty("version") == null ? "current": System.getProperty("version");

		
		int total = 0;
		int pageNumber = 0;
		SearchResult[] results;                  
                File file = new File("C:\\Users\\MyTEK\\Documents\\NetBeansProjects\\Medical-application\\src\\main\\resources\\Umls"+umlsterm);
                
                String content ="";
                int k=0;
		do  {
			pageNumber++;
			System.out.println("Fetching results for page "+pageNumber);
	    	RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
	    	Response response =  given()//.log().all()
	                .request().with()
	                	.param("ticket", ticketClient.getST(tgt))
	                	.param("string", term)
	                	.param("pageNumber",pageNumber)
	                	//uncomment to return CUIs that have at least one matching term from the US Edition of SNOMED CT
	                	//.param("sabs", "SNOMEDCT_US")
	                	//uncomment to return SNOMED CT concepts rather than UMLS CUIs.
	                	//.param("returnIdType", "sourceConcept")
	                	//.param("searchType","exact") //valid values are exact,words, approximate,leftTruncation,rightTruncation, and normalizedString
	        	 .expect()
	       		 .statusCode(200)
	        	 .when().get("/rest/search/"+version);
	    	
	    	String output = response.getBody().asString();
			Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
			results = JsonPath.using(config).parse(output).read("$.result.results",SearchResult[].class);

	    	//the /search endpoint returns an array of 'result' objects
	    	//See http://documentation.uts.nlm.nih.gov/rest/search/index.html#sample-output for a complete list of fields output under the /search endpoint
	    	
                
                
                
                for(SearchResult result:results) {
                    
	    		k++;
                        if(k==10)break;
	    		String ui = result.getUi();
	    		String name = result.getName();
	    		String rootSource = result.getRootSource();
	    		String uri = result.getUri();
	    		System.out.println("ui: " + ui);
	    		System.out.println("name: " + name);
	    		//System.out.println("rootSource: " + rootSource);
	    		//System.out.println("uri: " + uri);
	    		
	    		System.out.println("**");
                        //list.get(k).setName(name);
                        //list.get(k).setUi(ui);
                         
                        //System.out.println(list.get(k).getName()+" " +list.get(k).getUi());
                        
                        Umls u = new Umls(name);
                        u.setName(name);
                        u.setUi(ui);
                if (greetingMap == null) {
                 greetingMap = new HashMap<BigInteger, Umls>();
                  nextId = BigInteger.ONE;
        }
        
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(nextId, u);
        
               greetings = greetingMap.values();
                                
	    	}
	    	System.out.println("----------");
	    	total += results.length;
                
                
		}while((!results[0].getUi().equals("NONE"))&&(k<10));
		//account for the one 'NO RESULTS' result :-/
                System.out.println("hello");
                
         System.out.println(greetings);
              
		total--;
		System.out.println("Found " + total+ " results for "+ term);
                return greetings;
    }
    
}
