/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiver;

import controller.TransactionController;
import entity.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import websocket.SocketListener;

/**
 *
 * @author Vignesh
 */
@MessageDriven(mappedName = "jms/destinationQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class DataReceiver implements MessageListener {

    private static final Long serialVersionUID = 1L;
    


    @Inject
    private SocketListener listeners;

    @Override
    public void onMessage(Message message) {
        //TODO : Handle Data
        String msg = "";
        try {
            msg = ((TextMessage) message).getText();
            listeners.message(msg);
            Thread.sleep(100);
        } catch (JMSException ex) {
            Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>>>" + msg);

    }

}
