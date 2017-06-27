/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.dll.catalogmngmt;

import com.traitement.persistance.catalog.Result;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mimi
 */
@Stateless(name="ResultManager")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean
public class ResultManagerServiceBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
   @PersistenceContext(unitName = "wPu")
   EntityManager em;
   
   @PreDestroy
   void prevent(){
       System.out.println("instance va être détruite");
   }

   /**
    *
    * @param r result à persister
    * sauvegarder en base l'état d'un result nouvellement créé
    * @return le result persisté
    */
    public Result saveResult(Result r){      
        em.persist(r);
        System.out.println("Result created : "+r.toString());
        return r;
    }

    
    /**
    * Retourner un mot en fonction d'une identité unique
    * @param resultId id resultat recherché
    * @return le resultat correspondant à l'id passée en argument
    */
    public Result findResultById(Long resultId) {
       Result result = em.find(Result.class,resultId);
       return result;
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS) //méthode pouvant joindre le contexte transactionnel de l'appelant
    public List<Result> getAllResults() {
      
        String q= "SELECT r FROM Result r";
        TypedQuery<Result> query = em.createQuery(q,Result.class);
       
        List<Result> results = query.getResultList();
        return results;
     
    }
    
     /**
    *
    * supprimer un resultat
    * @param result le resultat a supprimé. Si result est null, l'opération de suppression n'est pas exécutée
    */
    public void deleteResult(Result result) { 
        if(result!=null){
            em.remove(result);
        }    
    }
    
    
    
    
}
