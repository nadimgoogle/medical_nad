/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.controller;

import static de.document.controller.UmlsController.Cui;
import de.document.entity.Umls;
import de.document.entity.UmlsList;
import de.document.service.UmlsService;
import de.document.umls.RetrieveCui;
import de.document.umls.checkRelation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MyTEK
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/umls")
public class UmlsController {

    public static List<String> table;
    List<String> tab= new ArrayList();
    public static String Cui=""  ;
    public static String C=""  ;
    public  static List<String> tab1 ;
     public   static List<String> tab2 = new ArrayList();
    public static String umlsterm="";
    private static BigInteger nextId;
  // private static Map<BigInteger, Umls> greetingMap;
  
   private final UmlsService service = new UmlsService();

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Umls> readAll() {
        //List<Umls> list = new ArrayList<>();
       //List<Umls> list = service.readAll();
       
       
       UmlsList u =  new UmlsList(umlsterm);
       System.out.println("hello get");
        return u.list(umlsterm);
    }  
    
        @RequestMapping(value = "/relationGet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
            public Collection<String> rel() throws Exception {
                String s="relationGetrelationGet";
                checkRelation c = new checkRelation();
                System.out.println("relationGET"+c.check());
                List<String> l = new ArrayList();
                //l.add(c.check());
         
                
        //System.out.println("this the relation"+);
                //return c.check();
                return c.check();
            }
    
     @RequestMapping(value = "/send", method = {RequestMethod.POST})
      public @ResponseBody String read(@RequestBody String UmlsTerm) {
        
          umlsterm = UmlsTerm;
          System.out.println(UmlsTerm);
          UmlsList u = new UmlsList(UmlsTerm);
          u.list(UmlsTerm);

        return UmlsTerm;
        
    }
       @RequestMapping(value = "/relation", method = {RequestMethod.POST})
       public @ResponseBody void checkRelation(@RequestBody List<String> table) throws Exception {
           for(int e=0;e<table.size();e++){
               System.out.println(table.get(e)+"hello table");
           }
          // System.out.println(table.get(0)+"hello table");
            
           tab=table;
            
        
            int j=0;
            tab1 = new ArrayList();
            while(!(tab.get(j).equals("000"))&&(j<tab.size())){
                tab1.add(tab.get(j));j++;
            }
            j++;
            tab2 = new ArrayList();
            while(j<tab.size()){
                tab2.add(tab.get(j));j++;

            }
            //tab = null;
           checkRelation c = new checkRelation();
        System.out.println("this the relation"+c.check());
            

        }
       
      
       @RequestMapping(value = "/sendId", method = {RequestMethod.POST})
      public @ResponseBody String readId(@RequestBody String Id) {
        
          System.out.println(Id);
          Cui = Id;
          //RetrieveCui RC = new RetrieveCui(Id);
          //System.out.println(RC.RetrieveCui());

        return Id;
        
    }
      
     
       

        
    
       
      @RequestMapping(value = "/queryId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

      public ResponseEntity<Collection<Umls>> getDef() {
          
          Map<BigInteger, Umls> greetingMap = null;
           RetrieveCui RC = new RetrieveCui(Cui);
           Umls u = new Umls();
           u = RC.RetCui(Cui);
         
           
        if (greetingMap == null) {
            greetingMap = new HashMap<BigInteger, Umls>();
            nextId = BigInteger.ONE;
        }
        
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(nextId, u);
        
               Collection<Umls> greetings = null;
               greetings = greetingMap.values();
                  System.out.println("55555555555555555555555555555555");
                  System.out.println(Cui);
                  System.out.println(greetings.toArray()[0]);
                  System.out.println(u);

                  
                
                  return new ResponseEntity<Collection<Umls>>(greetings,
                HttpStatus.OK);
    }
    
}

