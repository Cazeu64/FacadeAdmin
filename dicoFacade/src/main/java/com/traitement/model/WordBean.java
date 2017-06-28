/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.model;

import com.traitement.persistance.catalog.Word;
import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;
import com.traitement.persistance.dll.catalogmngmt.WordManagerServiceBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;


/**
 *
 * @author Mimi
 */
@Named
@SessionScoped
public class WordBean implements Serializable {

   
    @Inject
    private CatalogManagerService catalogManager;
    
    @Inject
    private WordManagerServiceBean wordManager;
    
    
    //permet d'activer le mode édition pour un mot - permet de spécifier qu'elle balise JSF est "affichée" dans la vue
    private Boolean updatable = true;
    
    private Long wordId;
    private String word;
    
    //variable utilisée pour recherchée un mot
    private String pattern;
    private Word wordObj;
    List<Word> words;
    
     /**
     * Initie la création d'un mot
     * @return chaine de navigation
     */
    public String createWord(){
        wordObj = catalogManager.createWord(word);
        if(word == null){  
            return null;
        }
        
        //réinitialisation des variables
        wordId = null;
        word = null;
        
        return "display";
    }
    
    
    /**
     * rechercher une liste de mot en fonction d'un motif dans le titre
     */
    public void findMatchingPatternWords(){
       words = null;
       words = wordManager.findByCriteria(pattern); 
       
    }
    
    /**
     * méthode permettant d'activer le mode édition pour modifier un livre
     */
    public void activeUpdate(){
        updatable=true;
    }
    
     /**
     * méthode initiant le processus de modification d'un word
     */
    public void update(){
        wordObj = wordManager.updateWord(wordObj);
        updatable=false;
    }
  
    /*******************/
    /* Getter / Setter */
    /*******************/

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public String getPattern() {
        return pattern;
    }

    public Word getWordObj() {
        return wordObj;
    }

    public List<Word> getWords() {
        return words;
    }
    
    public Boolean getUpdatable() {
        return updatable;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setWordObj(Word wordObj) {
        this.wordObj = wordObj;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
    
    

       
}
