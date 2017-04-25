/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiver;

import controller.HiveController;
import controller.TransactionController;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 *
 * @author Vignesh
 */
@ManagedBean
@RequestScoped
public class MessageSender implements Serializable {

    private static final Long serialVersion = 1L;

    private String message;

    @Resource(mappedName = "jms/connecFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/destinationQueue")
    private Queue destinationQueue;

    @EJB
    private TransactionController ctrl;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void send() {
        try {
            HiveController hiveCOntroller = new HiveController();
            //hiveCOntroller.selectData();

            //Main.test();

//            URL url = getClass().getResource("out.txt");
//            File file = new File(url.getPath());
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String top = reader.readLine();
//            JMSContext ctx = connectionFactory.createContext();
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//                JsonObject obj = Json.createObjectBuilder()
//                        .add("latitude", data[1])
//                        .add("longitude", data[2])
//                        .add("fraud", data[3]).build();
//                JMSProducer producer = ctx.createProducer();
//                TextMessage msg = ctx.createTextMessage(obj.toString());
//                producer.send(destinationQueue, msg);
//            }
//            ctx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
