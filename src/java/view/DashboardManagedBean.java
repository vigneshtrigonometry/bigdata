/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.TransactionController;
import entity.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Vignesh
 */
@RequestScoped
@Named
public class DashboardManagedBean implements Serializable {

    private static final Long serialVersionUID = 1L;
    @EJB
    private TransactionController txCtrl;
    private List<Transaction> transactionList;
    
    public DashboardManagedBean(){
        transactionList = new ArrayList<Transaction>();
    }

    @PostConstruct
    public void init() {
        transactionList = null;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
    
    

}
