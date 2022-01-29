package services;

import database.HibernateSessionFactoryUtil;
import entities.Client;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ClientService {

    @EJB(name = "ClientRepository")
    private BaseRepository<Client> clientRepository;


    public Client getClientById(Long id) {
        return clientRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Client> getAllClient() {
        return clientRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewClient(Client client) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        clientRepository.save(client, session);
        tx1.commit();
        session.close();
    }

    public void updateClientByName(Long id, String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Client client = clientRepository.findById(id, session);
        client.setFirstName(name);
        clientRepository.update(client,session);
        tx1.commit();
        session.close();
    }

    public void deleteClientById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Client client = clientRepository.findById(id,session);
        clientRepository.delete(client, session);
        tx1.commit();
        session.close();
    }
}
