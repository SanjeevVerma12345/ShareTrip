package de.sharetrip.experience.domain;

import de.sharetrip.core.domain.AddressType;
import de.sharetrip.core.domain.BaseAddress;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.util.Objects;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("EXPERIENCE")
public class ExperienceAddress extends BaseAddress {

    @OneToOne(mappedBy = "experienceAddress",
            fetch = FetchType.LAZY)
    private Experience experience;

    @Override
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (Objects.isNull(super.getType())) {
            super.setType(AddressType.EXPERIENCE);
        }
    }

}
