package DAO;

import entities.Card;
import entities.Pass;
import entities.User;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

public class CardDAO {
    private final EntityManager em;


    public CardDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Card card){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(card);
        transaction.commit();
        System.out.println("The card: " + card.getCardNumber() + ", has been correctly saved");
    }

    public Card findById(long card_id){
        Card card = em.find(Card.class,card_id);
        return card;
    }

    public String ValidityByUserId(long userId) {
        LocalDate today = LocalDate.now();
        TypedQuery<Card> query = em.createQuery(
                "SELECT c FROM Card c WHERE c.user.id = :userId", Card.class);
        query.setParameter("userId", userId);
        Card card = query.getSingleResult();

        if (card.getExpiryDate().isAfter(today)) {
            return "Your card is valid! ";
        } else {
            return "Your card IS NOT VALID!.";
        }
    }
    public String validityCard (long userId){
        Card card =findById(userId);
        LocalDate today = LocalDate.now();
        if (card == null) {throw new NotFoundException(userId);
        }else{ if (card.getExpiryDate().isAfter(today)) {
            return "Your card is valid! ENJOY YOUR TRIP  .";
        } else {
            return "Your card IS NOT VALID!.";
        }}





    }
}
