package de.sharetrip.message.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "message")
public class Message extends BaseDomain {

    @Column(name = "text",
            nullable = false)
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user")
    private User sender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_user")
    private User receiver;

    @Column(name = "message_read",
            length = 1,
            nullable = false)
    private boolean messageRead;
}
