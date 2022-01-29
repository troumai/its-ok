package repositories;

import database.HibernateSessionFactoryUtil;
import entities.Topic;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name = "TopicRepository")
public class TopicRepository implements BaseRepository<Topic> {

    @Override
    public List<Topic> findAll(Session session) {
        return null;
    }

    @Override
    public Topic findById(Long id, Session session) {
        return session.get(Topic.class, id);
    }

    @Override
    public void save(Topic topic, Session session) {
        session.save(topic);
    }

    @Override
    public void update(Topic topic, Session session) {
        session.update(topic);
    }

    @Override
    public void delete(Topic topic, Session session) {
        session.delete(topic);
    }
}
