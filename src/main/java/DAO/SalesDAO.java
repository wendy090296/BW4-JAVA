package DAO;

import entities.Sales;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class SalesDAO {

    private final EntityManager em;

    public SalesDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Sales sales) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(sales);
        transaction.commit();
        System.out.println("The seller with id:" + sales.getId() + " has been saved correctly");
    }

    public Sales findById(long id) {
        Sales sales = em.find(Sales.class, id);
        return sales;
    }
}
