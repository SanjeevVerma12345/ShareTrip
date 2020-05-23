package de.sharetrip.user.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.core.domain.Image;
import de.sharetrip.country.domain.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_details")
@EqualsAndHashCode(callSuper = true)
public class UserDetails extends BaseDomain {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age",
            length = 2)
    private Integer age;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image")
    private Image userImage;

    @Column(name = "about_me",
            length = 500)
    private String aboutMe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private Country country;

}
