package repositories;

import entities.Client;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="ClientRepository")
public class ClientRepository implements BaseRepository<Client> {
    @Override
    public List<Client> findAll(Session session){
        return session.createQuery("SELECT a FROM Client a", Client.class).getResultList();
    }

    @Override
    public Client findById(Long id, Session session){
        return session.get(Client.class, id);
    }

    @Override
    public void save(Client client, Session session) {
        session.save(client);
    }

    @Override
    public void update(Client client, Session session) {
        session.update(client);
    }

    @Override
    public void delete(Client client, Session session) {
        session.delete(client);
    }
}
