package DAO;

import entities.Route;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Random;

public class RouteDAO {
    private final EntityManager em;

    public RouteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Route route) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(route);
        transaction.commit();
        System.out.println("The route with id: " + route.getId() + ", has been saved correctly");
    }

    private Route getRouteById(long route_id) {
        Route route = em.find(Route.class, route_id);
        if (route == null) throw new NotFoundException(route_id);
        return route;
    }

    public void RouteData(long route_id) {
        Route myroute = getRouteById(route_id);
        Random random = new Random();
        int numberOfRoute = 1 + random.nextInt(5);
        System.out.println("Vehicle:" + myroute.getVehicle());
        System.out.println("Number of Routes: " + numberOfRoute);
        System.out.println("Actual Route Time: " + myroute.getActualRouteTime() + " minutes");
    }
}


