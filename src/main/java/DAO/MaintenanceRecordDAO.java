package DAO;

import entities.MaintenanceRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MaintenanceRecordDAO {
    private final EntityManager em;

    public MaintenanceRecordDAO(EntityManager em) {
        this.em = em;
    }

    public void save(MaintenanceRecord record) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(record);
        transaction.commit();
        System.out.println("The record with id: " + record.getIdRecord() + ", has been saved correctly");
    }

}
