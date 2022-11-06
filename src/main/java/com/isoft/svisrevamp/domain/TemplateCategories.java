package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TemplateCategories.
 */
@Entity
@Table(name = "template_categories")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "no_of_questions", nullable = false)
    private Integer noOfQuestions;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "templateCategories", "templateFacilitators", "exams" }, allowSetters = true)
    private Template template;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tempCategories", "questions" }, allowSetters = true)
    private Categories categories;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TemplateCategories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TemplateCategories code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNoOfQuestions() {
        return this.noOfQuestions;
    }

    public TemplateCategories noOfQuestions(Integer noOfQuestions) {
        this.setNoOfQuestions(noOfQuestions);
        return this;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public TemplateCategories seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return this.status;
    }

    public TemplateCategories status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public TemplateCategories template(Template template) {
        this.setTemplate(template);
        return this;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public TemplateCategories categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateCategories)) {
            return false;
        }
        return id != null && id.equals(((TemplateCategories) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCategories{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", noOfQuestions=" + getNoOfQuestions() +
            ", seq=" + getSeq() +
            ", status=" + getStatus() +
            "}";
    }
}
