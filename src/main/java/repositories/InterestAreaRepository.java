package repositories;

import entities.InterestArea;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name = "InterestAreaRepository")
public class InterestAreaRepository implements BaseRepository<InterestArea> {
    @Override
    public List<InterestArea> findAll(Session session) {
        return session.createQuery("SELECT a FROM InterestArea a", InterestArea.class).getResultList();
    }

    @Override
    public InterestArea findById(Long id, Session session) {
        return session.get(InterestArea.class, id);
    }

    @Override
    public void save(InterestArea interestArea, Session session) {
        session.save(interestArea);
    }

    @Override
    public void update(InterestArea interestArea, Session session) {
        session.saveOrUpdate(interestArea);
    }

    @Override
    public void delete(InterestArea interestArea, Session session) {
        session.delete(interestArea);
    }
}
