package de.sharetrip.notification.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseDomain {

    @Column(name = "title",
            nullable = false)
    private String title;

    @Column(name = "body",
            nullable = false,
            columnDefinition = "TEXT")
    private String body;

    @Column(name = "sent",
            nullable = false,
            insertable = false)
    private boolean isSent;

    @Column(name = "failed",
            nullable = false,
            insertable = false)
    private boolean failed;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20,
            updatable = false)
    private NotificationType notificationType;

    @ToString.Exclude
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
