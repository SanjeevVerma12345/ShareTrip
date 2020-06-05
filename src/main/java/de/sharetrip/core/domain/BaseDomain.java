package de.sharetrip.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Slf4j
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseDomain extends AbstractPersistable<Long> implements Serializable {

    @Column(name = "uuid",
            updatable = false,
            unique = true)
    private UUID uuid;

    @Column(name = "created_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @PrePersist
    protected void onCreate() {
        final Date date = new Date();
        createdDate = date;
        modifiedDate = date;
        uuid = UUID.randomUUID();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = new Date();
    }

}
