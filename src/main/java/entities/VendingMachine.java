package entities;

import enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Random;
import java.util.function.Supplier;

@Entity

public class VendingMachine extends Sales {

    @Enumerated(EnumType.STRING)
    private Status status;

    public VendingMachine() {
    }

    public VendingMachine(Status status) {
//        super(id);
        this.status = status;
    }


    public static Supplier<VendingMachine> getVendingMachineSupplier() {
        Random rdm = new Random();
        Status[] statuses = Status.values();

        return () -> {
            int rdmStatus = rdm.nextInt(statuses.length);
            Status status = statuses[rdmStatus];
            return new VendingMachine(status);
        };

    }

    @Override
    public String toString() {
        return "VendingMachine{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
