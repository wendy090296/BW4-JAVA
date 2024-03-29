package entities;

import com.github.javafaker.Faker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.function.Supplier;

@Entity
public class VerifiedSupplier extends Sales {

    @Column(name = "verified_supplier_name")
    protected String supplierName;

    public VerifiedSupplier() {
    }

    public VerifiedSupplier(String supplierName) {
//        super(id);
        this.supplierName = supplierName;
    }

    public static Supplier<VerifiedSupplier> getVerifiedSupplierSupplier() {
        Faker faker = new Faker();

        return () -> {
            String supplierName = faker.harryPotter().character();

            return new VerifiedSupplier(supplierName);
        };

    }

    @Override
    public String toString() {
        return "VerifiedSupplier{" +
                ", supplierName='" + supplierName + '\'' +
                ", id=" + id +
                '}';
    }
}
