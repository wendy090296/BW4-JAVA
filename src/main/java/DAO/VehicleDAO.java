package DAO;

import entities.MaintenanceRecord;
import entities.Ticket;
import entities.Vehicle;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    private static List<MaintenanceRecord> maintenanceDatabase = new ArrayList<>();
    private final EntityManager em;

    public VehicleDAO(EntityManager em) {
        this.em = em;
    }

    public static void addMaintenanceRecord(MaintenanceRecord record) {
        maintenanceDatabase.add(record);
    }

    public List<MaintenanceRecord> getVehicleMaintenanceHistory(Long vehicle_id) {
        TypedQuery<MaintenanceRecord> query = em.createQuery("SELECT mr FROM MaintenanceRecord mr WHERE mr.vehicle.id = :vehicleId", MaintenanceRecord.class);
        query.setParameter("vehicleId", vehicle_id);
        return query.getResultList();
    }

    public void save(Vehicle vehicle) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(vehicle);
        transaction.commit();
        System.out.println("The vehicle with id: " + vehicle.getId() + ", has been saved correctly");
    }

    public List<Vehicle> getAllVehicles() {
        TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v", Vehicle.class);
        return query.getResultList();
    }

    public Vehicle getVehiclebyId(long vehicle_id) {
        Vehicle vehicle = em.find(Vehicle.class, vehicle_id);
        if (vehicle == null) throw new NotFoundException(vehicle_id);
        return vehicle;

    }

    public void checkMaintenance(long vehicle_id) {
        Vehicle vehicle = getVehiclebyId(vehicle_id);
        try {
            if (vehicle != null) {
                LocalDate today = LocalDate.now();
                if (today.isBefore(vehicle.getMaintenanceStartDate()) && today.isAfter(vehicle.getMaintenanceEndDate())) {
                    System.out.println("The vehicle is ON DUTY.");
                } else{
                    System.out.println("The vehicle is ON MAINTENANCE.");
                }
            } else {
                System.out.println("Vehicle by id: " + vehicle_id + " not found");
            }
        } catch (NullPointerException e) {
            System.out.println("The vehicle is ON DUTY.");
        }
    }

}



