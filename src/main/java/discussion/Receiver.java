package discussion;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String subject = "JCG_QUEUE";
 
    public static void main(String[] args) throws JMSException {
    	
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        
        Connection myConnection = connectionFactory.createConnection();
        
        myConnection.start();
 
        Session session = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
        Destination myDestination = session.createQueue(subject);
 
        MessageConsumer sendToConsumer = session.createConsumer(myDestination);
        
        sendToConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if (message instanceof TextMessage) {
		        	
		        	//on fait un casting au format TextMessage
		            TextMessage textMessage = (TextMessage) message;
		            
		            try {
						System.out.println("Recepteur : '" + textMessage.getText() + "'");
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						System.out.println("Recepteur : '" + message + "'");
						e.printStackTrace();
					}
		        }
			}
		});
 
//        Message myMessage = sendToConsumer.receive();
        
        // verifier si le message recu est de type texte
//        if (myMessage instanceof TextMessage) {
//        	
//        	//on fait un casting au format TextMessage
//            TextMessage textMessage = (TextMessage) myMessage;
//            
//            System.out.println("Recepteur : '" + textMessage.getText() + "'");
//        }
        myConnection.close();
    }
    
}
