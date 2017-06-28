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
    
    private List<String> listWords;
    
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
    
    /*
    *
    */
    public Word retrieveWord(String word) {
        return wordManager.findWord(word);
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
    public List<String> getAllWords() {
       List<String> results = wordManager.getAllWords();
       results.size();
       return results;
    }


    @Override
    public List<Result> getAllResults() {
       List<Result> results = resultManager.getAllResults();
       results.size();
       return results;
    }

    @Override
    public Result createResult(String file, String echantillon, String key, Float tauxE, Float tauxR) {
        Result res = new Result();
        res.setFile(file);
        res.setKeyUsed(key);
        res.setEchantillon(echantillon);
        res.setTauxE(tauxE);
        res.setTauxR(tauxR);
        
        res = resultManager.saveResult(res); 
        
        return res;
    }


    @Override
    public Float testFile(List<Word> echantillon, Float tauxC){
        
        int count = 0;
        System.out.println("Taille echantillon : "+echantillon.size());
        for(Word w :  echantillon){
          
           Word result = retrieveWord(w.getWord());
           if(result != null){
               count++;
           }
                   
           System.out.println("Nombre de mot trouvés :"+count);
        }
        
        System.out.println("Nombre de mot trouvés : "+count);
        //ratio de mots trouvés en fr sur le nombre de mots dans la liste
        Float ratio = Float.valueOf(count) / Float.valueOf(echantillon.size()) ;
        System.out.println("Ratio mot trouvé / nbr de mots dans echantillon : "+ratio.toString());
        float tauxReel = ratio * tauxC;
        System.out.println("Taux de confiance reel :"+ tauxReel);
        return tauxReel;
    }
    
    @Override
    public Float testFileInList(List<Word> echantillon, Float tauxC){
        
        int count = 0;
        System.out.println("Taille echantillon : "+echantillon.size());
        
        if(listWords == null){
            System.out.println("Chargement de la liste de mots");
            listWords = wordManager.getAllWords();
        }
        
        for(Word w :  echantillon){
           
           if(listWords.contains(w.getWord())){
         
               count++;
           }
                   
           System.out.println("Nombre de mot trouvés :"+count);
        }
        
        System.out.println("Nombre de mot trouvés : "+count);
        //ratio de mots trouvés en fr sur le nombre de mots dans la liste
        Float ratio = Float.valueOf(count) / Float.valueOf(echantillon.size()) ;
        System.out.println("Ratio mot trouvé / nbr de mots dans echantillon : "+ratio.toString());
        float tauxReel = ratio * tauxC;
        System.out.println("Taux de confiance reel :"+ tauxReel);
        return tauxReel;
    }

   
    

}//fin classe
