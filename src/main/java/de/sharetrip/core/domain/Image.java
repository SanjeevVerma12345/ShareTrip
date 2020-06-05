package de.sharetrip.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseDomain {

    @Column(name = "url",
            nullable = false)
    private String url;
}
