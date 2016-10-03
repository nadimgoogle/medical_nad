/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.umls;

import de.document.controller.UmlsController;
import static de.document.controller.UmlsController.tab1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author MyTEK
 */
public class checkRelation {
    String relations;
    String s="";
    
       List<String> tab1 = UmlsController.tab1;
       List<String> tab2 = UmlsController.tab2;
       List<String> rel = new ArrayList();
       
       

   List<String> l =new ArrayList();
          
       public List<String> check() throws Exception{
           
          
          List<String> listRelation =new ArrayList();
          
           
       
          
           
           relations="";
           System.out.println("tab1 and tab2 in check relation class");
           //System.out.println(tab1.get(0));
           //System.out.println(tab1.get(1));
           //System.out.println(tab2.get(0));
           //System.out.println(tab2.get(1));
           listRelation=l;
           for(int g =0; g<tab1.size();g++){
               System.out.println(tab1.size()+"tab1");
           System.out.println(tab1.get(g));}
           for(int i =0; i<tab1.size();i++){
               CuiTest c = new CuiTest(tab1.get(i));
               int k=0;
               
              
               List<relation> listR=c.RetrieveCui();// ListRi
               for(int j =0;j<listR.size();j++){
                   
                  if( tab2.contains(listR.get(j).getCui())) {
                      
                      
                      relations= tab1.get(i)+" and  "+listR.get(j).getCui()+", The related name is: "+listR.get(j).getRelatedIdName()+".";
                     
                      if(!rel.contains(relations)){
                         
                      rel.add(relations);
                      if(relations!=""){
                     
                      listRelation.add(0,relations);}
                     
                     k++;
                      
                      //System.out.println("inside method check"+listRelation.get(0)+"listrelation");
                      }
               
                  
                  }
               }
               
               
              // tab1= null;
               //tab2= null;
           }
           return listRelation;
           
       }
 
}
