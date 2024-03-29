package entities;

import enums.PassDuration;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Entity
@Table(name = "Pass")
public class Pass {
    @Id
    @GeneratedValue
    private long Pass_id;
    @Enumerated(EnumType.STRING)
    private PassDuration passDuration;
    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;
    private LocalDate IssueDate;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


    public Pass() {
    }

    public Pass(PassDuration passDuration, LocalDate issueDate, Card card, Sales sales) {

        this.passDuration = passDuration;

        this.IssueDate = issueDate;
        this.card = card;
        this.sales = sales;

    }

    public static Supplier<Pass> gePassSupplier(EntityManagerFactory emf) {
        Random rdm = new Random();

        return () -> {
            EntityManager eM = emf.createEntityManager();

            TypedQuery<Card> cardQuery = eM.createQuery("SELECT c FROM Card c", Card.class);
            List<Card> cardList = cardQuery.getResultList();
            Card randomCard = cardList.get(rdm.nextInt(cardList.size()));

            TypedQuery<Sales> salesQuery = eM.createQuery("SELECT s FROM Sales s", Sales.class);
            List<Sales> salesList = salesQuery.getResultList();
            Sales randomSale = salesList.get(rdm.nextInt(salesList.size()));

            PassDuration[] passDurations = PassDuration.values();
            int rdmPassDuration = rdm.nextInt(passDurations.length);
            PassDuration passDuration = passDurations[rdmPassDuration];

            LocalDate dateOfIssue;
            if (rdm.nextBoolean()) {
                dateOfIssue = LocalDate.now().minusDays(rdm.nextInt(365));
            } else {
                dateOfIssue = LocalDate.now().minusDays(rdm.nextInt(10));
            }


            return new Pass(passDuration, dateOfIssue, randomCard, randomSale);
        };
    }

    public long getPass_id() {
        return Pass_id;
    }

    public void setPass_id(long pass_id) {
        Pass_id = pass_id;
    }

    public PassDuration getPassDuration() {
        return passDuration;
    }

//    public Sales getIsssue_id() {
//        return Isssue_id;
//    }
//
//    public void setIsssue_id(Sales isssue_id) {
//        Isssue_id = isssue_id;
//    }

//    public int getIssueDate() {
//        return IssueDate;
//    }
//
//    public void setIssueDate(int issueDate) {
//        IssueDate = issueDate;
//    }

    public void setPassDuration(PassDuration passDuration) {
        this.passDuration = passDuration;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public LocalDate getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        IssueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "Pass_id=" + Pass_id +
                ", passDuration=" + passDuration +
//                ", Isssue_id=" + Isssue_id +
                ", IssueDate=" + IssueDate +
                ", card=" + card +
                '}';
    }
}
