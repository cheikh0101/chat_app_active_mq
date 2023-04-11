package discussion;

import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    
    private static String subject = "JCG_QUEUE";
    
    public static void main(String[] args) throws JMSException {  
    	
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        
        //creation de la connexion
        Connection myConnection = connectionFactory.createConnection();
        
        // il faut demarrer la connexion
        myConnection.start();
        
        //creation de la session
        Session mySession = myConnection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);  
        
        // creation d'une file d'attente au niveau du broker
        Destination destination = mySession.createQueue(subject); 
        
        // creation de l'objet producer grace a l'objet session
        MessageProducer producer = mySession.createProducer(destination);
         
        // creation du message
        TextMessage myMessage = mySession
                .createTextMessage("Message emis par lemetteur ...");
        producer.send(myMessage);
         
        System.out.println("Emetteur : " + myMessage.getText() + "'");
        
        // fermer la connexion
        myConnection.close();
    }
    
}
