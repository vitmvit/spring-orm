package hiber.dao.impl;

import hiber.dao.UserDao;
import hiber.exception.NotFoundException;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final String FIND_USER_BY_MODEL_AND_SERIES_QUERY = "from User user where user.car.model = :model and user.car.series = :series";
    private final String FIND_ALL_USERS = "from User";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object findUserByCarModelAndSeries(String model, int series) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(FIND_USER_BY_MODEL_AND_SERIES_QUERY)
                    .setParameter("model", model)
                    .setParameter("series", series);
            return query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            throw new NotFoundException("Entity nit found!");
        }
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(FIND_ALL_USERS);
        return query.getResultList();
    }
}