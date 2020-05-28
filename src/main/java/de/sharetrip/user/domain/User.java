package de.sharetrip.user.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.friend.domain.Friend;
import de.sharetrip.hobby.domain.Hobby;
import de.sharetrip.trip.domain.Trip;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDomain {

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_provider", length = 10)
    private AuthenticationProvider authenticationProvider;

    @JsonBackReference
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private UserDetails userDetails;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "user_trips",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "trip_id")})
    private List<Trip> trips;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_user_id")
    private List<Friend> friendList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_hobbies",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "hobby_id")})
    private List<Hobby> hobbies;

}

