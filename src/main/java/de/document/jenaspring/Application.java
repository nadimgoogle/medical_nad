/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.jenaspring;

import de.document.controller.KrankheitController;
import de.document.entity.UmlsList;
//import de.document.controller.ProzedurController;
import java.net.URISyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Bilel-PC
 */
@SpringBootApplication
@Configuration
//@ComponentScan(basePackageClasses = {KrankheitController.class, ProzedurController.class})
@ComponentScan(basePackageClasses = {KrankheitController.class})

public class Application {

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(Application.class, args);
        
        
    }
}
