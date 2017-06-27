/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.model;

import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mimi
 */
@Named
@SessionScoped
public class ResultBean implements Serializable{
    
    @Inject
    private CatalogManagerService catalogManager;
   
    private Result result;
    private List<Result> results;
    private String file;
    private String words;
    private String key;
    
    
    /**
     * Initie la création d'un result
     * @return chaine de navigation
     */
    public String createResult(){
        result = catalogManager.createResult(file, words, key);
        return "";
    }
    
    
     /**
     * rechercher la liste de resultats
     */
    public void getAllResults(){
       results = null;
       results = catalogManager.getAllResults();   
    }
    
    
  

    /*******************/
    /* Getter / Setter */
    /******************/
    

    
    
    public List<Result> getResults() {
        return results;
    }
    
    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


  
}
