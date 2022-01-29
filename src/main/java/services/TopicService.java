package services;

import database.HibernateSessionFactoryUtil;
import entities.Client;
import entities.InterestArea;
import entities.Topic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TopicService {

    @EJB(name = "TopicRepository")
    private BaseRepository<Topic> topicRepository;

    @EJB(name = "InterestAreaRepository")
    private BaseRepository<InterestArea> interestAreaRepository;

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewTopic(Topic topic, Long interestAreaId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        InterestArea interestArea = interestAreaRepository.findById(interestAreaId, session);
        interestArea.addTopic(topic);
        interestAreaRepository.update(interestArea, session);
        tx1.commit();
        session.close();
    }

    public void updateTopic(Long id, String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Topic topic = topicRepository.findById(id, session);
        topic.setName(name);
        topicRepository.update(topic, session);
        tx1.commit();
        session.close();
    }

    public void deleteTopicById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Topic topic = topicRepository.findById(id, session);
        topicRepository.delete(topic, session);
        tx1.commit();
        session.close();
    }
}
