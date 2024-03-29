package testClass;

import DAO.CardDAO;
import DAO.PassDAO;
import DAO.SalesDAO;
import DAO.UserDAO;
import com.github.javafaker.Faker;
import entities.Pass;
import enums.PassDuration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PassTest {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atm");

    public static void main(String[] args) {
        List<PassDuration> passDurations = List.of(PassDuration.values());
        EntityManager em = emf.createEntityManager();

        UserDAO udao = new UserDAO(em);
        SalesDAO sdao = new SalesDAO(em);
        PassDAO pdao = new PassDAO(em);
        CardDAO cdao = new CardDAO(em);
        Faker faker = new Faker();
        Random random = new Random();

//        Supplier<Pass> passSupplier = () -> (new Pass(PassDuration.WEEKLY,2020));
        for (int i = 0; i < 30; i++) {
            pdao.save(new Pass(passDurations.get(random.nextInt(0, passDurations.size())), LocalDate.now().plusDays(random.nextInt(365)), cdao.findById(random.nextInt(2, 29)), sdao.findById(random.nextInt(1, 19))));

        }


        em.close();
        emf.close();


    }
}
