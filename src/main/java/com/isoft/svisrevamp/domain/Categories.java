package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Categories.
 */
@Entity
@Table(name = "categories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name_ar", nullable = false)
    private String nameAr;

    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "template", "categories" }, allowSetters = true)
    private Set<TemplateCategories> tempCategories = new HashSet<>();

    @OneToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categories" }, allowSetters = true)
    private Set<Questions> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Categories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return this.nameAr;
    }

    public Categories nameAr(String nameAr) {
        this.setNameAr(nameAr);
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Categories nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return this.code;
    }

    public Categories code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Categories status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<TemplateCategories> getTempCategories() {
        return this.tempCategories;
    }

    public void setTempCategories(Set<TemplateCategories> templateCategories) {
        if (this.tempCategories != null) {
            this.tempCategories.forEach(i -> i.setCategories(null));
        }
        if (templateCategories != null) {
            templateCategories.forEach(i -> i.setCategories(this));
        }
        this.tempCategories = templateCategories;
    }

    public Categories tempCategories(Set<TemplateCategories> templateCategories) {
        this.setTempCategories(templateCategories);
        return this;
    }

    public Categories addTempCategories(TemplateCategories templateCategories) {
        this.tempCategories.add(templateCategories);
        templateCategories.setCategories(this);
        return this;
    }

    public Categories removeTempCategories(TemplateCategories templateCategories) {
        this.tempCategories.remove(templateCategories);
        templateCategories.setCategories(null);
        return this;
    }

    public Set<Questions> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<Questions> questions) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setCategories(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setCategories(this));
        }
        this.questions = questions;
    }

    public Categories questions(Set<Questions> questions) {
        this.setQuestions(questions);
        return this;
    }

    public Categories addQuestions(Questions questions) {
        this.questions.add(questions);
        questions.setCategories(this);
        return this;
    }

    public Categories removeQuestions(Questions questions) {
        this.questions.remove(questions);
        questions.setCategories(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categories)) {
            return false;
        }
        return id != null && id.equals(((Categories) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categories{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
