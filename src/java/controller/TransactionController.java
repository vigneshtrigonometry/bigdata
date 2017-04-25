/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Transaction;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author Vignesh
 */
@Stateless
public class TransactionController {

    @PersistenceContext(unitName = "MyApplicationPU")
    EntityManager em;

    public void create(Transaction tx) {
        em.persist(tx);
    }

    public List<Transaction> getAllTransactionsForCity(String city) {
        List<Transaction> result = em.createNamedQuery(Transaction.FINDBYCITY, Transaction.class).setFirstResult(20).getResultList();
        return result;
    }

    public List<Transaction> getAllSuccessfulTransactionsForCity(String city) {
        return em.createNamedQuery(Transaction.FINDBYNONFRAUD_BYCITY, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllFraudTransactionsForCity(String city) {
        return em.createNamedQuery(Transaction.FINDBYFRAUD_BYCITY, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllTransactionsForState(String state) {
        List<Transaction> result = em.createNamedQuery(Transaction.FINDBYSTATE, Transaction.class).setFirstResult(20).getResultList();
        return result;
    }

    public List<Transaction> getAllSuccessfulTransactionsForState(String state) {
        return em.createNamedQuery(Transaction.FINDBYNONFRAUD_BYSTATE, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllFraudTransactionsForState(String state) {
        return em.createNamedQuery(Transaction.FINDBYFRAUD_BYSTATE, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<String> getAllCities() {
        return em.createNamedQuery(Transaction.DISTINCT_CITY, String.class).setFirstResult(20).getResultList();
    }

    public List<String> getAllStates() {
        return em.createNamedQuery(Transaction.DISTINCT_STATE, String.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllSuccessfulTransactions() {
        return em.createNamedQuery(Transaction.FINDALLSUCCESS, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllFraudTransactions() {
        return em.createNamedQuery(Transaction.FINDALLFRAUD, Transaction.class).setFirstResult(20).getResultList();
    }

    public List<Transaction> getAllTransactions() {
        return em.createNamedQuery(Transaction.FINDALL, Transaction.class).setFirstResult(20).getResultList();
    }

}
