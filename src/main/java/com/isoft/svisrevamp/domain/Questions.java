package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Questions.
 */
@Entity
@Table(name = "questions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "desc_ar", nullable = false)
    private String descAr;

    @NotNull
    @Column(name = "desc_en", nullable = false)
    private String descEn;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "time_in_sec")
    private Long timeInSec;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tempCategories", "questions" }, allowSetters = true)
    private Categories categories;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Questions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescAr() {
        return this.descAr;
    }

    public Questions descAr(String descAr) {
        this.setDescAr(descAr);
        return this;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public String getDescEn() {
        return this.descEn;
    }

    public Questions descEn(String descEn) {
        this.setDescEn(descEn);
        return this;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getCode() {
        return this.code;
    }

    public Questions code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public Questions imgPath(String imgPath) {
        this.setImgPath(imgPath);
        return this;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getTimeInSec() {
        return this.timeInSec;
    }

    public Questions timeInSec(Long timeInSec) {
        this.setTimeInSec(timeInSec);
        return this;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Integer getType() {
        return this.type;
    }

    public Questions type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Questions weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Questions status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Questions categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questions)) {
            return false;
        }
        return id != null && id.equals(((Questions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", descAr='" + getDescAr() + "'" +
            ", descEn='" + getDescEn() + "'" +
            ", code='" + getCode() + "'" +
            ", imgPath='" + getImgPath() + "'" +
            ", timeInSec=" + getTimeInSec() +
            ", type=" + getType() +
            ", weight=" + getWeight() +
            ", status=" + getStatus() +
            "}";
    }
}
