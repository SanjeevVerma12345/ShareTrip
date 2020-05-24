package de.sharetrip.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseDomain extends AbstractPersistable<Long> {

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
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.uuid = UUID.randomUUID();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = new Date();
    }

}
