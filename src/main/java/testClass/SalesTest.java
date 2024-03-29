package testClass;

import DAO.SalesDAO;
import com.github.javafaker.Faker;
import entities.Sales;
import entities.VendingMachine;
import entities.VerifiedSupplier;
import enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SalesTest {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atm");

    public static void main(String[] args) {
        EntityManager eM = emf.createEntityManager();
        SalesDAO salesDAO = new SalesDAO(eM);
        Faker faker = new Faker();
        Random random = new Random();
        List<Status> colorList = Arrays.stream(Status.values())
                .collect(Collectors.toList());


        Supplier<Sales> supplier = () -> (new VerifiedSupplier(faker.harryPotter().character()));
        Supplier<Sales> supplier1 = () -> (new VendingMachine(colorList.get(random.nextInt(0, colorList.size()))));

        for (int i = 0; i < 15; i++) {
            salesDAO.save(supplier.get());
            salesDAO.save(supplier1.get());
        }

    }
}
