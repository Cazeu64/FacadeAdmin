/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Mimi
 */
@Entity
public class TestFile {
    private double coeff = 0.9;
    private int percent = 70;
    private String test = "abécédaire," +
        "abeille," +
        "aber," +
        "aberrant," +
        "aberration," +
        "abêtir," +
        "abhorrer," +
        "Abidjan," +
        "abîme," +
        "abîmer," +
        "abject," +
        "abjection," +
        "abjurer," +
        "ablation," +
        "aboiement," +
        "aboli," +
        "abolir," +
        "abolition," +
        "abolitionnisme,"+
        "abcd,"+
        "aerjdsh,"+
        "sjkgtv";
    @Id
    private Long id;

    public TestFile() {
    }
    
   

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
   
}
