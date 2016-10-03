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
import java.util.Map;

import org.apache.jena.query.ResultSet;

/**
 * SolutionDimensionalMapper
 * 
 * Map a result set based on the key and value of the result set
 * 
 * @author Al Baker
 * @author Michael Soren
 * @param <T>
 * @param <V>
 *
 */
public interface SolutionDimensionalMapper<T, V> {

	Map<T, V> mapSelect(ResultSet rs, int rowNum);
	
}