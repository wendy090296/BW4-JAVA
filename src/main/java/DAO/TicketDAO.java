package DAO;

import entities.Ticket;
import enums.Validation;
import exceptions.NotFoundException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


public class TicketDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atm");

    private final EntityManager em;


    public TicketDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Ticket ticket) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(ticket);
        transaction.commit();
        System.out.println("The ticket with id: " + ticket.getTicket_id() + ", has been saved correctly");
    }

    public List<Ticket> findTicketsBySalesId(int sales_id) {
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.sales.sales_id = :salesId", Ticket.class);
        query.setParameter("salesId", sales_id);
        return query.getResultList();
    }

    public List<Ticket> findTicketsByIssueDateRange(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.issueDate BETWEEN :startDate AND :endDate", Ticket.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();

    }

    public List<Ticket> findTicketsComplete(int sales_id, LocalDate startDate, LocalDate endDate) {
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.sales.id = :salesId AND t.issueDate BETWEEN :startDate AND :endDate", Ticket.class);
        query.setParameter("salesId", sales_id);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public long countValidatedTicketsByVehicleId(int vehicleId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Ticket t WHERE t.sales.vehicle.id = :vehicleId AND t.validation = :validatedStatus", Long.class);
        query.setParameter("vehicleId", vehicleId);
        query.setParameter("validatedStatus", Validation.VALIDATED);
        return query.getSingleResult();
    }


    private Ticket getTicketbyID(int ticket_id) {
        Ticket ticket = em.find(Ticket.class, ticket_id);
        if (ticket == null) throw new NotFoundException(ticket_id);
        return ticket;

    }

    public String TicketValidation(int ticket_id) {
        Ticket ticket = getTicketbyID(ticket_id);

        if (ticket != null && ticket.getValidation() == Validation.VALIDATED) {
            return "The ticket has already been VALIDATED";
        } else {
            return "The ticket has not been VALIDATED yet";
        }
    }

    public void updateTicketValidation(int ticketId, int vehicleId, Validation validation) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Query query = em.createQuery("UPDATE Ticket t SET t.validation = :validation, t.vehicle.id = :vehicleId WHERE t.id = :ticketId");
        query.setParameter("validation", validation);
        query.setParameter("vehicleId", vehicleId);
        query.setParameter("ticketId", ticketId);

        query.executeUpdate();

        em.getTransaction().commit();
        em.close();

    }
    public String TicketValidation1(int ticket_id, int vehicle_id) {
        Ticket ticket = getTicketbyID(ticket_id);
            if (ticket.getValidation() == Validation.VALIDATED) {
                return "The ticket has already been validated";
            } else {

                updateTicketValidation(ticket_id, vehicle_id, Validation.VALIDATED);
                return "Validation success";
            }
        }



    public long countValidatedTicketsByVehicleIdAndPeriod(long vehicleId, LocalDate startDate, LocalDate endDate) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Ticket t WHERE t.vehicle.id = :vehicleId AND t.validation = :validation AND t.issueDate >= :startDate AND t.issueDate <= :endDate", Long.class);
        query.setParameter("vehicleId", vehicleId);
        query.setParameter("validation", Validation.VALIDATED);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getSingleResult();
    }




}
