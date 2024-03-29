package entities;

import enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.Supplier;

@Entity
public class Bus extends Vehicle {
    @Column(name = "max_capacity_bus")
    private int maxCapacityBus = 80;

    public Bus() {
    }

    public Bus(ServiceType serviceType, LocalDate startDate, LocalDate endDate, int validatedTicket, Long period) {
        super(serviceType, startDate, endDate, validatedTicket, period);
    }

    public static Supplier<Bus> getBusSupplier() {
        Random rdm = new Random();
        ServiceType[] serviceTypes = ServiceType.values();


        return () -> {
            int rdmServiceType = rdm.nextInt(serviceTypes.length);
            ServiceType serviceType = serviceTypes[rdmServiceType];

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.now();
            Long period = null;
            if (ServiceType.ON_DUTY.equals(serviceType)) {
                startDate = startDate.plusDays(rdm.nextInt(730));
                endDate = startDate.plusDays(rdm.nextInt(7, 60));
                long periodInMaintenance = ChronoUnit.DAYS.between(startDate, endDate);
                period = periodInMaintenance;

            } else if (ServiceType.IN_MAINTENANCE.equals(serviceType)) {
                startDate = LocalDate.now().plusDays(rdm.nextInt(730));
                endDate = startDate.plusDays(rdm.nextInt(7, 60));
                long periodInMaintenance = ChronoUnit.DAYS.between(startDate, endDate);
                period = periodInMaintenance;

            }


            int validatedTicket = rdm.nextInt(1000);

            return new Bus(serviceType, startDate, endDate, validatedTicket, period);
        };
    }

    public int getMaxCapacityBus() {
        return maxCapacityBus;
    }

    public void setMaxCapacityBus(int maxCapacityBus) {
        this.maxCapacityBus = maxCapacityBus;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "maxCapacityBus=" + maxCapacityBus +
                ", id=" + id +
                ", serviceType=" + serviceType +
                ", dutyStartDate=" + dutyStartDate +
                ", maintenanceStartDate=" + maintenanceStartDate +
                ", dutyEndDate=" + dutyEndDate +
                ", maintenanceEndDate=" + maintenanceEndDate +
                ", validatedTicket=" + validatedTicket +
                ", periodOnDuty=" + periodOnDuty +
                ", periodOnMaintenance=" + periodOnMaintenance +
                ", maintenanceRecords=" + maintenanceRecords +
                '}';
    }
}
