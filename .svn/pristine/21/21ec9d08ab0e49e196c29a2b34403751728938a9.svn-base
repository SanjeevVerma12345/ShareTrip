package de.sharetrip.country.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.core.domain.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "country")
public class Country extends BaseDomain {

    @Column(name = "name")
    private String name;

    @Column(name = "alpha_code_2")
    private String alphaCode2;

    @Column(name = "numeric_code")
    private Integer numericCode;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "image")
    private Image image;
}
