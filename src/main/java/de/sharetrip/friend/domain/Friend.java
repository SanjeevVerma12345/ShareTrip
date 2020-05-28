package de.sharetrip.friend.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "friend")
@EqualsAndHashCode(callSuper = true)
public class Friend extends BaseDomain {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_user_id")
    private User requester;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User buddy;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_status",
            length = 15,
            nullable = false)
    private FriendStatus status;

    @Column(name = "is_friend",
            nullable = false)
    private boolean isFriend;

}
