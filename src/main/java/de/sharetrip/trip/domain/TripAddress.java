package de.sharetrip.trip.domain;

import de.sharetrip.core.domain.AddressType;
import de.sharetrip.core.domain.BaseAddress;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.util.Objects;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("TRIP")
public class TripAddress extends BaseAddress {

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "address")
    private Trip trip;

    @Override
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (Objects.isNull(super.getType())) {
            super.setType(AddressType.TRIP);
        }
    }
}
