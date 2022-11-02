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
 * A Template.
 */
@Entity
@Table(name = "template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Template implements Serializable {

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

    @Column(name = "time_in_sec")
    private Long timeInSec;

    @Column(name = "pass_score")
    private Double passScore;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "template")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "template", "categories" }, allowSetters = true)
    private Set<TemplateCategories> templateCategories = new HashSet<>();

    @OneToMany(mappedBy = "template")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "template" }, allowSetters = true)
    private Set<TemplateFacilitators> templateFacilitators = new HashSet<>();

    @OneToMany(mappedBy = "template")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "examQuestions", "template" }, allowSetters = true)
    private Set<Exam> exams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Template id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return this.nameAr;
    }

    public Template nameAr(String nameAr) {
        this.setNameAr(nameAr);
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Template nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return this.code;
    }

    public Template code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTimeInSec() {
        return this.timeInSec;
    }

    public Template timeInSec(Long timeInSec) {
        this.setTimeInSec(timeInSec);
        return this;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Double getPassScore() {
        return this.passScore;
    }

    public Template passScore(Double passScore) {
        this.setPassScore(passScore);
        return this;
    }

    public void setPassScore(Double passScore) {
        this.passScore = passScore;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Template status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<TemplateCategories> getTemplateCategories() {
        return this.templateCategories;
    }

    public void setTemplateCategories(Set<TemplateCategories> templateCategories) {
        if (this.templateCategories != null) {
            this.templateCategories.forEach(i -> i.setTemplate(null));
        }
        if (templateCategories != null) {
            templateCategories.forEach(i -> i.setTemplate(this));
        }
        this.templateCategories = templateCategories;
    }

    public Template templateCategories(Set<TemplateCategories> templateCategories) {
        this.setTemplateCategories(templateCategories);
        return this;
    }

    public Template addTemplateCategories(TemplateCategories templateCategories) {
        this.templateCategories.add(templateCategories);
        templateCategories.setTemplate(this);
        return this;
    }

    public Template removeTemplateCategories(TemplateCategories templateCategories) {
        this.templateCategories.remove(templateCategories);
        templateCategories.setTemplate(null);
        return this;
    }

    public Set<TemplateFacilitators> getTemplateFacilitators() {
        return this.templateFacilitators;
    }

    public void setTemplateFacilitators(Set<TemplateFacilitators> templateFacilitators) {
        if (this.templateFacilitators != null) {
            this.templateFacilitators.forEach(i -> i.setTemplate(null));
        }
        if (templateFacilitators != null) {
            templateFacilitators.forEach(i -> i.setTemplate(this));
        }
        this.templateFacilitators = templateFacilitators;
    }

    public Template templateFacilitators(Set<TemplateFacilitators> templateFacilitators) {
        this.setTemplateFacilitators(templateFacilitators);
        return this;
    }

    public Template addTemplateFacilitators(TemplateFacilitators templateFacilitators) {
        this.templateFacilitators.add(templateFacilitators);
        templateFacilitators.setTemplate(this);
        return this;
    }

    public Template removeTemplateFacilitators(TemplateFacilitators templateFacilitators) {
        this.templateFacilitators.remove(templateFacilitators);
        templateFacilitators.setTemplate(null);
        return this;
    }

    public Set<Exam> getExams() {
        return this.exams;
    }

    public void setExams(Set<Exam> exams) {
        if (this.exams != null) {
            this.exams.forEach(i -> i.setTemplate(null));
        }
        if (exams != null) {
            exams.forEach(i -> i.setTemplate(this));
        }
        this.exams = exams;
    }

    public Template exams(Set<Exam> exams) {
        this.setExams(exams);
        return this;
    }

    public Template addExam(Exam exam) {
        this.exams.add(exam);
        exam.setTemplate(this);
        return this;
    }

    public Template removeExam(Exam exam) {
        this.exams.remove(exam);
        exam.setTemplate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Template)) {
            return false;
        }
        return id != null && id.equals(((Template) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", timeInSec=" + getTimeInSec() +
            ", passScore=" + getPassScore() +
            ", status=" + getStatus() +
            "}";
    }
}
