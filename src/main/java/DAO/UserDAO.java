package DAO;

import entities.Card;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserDAO {

    private final EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public void save(User user){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("The user with id: " + user.getUser_id() + ", has been correctly saved");
    }

    public User findById(long user_id){
        User user = em.find(User.class,user_id);
        return user;
    }
    }

