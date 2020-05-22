package de.sharetrip.hobby.domain;

import de.sharetrip.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "hobby")
@EqualsAndHashCode(callSuper = true)
public class Hobby extends BaseDomain {

    @Column(name = "hobby",
            unique = true)
    private String hobby;
}
