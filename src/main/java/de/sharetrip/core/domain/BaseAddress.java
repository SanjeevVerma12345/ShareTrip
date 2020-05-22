package de.sharetrip.core.domain;

import de.sharetrip.country.domain.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode(callSuper = true)
@Table(name = "address")
public class BaseAddress extends BaseDomain {

    @Column(name = "address_name_from")
    private String fromAddressName;

    @Column(name = "latitude_from")
    private BigDecimal fromLatitude;

    @Column(name = "longitude_from")
    private BigDecimal fromLongitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_from")
    private Country fromCountry;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_to")
    private Country toCountry;

    @Column(name = "address_name_to")
    private String toAddressName;

    @Column(name = "latitude_to")
    private BigDecimal toLatitude;

    @Column(name = "longitude_to")
    private BigDecimal toLongitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10,
            insertable = false,
            updatable = false)
    private AddressType type;

}
