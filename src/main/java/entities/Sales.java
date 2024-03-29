package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sales")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    protected int id;

    @OneToMany(mappedBy = "sales")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "Pass_id")
    private List<Pass> passes;


    public Sales() {
    }

//    public Sales(int id) {
//        this.id = id;
//    }


    public Sales(List<Ticket> tickets, List<Pass> passes) {
        this.tickets = tickets;
        this.passes = passes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Pass> getPasses() {
        return passes;
    }

    public void setPasses(List<Pass> passes) {
        this.passes = passes;
    }


    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", tickets=" + tickets +
                ", passes=" + passes +
                '}';
    }
}
