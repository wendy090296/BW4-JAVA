package DAO;

import entities.Pass;
import enums.PassDuration;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PassDAO {
    private final EntityManager em;

    public PassDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Pass pass) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(pass);
        transaction.commit();
        System.out.println("The pass with id: " + pass.getPass_id() + ", has been saved correctly");
    }

    public Pass findPassesByIssueId(long Pass_id) {
       TypedQuery<Pass> query = em.createQuery(
                "SELECT p FROM Pass p WHERE p.Pass_id = :Pass_id", Pass.class);
            query.setParameter("Pass_id", Pass_id);
        Pass pass = query.getSingleResult();
        return pass;
    }
    public Pass passValidation(long pass_id) {
        Pass pass = findPassesByIssueId(pass_id);

        if (pass == null) {
            throw new NotFoundException(pass_id);
        }
        return pass;
    }

    public List<Pass> findPassesByIssueDateRange(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Pass> query = em.createQuery(
                "SELECT p FROM Pass p WHERE p.IssueDate BETWEEN :startDate AND :endDate", Pass.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<Pass> findPassesComplete(long issueId, LocalDate startDate, LocalDate endDate) {
        TypedQuery<Pass> query = em.createQuery(
                "SELECT p FROM Pass p WHERE p.issue.id = :issueId AND p.issueDate BETWEEN :startDate AND :endDate", Pass.class);
        query.setParameter("issueId", issueId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public long countPassesSoldBySellerInPeriod(long sellerId, LocalDate startDate, LocalDate endDate) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Pass p WHERE p.sales.id = :sellerId AND p.IssueDate BETWEEN :startDate AND :endDate", Long.class);
        query.setParameter("sellerId", sellerId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getSingleResult();
    }

    public String checkPassValidity(Pass pass) {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate;

        if (pass.getPassDuration() == PassDuration.MONTHLY) {
            expiryDate = pass.getIssueDate().plusDays(30);
        } else if (pass.getPassDuration() == PassDuration.WEEKLY) {
            expiryDate = pass.getIssueDate().plusDays(7);
        } else {
            throw new IllegalArgumentException("Unknown PassDuration value");
        }

        return today.isAfter(expiryDate) ? "Pass expired" : "Pass valid";
    }


}