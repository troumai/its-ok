package services;

import database.HibernateSessionFactoryUtil;
import entities.InterestArea;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repositories.BaseRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class InterestAreaService {
    @EJB(name = "InterestAreaRepository")
    private BaseRepository<InterestArea> interestAreaRepository;

    public InterestArea getInterestAreaById(Long id) {
        return interestAreaRepository.findById(id, HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public List<InterestArea> getAllInterestArea() {
        return interestAreaRepository.findAll(HibernateSessionFactoryUtil.getSessionFactory().openSession());
    }

    public void createNewInterestArea(InterestArea interestArea) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        interestAreaRepository.save(interestArea, session);
        tx1.commit();
        session.close();
    }

    public void updateInterestAreaByName(Long id, String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        InterestArea interestArea = interestAreaRepository.findById(id, session);
        interestArea.setName(name);
        interestAreaRepository.update(interestArea,session);
        tx1.commit();
        session.close();
    }

    public void deleteInterestAreaById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        InterestArea interestArea = interestAreaRepository.findById(id, session);
        interestAreaRepository.delete(interestArea, session);
        tx1.commit();
        session.close();
    }
}
