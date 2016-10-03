/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.umls;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.Response;
import de.document.entity.RestTicketClient;
import org.junit.Test;
import com.codesnippets4all.json.parsers.JSONParser;
import org.junit.Test;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;
import de.document.entity.Umls;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minidev.json.JSONObject;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.given;
//import static org.apache.commons.lang.StringUtils.join;

/**
 *
 * @author MyTEK
 */
public class RetrieveCui {

	String username = System.getProperty("username"); 
	String password = System.getProperty("password");
	//String id = "C0155502";
        String id;
         public RetrieveCui(String id) {
        this.id=id;
            }
	String version = System.getProperty("version");
	RestTicketClient ticketClient = new RestTicketClient("tmar01","Master2016");
        RestTicketClient ticketClient2 = new RestTicketClient("tmar01","Master2016");

	//get a ticket granting ticket for this session.
	String tgt = ticketClient.getTgt();
        String tgt2 = ticketClient2.getTgt();
         private static BigInteger nextId;


	
	public Umls RetCui(String id)  {
		
		    //if you omit the -Dversion parameter, use 'current' as the default.
		    version = System.getProperty("version") == null ? "current": System.getProperty("version");
		    String path = "/rest/content/"+version+"/CUI/"+id;	    	
			RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
	    	Response response =  given()//.log().all()
	                .request().with()
	                	.param("ticket", ticketClient.getST(tgt))
	        	 .expect()
	       		 .statusCode(200)
	        	 .when().get(path);
	        	 //response.then().log().all();;     
	    	String output = response.getBody().asString();
                       
			Configuration config = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
			ConceptLite conceptLite = JsonPath.using(config).parse(output).read("$.result",ConceptLite.class);
			
                        
 			System.out.println(conceptLite.getUi()+": "+conceptLite.getName());
			//System.out.println("Semantic Type(s): "+ join(conceptLite.getSemanticTypes(),","));
			System.out.println("Number of Atoms: " + conceptLite.getAtomCount());
			System.out.println("Atoms: "+conceptLite.getAtoms());
			System.out.println("Relations: "+conceptLite.getRelations());
			//System.out.println("Highest Ranking Atom: "+conceptLite.getDefaultPreferredAtom());
                        System.out.println("Definitions: "+conceptLite.getDefinitions());
                        //System.out.println(ticketClient.getST(tgt));
                        //ST-115269-Mvb5oLFpIgQElKaKWVsN-cas
                        String s = conceptLite.getDefinitions();
                         if(s.charAt(0)!='N'&&s.charAt(1)!='O'&&s.charAt(2)!='N'&&s.charAt(3)!='E') 
                        {
                        
                         String path2 = "/rest/content/"+version+"/CUI/"+id+"/definitions?ticket="+ticketClient.getST(tgt);	    	
			RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
                        Response response2 =  given().log().all()
	                .request().with()
	                	.param("ticket", ticketClient.getST(tgt))
	        	 .expect()
	       		 .statusCode(200)
	        	 .when().get(path2);
	        	 //response.then().log().all();;     
                    String output2 = response2.getBody().asString();
                           
                   // System.out.println(output2);
                    //extract the definition 
                    System.out.println(output2.substring(output2.indexOf("value")+8,output2.indexOf(".\"}")));
                    String def =output2.substring(output2.indexOf("value")+8,output2.indexOf("\"}"));
                    
                    
                     HashMap hm = conceptLite.getSemanticTypes().get(0);
                      Set set = hm.entrySet();
                     // Get an iterator
                     Iterator i = set.iterator();
                     // Display elements

                        Map.Entry me = (Map.Entry)i.next();
                        System.out.print(me.getKey() + ": ");
                        System.out.println(me.getValue());
                        
                       // def =def + "\r\n"+"Semantic Type"+me.getKey() + ": "+me.getValue()+"\r\n"+"Number of Atoms: " + conceptLite.getAtomCount();
                        
                        Umls u = new Umls();
                        //u.setNumberOfAtom(conceptLite.getAtomCount());
                        u.setUi(id);
                        u.setDefinition(def);
                        u.setNumberOfAtom(conceptLite.getAtomCount());
                        
                        u.setSemanticType(me.getValue().toString());
                        
                        return u;}
                         else{
                                 Umls u = new Umls();
                        //u.setNumberOfAtom(conceptLite.getAtomCount());
                        u.setUi("");
                        u.setName("");
                        u.setDefinition("There is no available definition");
                        return u;
                             
                         }
	}}