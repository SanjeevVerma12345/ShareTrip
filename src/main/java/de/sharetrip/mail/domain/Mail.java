package de.sharetrip.mail.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
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
@Table(name = "mail")
@EqualsAndHashCode(callSuper = true)
public class Mail extends BaseDomain {

    @Column(name = "message",
            nullable = false,
            updatable = false,
            columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent",
            nullable = false,
            insertable = false)
    private boolean isSent;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_type",
            nullable = false,
            length = 20)
    private MailType mailType;

    @ToString.Exclude
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private User user;
}
