package services;

import entities.Client;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class JmsService {
    @Resource(name = "messageQueue")
    private Queue messageQueue;

    @Resource(name = "messageQueueSecond")
    private Queue messageQueueSecond;

    @Resource
    private ConnectionFactory connectionFactory;

    public String sendJmsMessage(Client client) {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageProducer producer = session.createProducer(messageQueue)) {
            connection.start();
            final ObjectMessage message = session.createObjectMessage(client);
            producer.send(message);
            return "successfylly sended";
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when sending a message", e);
        }
    }

    public String sendJmsMessageSecond(Client client) {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageProducer producer = session.createProducer(messageQueueSecond)) {
            connection.start();
            final ObjectMessage message = session.createObjectMessage(client);
            System.out.println("Test1:" + message.getObject());
            producer.send(message);
            return "successfylly sended";
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when sending a message", e);
        }
    }

    public Client getMessageSecond() {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageConsumer messageConsumer = session.createConsumer(messageQueueSecond)) {

            connection.start();

            final Message jmsMessage = messageConsumer.receive(1000);
            if (jmsMessage == null) {
                return null;
            }
            ObjectMessage message = (ObjectMessage) jmsMessage;


            if (message == null) {
                return null;
            }
            return (Client) message.getObject();
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when receiving a message", e);
        }
    }

    public Client getMessage() {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageConsumer messageConsumer = session.createConsumer(messageQueue)) {

            connection.start();


            final Message jmsMessage = messageConsumer.receive(1000);
            if (jmsMessage == null) {
                return null;
            }
            ObjectMessage message = (ObjectMessage) jmsMessage;

            if (message == null) {
                return null;
            }
            return (Client) message.getObject();
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when receiving a message", e);
        }
    }

}
