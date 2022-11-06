package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TemplateFacilitators.
 */
@Entity
@Table(name = "template_facilitators")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateFacilitators implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "count", nullable = false)
    private Integer count;

    @NotNull
    @Column(name = "center_id", nullable = false)
    private Long centerId;

    @NotNull
    @Column(name = "facilitator_type", nullable = false)
    private Integer facilitatorType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "templateCategories", "templateFacilitators", "exams" }, allowSetters = true)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TemplateFacilitators id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return this.count;
    }

    public TemplateFacilitators count(Integer count) {
        this.setCount(count);
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getCenterId() {
        return this.centerId;
    }

    public TemplateFacilitators centerId(Long centerId) {
        this.setCenterId(centerId);
        return this;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Integer getFacilitatorType() {
        return this.facilitatorType;
    }

    public TemplateFacilitators facilitatorType(Integer facilitatorType) {
        this.setFacilitatorType(facilitatorType);
        return this;
    }

    public void setFacilitatorType(Integer facilitatorType) {
        this.facilitatorType = facilitatorType;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public TemplateFacilitators template(Template template) {
        this.setTemplate(template);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateFacilitators)) {
            return false;
        }
        return id != null && id.equals(((TemplateFacilitators) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFacilitators{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", centerId=" + getCenterId() +
            ", facilitatorType=" + getFacilitatorType() +
            "}";
    }
}
