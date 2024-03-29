package testClass;

import DAO.CardDAO;
import DAO.UserDAO;
import com.github.javafaker.Faker;
import entities.Card;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class UserTest {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atm");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        UserDAO udao = new UserDAO(em);
        CardDAO cdao = new CardDAO(em);

        Faker faker = new Faker();
        Random random = new Random();

        Supplier<User> userSupplier = () -> (new User(faker.harryPotter().character(), faker.lordOfTheRings().character()));
        for (int i = 0; i < 30; i++) {
            udao.save(userSupplier.get());
            System.out.println(userSupplier.get());


        }

        Set<Card> cardSet = new HashSet<>();
        for (int i = 1; i < 30; i++) {
            cdao.save(new Card(LocalDate.now().plusDays(random.nextInt(365)), udao.findById(i)));

        }

    }

}
