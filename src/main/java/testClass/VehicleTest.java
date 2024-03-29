package testClass;

import DAO.MaintenanceRecordDAO;
import DAO.RouteDAO;
import DAO.TicketDAO;
import DAO.VehicleDAO;
import entities.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static entities.Ticket.getTicketSupplier;

public class VehicleTest {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atm");

    public static void main(String[] args) {

        EntityManager eM = emf.createEntityManager();

        VehicleDAO vDAO = new VehicleDAO(eM);
        RouteDAO rDAO = new RouteDAO(eM);
        MaintenanceRecordDAO mrDAO = new MaintenanceRecordDAO(eM);
        TicketDAO tDAO = new TicketDAO(eM);

//        Supplier<Route> routeSupplier = getRouteSupplier(emf);
//        List<Route> routeList = new ArrayList<>();
//        for (int i = 0; i < 60; i++) {
//            routeList.add(routeSupplier.get());
//        }
//        routeList.forEach(rDAO::save);

//        Supplier<Bus> busSupplier = getBusSupplier();
//        List<Bus> busList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            busList.add(busSupplier.get());
//        }
//        busList.forEach(vDAO::save);
//
//        Supplier<Tram> tramSupplier = getTramSupplier();
//        List<Tram> tramList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            tramList.add(tramSupplier.get());
//        }
//        tramList.forEach(vDAO::save);

//        Supplier<List<MaintenanceRecord>> maintenanceRecordsSupplier = getMaintenanceRecords(emf);
//        List<Vehicle> vehicleList = vDAO.getAllVehicles();
//        List<MaintenanceRecord> maintenanceRecordList = new ArrayList<>();
//
//        for (Vehicle vehicle : vehicleList) {
//            List<MaintenanceRecord> maintenanceRecordsForVehicle = maintenanceRecordsSupplier.get();
//            for (MaintenanceRecord maintenanceRecord : maintenanceRecordsForVehicle) {
//                maintenanceRecord.setVehicle(vehicle);
//                maintenanceRecordList.add(maintenanceRecord);
//            }
//        }
//
//        maintenanceRecordList.forEach(mrDAO::save);
//        Supplier<List<MaintenanceRecord>> maintenanceRecordSupplier = getMaintenanceRecords(emf);
//        List<MaintenanceRecord> maintenanceRecords = maintenanceRecordSupplier.get();
//        maintenanceRecords.forEach(mrDAO::save);
//
//
        List<Ticket> ticketList = new ArrayList<>();
        Supplier<List<Ticket>> ticketSupplier = getTicketSupplier(emf);
        ticketList.addAll(ticketSupplier.get());
        ticketList.forEach(tDAO::save);


        LocalDate today = LocalDate.now();
        LocalDate todayPlusSeven = today.plusDays(7);
        LocalDate dayX = LocalDate.parse("2024-04-11");
        LocalDate dayY = LocalDate.parse("2024-05-11");
//
//        tDAO.findTicketsByIssueDateRange(dayX, dayY).forEach(System.out::println);

        emf.close();
        eM.close();
    }


}
