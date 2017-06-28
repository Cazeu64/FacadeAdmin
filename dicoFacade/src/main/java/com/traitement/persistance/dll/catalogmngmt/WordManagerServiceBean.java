/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traitement.persistance.dll.catalogmngmt;


import com.traitement.persistance.catalog.Word;
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
@Stateless(name="WordManager")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean //facultatif
public class WordManagerServiceBean {

   @PersistenceContext(unitName = "wPu")
   EntityManager em;

   @PreDestroy
   void prevent(){
       System.out.println("instance va être détruite");
   }

   /**
    *
    * @param w mot à persister
    * sauvegarder en base l'état d'un mot nouvellement créé
    * @return le mot persisté
    */
    public Word saveWord(Word w){      
        em.persist(w);
        System.out.println("Word created : "+w.toString());
        return w;
    }

    
    /**
    * Retourner un mot en fonction d'une identité unique
    * @param wordId id mot recherché
    * @return le mot correspondant à l'id passée en argument
    */
    public Word findWordById(Long wordId) {
       Word wordObj = em.find(Word.class,wordId);
        return wordObj;
    }
    
     /**
    *
    * supprimer un mot
    * @param wordObj le mot a supprimé. Si word est null, l'opération de suppression n'est pas exécutée
    */
    public void deleteWord(Word wordObj) { 
        if(wordObj!=null){
            em.remove(wordObj);
        }    
    }
    

   /**
     * retourner une liste de mots contenant l'argument passé en paramètre.
     * Comportement transactionnel redéfini (SUPPORTS)
     * @param pattern motif permettant de retrouver une liste de mots
     * contenant le motif dans le mot
     * @return la liste de mot possédant le motif
     */ 
    @TransactionAttribute(TransactionAttributeType.SUPPORTS) //méthode pouvant joindre le contexte transactionnel de l'appelant
    public List<Word> findByCriteria(String pattern) {
        pattern="%"+pattern+"%";//pour retrouver des mots qui contiennent le motif
        String q= "SELECT w From Word w where w.word LIKE :pattern";
        TypedQuery<Word> query = em.createQuery(q,Word.class);
        query.setParameter("pattern", pattern);
        List<Word> words = query.getResultList();
          
        
        return words;
     
    }
    
    
     /**
     * retourner le mot correspondant à la recherche
     * Comportement transactionnel redéfini (SUPPORTS)
     * @param word le mot à trouver
     * @return le mot trouvé
     */ 
    @TransactionAttribute(TransactionAttributeType.SUPPORTS) //méthode pouvant joindre le contexte transactionnel de l'appelant
    public Word findWord(String research) {
        research = "'"+research+"'";
        String q= "SELECT w From Word w where w.word = "+research;
        TypedQuery<Word> query = em.createQuery(q,Word.class);
        //query.setParameter("research", research);
        List<Word> words = query.getResultList();
          
        if(words.isEmpty()){
            return null;
        }else{
             return words.get(0);
        }

    }
    
    
    /**
     * modifier un word et synchroniser l'état modifié avec la base.
     * Le comportement transactionnel de la méthode est redéfini. l'attribut transactionnel est REQUIRED.
     * Ainsi l'opération de mise à jour nécessitant un contexte transactionnel peut être invoquée depuis un bean CDI ne s'exécutant pas dans une transaction.
     * @param wordObj livre existant (dans la base) à modifier
     * @return le livre modifié
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Word updateWord(Word wordObj) {
         //ATTENTION
         //l'instance référencée par b est managée . merge retourne une instance
         //managée de l'entité passée en argument . book est toujours détachée
        
        Word b = em.merge(wordObj);// une copie de word étant managée, ses modifications seront repercutées dans la base
                                //on utilise l'attribut cascade=CascadeType.MERGE dans
                              
        return b;
    }

    
}
