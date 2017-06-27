/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.traitement.persistance.dll.catalogmngmt;



import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * service facade (application service).<br>
 * Fait le pont entre la présentation et la logique métier<br>
 * Composant coarse grained (forte granularité) chargé de la réalisation des processus métiers<br>
 * Expose une vue locale au travers d'une interface métier annotée @Local
 */
@Stateless(name="CatalogManager")
//options par défaut - on peut se passer de cette anotation - convention over configuration
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)//on démarrage une nouvelle transaction si le client n'en n'a pas démarrée.
@LocalBean //obligatoire si on veut que le SB expose d'autres vues (locales ou "remote")
public class CatalogManagerServiceBean implements CatalogManagerService {
     //on aurait pu utiliser @Inject vu qu'on injecte des composants co-localisés dans la même instance de JVM (car dans la même archive)

    @EJB
    private WordManagerServiceBean wordManager;

    @EJB
    private ResultManagerServiceBean resultManager;
    
    /**
    * Créer un nouveau mot<br>
    * Délègue le traitement à WordManager
    * @param word mot à insérer en base.<br>
    * @return le mot créé ou null si le mot passé en argument est null
    */
    @Override
    public Word createWord(String word) {
        
        System.out.println("New word : "+word);
        Word wordObj = new Word();
        wordObj.setWord(word);
        
        wordObj = wordManager.saveWord(wordObj); 
        
        return wordObj;
    }

    
    /**
    * Rechercher les mots correspondant à un pattern<br>
    * Délègue le traitement à WordManager
    * @param pattern le pattern à rechercher<br>
    * @return la liste de mots correspondants au pattern
    */
    @Override
    public List<Word> findWord(String pattern) {
        
        List<Word> words =  wordManager.findByCriteria(pattern);
        //la liste ayant un chargement différé (LAZY), il faut invoquer une opération (ici size()) dessus pour que les éléments soient chargés 
        words.size();
        return words;
    }


    @Override
    public List<Result> getAllResults() {
       List<Result> results = resultManager.getAllResults();
       results.size();
       return results;
    }

    @Override
    public Result createResult(String file, String words, String key) {
        Result res = new Result();
        res.setFile(file);
        res.setKeyUsed(key);
        res.setWords(words);
        
        res = resultManager.saveResult(res); 
        
        return res;
    }

   
    

}//fin classe
