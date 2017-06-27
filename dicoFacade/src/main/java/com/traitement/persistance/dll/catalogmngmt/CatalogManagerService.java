/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;


import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author asbriglio
 */
@Local
public interface CatalogManagerService {

    Word createWord(String word);
    List<Word> findWord(String pattern);
    
    List<Result> getAllResults();

    public Result createResult(String file, String words, String key);

    
}
