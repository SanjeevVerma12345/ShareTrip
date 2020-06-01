package de.sharetrip.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "image")
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseDomain {

    @Column(name = "url",
            nullable = false)
    private String url;

    @Builder
    public Image(final String url) {
        this.url = url;
    }
}
