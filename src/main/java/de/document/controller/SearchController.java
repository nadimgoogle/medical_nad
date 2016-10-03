/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.controller;

import de.document.service.SearchService;
import java.io.IOException;
import java.util.HashMap;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bilel-PC
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/search")
public class SearchController {

    SearchService service = new SearchService();

    
  @RequestMapping(value = "/{word}", method = {RequestMethod.GET})
    public HashMap search(@PathVariable("word") String word) throws IOException, ParseException {
        
        HashMap entity = this.service.searchText(word);
        System.out.println(entity.size());
        return entity;
    }
}
