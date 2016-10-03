package de.document.umls;

import org.junit.Test;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import de.document.controller.UmlsController;
import java.io.InputStream;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
//import static org.apache.commons.lang.StringUtils.join;
//import uts.rest.samples.util.Rest;

 import org.json.*;


public class CuiTest {
  
	//String username = System.getProperty("username"); 
	//String password = System.getProperty("password");
	String apiKey = System.getProperty("apikey");
	String id = "";
	String version = System.getProperty("version");
	Rest ticketClient = new Rest("3c7a9167-cfda-41b6-975f-08b2d156bc82");
	//get a ticket granting ticket for this session.
	String tgt = ticketClient.getTgt();
        relation r = new relation();
      
        List<String> tab = UmlsController.tab1;
        public CuiTest(String id){
            this.id = id;
        }
        
       

	
	public List<relation> RetrieveCui() throws Exception {
		
		    //if you omit the -Dversion parameter, use 'current' as the default.
		    version = System.getProperty("version") == null ? "current": System.getProperty("version");
		    String path = "/rest/content/"+version+"/CUI/"+id+"/relations?ticket="+ ticketClient.getST(tgt) ;	   
                
			RestAssured.baseURI = "https://uts-ws.nlm.nih.gov";
	    	Response response =  given()//.log().all()
	                .request().with()
	                	.param("ticket", ticketClient.getST(tgt))
	        	 .expect()
	       		 .statusCode(200)
	        	 .when().get(path);
	        	 //response.then().log().all();;   
                         //System.out.println(response.path("result.classType").getClass());
                         ArrayList l1 = new ArrayList();
                         l1=response.path("result");
	    	String output = response.getBody().asString();
               
                
 List<relation> list = new ArrayList<>();
 List<relation> list1 = new ArrayList<>();
                for(int h=0;h<l1.size();h++){
                HashMap hm = (HashMap) l1.get(h);
     
               
                String s,s1;
                // Get a set of the entries
                Set set = hm.entrySet();
                // Get an iterator
                 Iterator j = set.iterator();
                // Display elements
                 while(j.hasNext()) {
                 
                 Map.Entry me = (Map.Entry)j.next();
        
                  relation rr= new relation();
                s= (String) me.getKey().toString();
                s1 =(String) me.getValue().toString();
                rr.setCui(s);
                rr.setRelatedIdName(s1);
                 list.add(rr);
      
      }}
                String cui="";
               
               for(int k =0;k<list.size();k++){
                   //System.out.println(list.get(k).getCui());
                   if (list.get(k).getCui().equals("relatedId")){
                       relation n = new relation();
                       cui =list.get(k).getRelatedIdName();
                       n.setCui(cui.substring(cui.indexOf("CUI")+4,cui.indexOf("CUI")+12));
                       n.setRelatedIdName(list.get(k+1).getRelatedIdName());
                       list1.add(n);
                   }
               }
              //for(int f =0;f<list1.size();f++){
                  //System.out.println(list1.get(f).getCui()+"--22"+list1.get(f).getRelatedIdName());
              //}

      
	return list1;
        }
}