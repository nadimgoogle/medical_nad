/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.document.jenaspring;

/**
 *
 * @author Bilel-PC
 */
import org.apache.jena.query.ResultSet;

/**
 * <code>SolutionMapper<T></code>
 * 
 * Template interface for abstracting how SPARQL result sets are mapped to other objects
 * Inspired by Spring's jdbcTemplate
 * 
 * @author Al Baker
 * @author Michael Soren
 *
 */
public interface SolutionMapper<T> {

	/**
	 * <code>mapSelect</code>
	 * 
	 * Method to map a Jena SELECT ResultSet to an object of type T
	 * 
	 * @param rs Jena ResultSet
	 * @param rowNum represents the current row number (not really used)
	 * @return template type T
	 */
	T mapSelect(ResultSet rs, int rowNum);
	
}