package de.sharetrip.experience.domain;

import de.sharetrip.core.domain.BaseDomain;
import de.sharetrip.core.domain.Image;
import de.sharetrip.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "experience")
@EqualsAndHashCode(callSuper = true)
public class Experience extends BaseDomain {

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Lob
    @Column(name = "details",
            columnDefinition = "LONGTEXT")
    private String details;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ExperienceAddress experienceAddress;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "experience_images",
            joinColumns = {@JoinColumn(name = "experience_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private List<Image> imageList;
}
