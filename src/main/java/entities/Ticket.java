package entities;

import enums.Validation;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


@Entity
public class Ticket {
    @Id
    @Column
    @GeneratedValue
    private int ticket_id; // primary key
    @Column
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;
    @Column
    @Enumerated(EnumType.STRING)
    private Validation validation;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Ticket() {
    }

    public Ticket(LocalDate issueDate, Validation stato, Vehicle vehicle, Sales sales) {
        this.vehicle = vehicle;
        this.issueDate = issueDate;
        this.validation = stato;
        this.sales = sales;
    }

    public static Supplier<List<Ticket>> getTicketSupplier(EntityManagerFactory emf) {
        Random rdm = new Random();
        Validation[] validations = Validation.values();

        return () -> {
            EntityManager eM = emf.createEntityManager();

            TypedQuery<Vehicle> vehicleQuery = eM.createQuery("SELECT v from Vehicle v", Vehicle.class);
            List<Vehicle> vehicleList = vehicleQuery.getResultList();


            TypedQuery<Sales> salesQuery = eM.createQuery("SELECT s FROM Sales s WHERE s.status = 'ACTIVE' OR s.status IS NULL", Sales.class);
            List<Sales> salesList = salesQuery.getResultList();

            List<Ticket> ticketsList = new ArrayList<>();

            int rdmTicket = rdm.nextInt(1, 20);

            for (Sales sales : salesList) {
                Vehicle selectedVehicle = null;
                selectedVehicle = vehicleList.get(rdm.nextInt(vehicleList.size()));

                int rdmValidation = rdm.nextInt(validations.length);
                Validation validationSelector = validations[rdmValidation];
                if (Validation.VALIDATED.equals(validationSelector) && !vehicleList.isEmpty()) {
                    selectedVehicle = vehicleList.get(rdm.nextInt(vehicleList.size()));

                } else if (Validation.NOT_VALIDATED.equals(validationSelector)) {
                    selectedVehicle = null;
                }


                for (int i = 0; i < rdmTicket; i++) {
                    LocalDate issueDate = LocalDate.now().minusDays(rdm.nextInt(365));
                    ticketsList.add(new Ticket(issueDate, validationSelector, selectedVehicle, sales));
                }

            }


            eM.close();

            return ticketsList;
        };
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", issueDate=" + issueDate +

                ", validation=" + validation +
                '}';
    }
}