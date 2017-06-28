/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.model;

import com.traitement.persistance.catalog.Result;
import com.traitement.persistance.catalog.Word;

import com.traitement.persistance.dll.catalogmngmt.CatalogManagerService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private String echantillon;
    private String keyUsed;
    private Float tauxE;
    private Float tauxC;
    private Float seuil;

    private Boolean isValid;

    
    /**
     * Initie la création d'un result
     * @return chaine de navigation
     */
    public String createResult(){
        result = catalogManager.createResult(file, echantillon, keyUsed, tauxE, tauxC);
        return "";
    }
    
    
    
     /**
     * rechercher la liste de resultats
     */
    public void getAllResults(){
       results = null;
       results = catalogManager.getAllResults();   
    }

   
    public String testFile(){
        
        List<Word> e = getEchantillonFromFile();
        
        seuil = catalogManager.testFile(e, tauxC);
        if(seuil >= tauxC){
            isValid = true;
            
        }else{
            isValid = false;
        }
        
        catalogManager.createResult(file,echantillon,keyUsed,tauxE,seuil);
        
        return "display";
    }
  
     public List<Word> fillListFromString(){
        
        System.out.println("String to list");
        words = words.trim();
        words = words.replaceAll(" ", "");
        String[] list = words.split(",");
        List<Word> wordsList = new ArrayList<>(); 
        for (String l : list) {
            Word w = new Word();
            w.setWord(l);
            wordsList.add(w);
            System.out.println(w.getWord());
        }
        return wordsList;
    }
     
     
     public List<Word> getEchantillonFromFile (){
        
        System.out.println("Echantillonnage");
        
        
        List<Word> list = fillListFromString();
        
        System.out.println("Taille liste mot : "+ list.size());
       
        Random randomGen = new Random();
        List<Word> e = new ArrayList<>();
        int count = (int)(tauxE*list.size());
        
        for( int i = 0 ; i < count ; i++){
            int index = randomGen.nextInt(list.size());
            e.add(list.get(index));
            //on supprime pour ne pas tirer plusieur fois le même mot dans l'échantiller
            list.remove(index);
        }
        
        //remplissage var echantillon
        echantillon = "";
        for(Word w : e){
            echantillon += w.getWord();
            if(!w.equals(e.get(e.size()-1))){
                echantillon += ",";
            }
        }

        return e;
    }

    /*******************/
    /* Getter / Setter */
    /******************/
    
    
    public Float getTauxE() {
        return tauxE;
    }

    public void setTauxE(Float tauxE) {
        this.tauxE = tauxE;
    }

    public Float getTauxC() {
        return tauxC;
    }
    
    public void setTauxC(Float tauxC) {    
        this.tauxC = tauxC;
    }

    public Float getSeuil() {
        return seuil;
    }

    public void setSeuil(Float seuil) {
        this.seuil = seuil;
    }
    
    

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
    

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

    public String getEchantillon() {
        return echantillon;
    }

    public void setEchantillon(String echantillon) {
        this.echantillon = echantillon;
    }

    
    public String getKeyUsed() {
        return keyUsed;
    }

    public void setKeyUsed(String keyUsed) {
        this.keyUsed = keyUsed;
    }


  
}
