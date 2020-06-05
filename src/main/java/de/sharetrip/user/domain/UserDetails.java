package de.sharetrip.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.core.domain.Country;
import de.sharetrip.core.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @MapsId
    @ToString.Exclude
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private User user;

    @Column(length = 6)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JoinColumn(name = "user_image")
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Image userImage;

    @Column(name = "about_me",
            length = 500)
    private String aboutMe;

    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;


}
