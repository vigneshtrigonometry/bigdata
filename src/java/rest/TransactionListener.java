/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import controller.TransactionController;
import entity.Transaction;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import websocket.SocketListener;

/**
 *
 * @author Vignesh
 */
@RequestScoped
@Path("/transactions")
public class TransactionListener {

    @EJB
    private TransactionController ctrlr;
    @Inject
    private SocketListener listener;

    @POST
    @Consumes("application/json")
    public void receiveTransaction(RequestBody body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId(Long.parseLong(body.txid));
            transaction.setAccountNo(Long.parseLong(body.account_id));
            transaction.setCity(body.city);
            transaction.setCounty(body.county);
            transaction.setDeviceId(Integer.parseInt(body.device_id));
            transaction.setIsFraud(Integer.parseInt(body.fraud) == 1 ? Boolean.TRUE : Boolean.FALSE);
            transaction.setLatitude(Float.parseFloat(body.lat));
            transaction.setLongitude(Float.parseFloat(body.lon));
            transaction.setMerchantName(body.merchant_name);
            transaction.setState(body.state);
            transaction.setTransactionAmt(Float.parseFloat(body.tx_val));
            transaction.setZipCode(Integer.parseInt(body.zip));
            ctrlr.create(transaction);
            JsonObject obj = Json.createObjectBuilder()
                    .add("latitude", body.lat)
                    .add("longitude", body.lon)
                    .add("fraud", body.fraud).build();
            listener.message(obj.toString());

        } catch (Exception ex) {

        }

    }

}
