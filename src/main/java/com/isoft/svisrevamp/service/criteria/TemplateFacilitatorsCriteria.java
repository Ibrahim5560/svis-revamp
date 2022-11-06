package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.TemplateFacilitators} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.TemplateFacilitatorsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /template-facilitators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateFacilitatorsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter count;

    private LongFilter centerId;

    private IntegerFilter facilitatorType;

    private LongFilter templateId;

    private Boolean distinct;

    public TemplateFacilitatorsCriteria() {}

    public TemplateFacilitatorsCriteria(TemplateFacilitatorsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
        this.facilitatorType = other.facilitatorType == null ? null : other.facilitatorType.copy();
        this.templateId = other.templateId == null ? null : other.templateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplateFacilitatorsCriteria copy() {
        return new TemplateFacilitatorsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCount() {
        return count;
    }

    public IntegerFilter count() {
        if (count == null) {
            count = new IntegerFilter();
        }
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public LongFilter centerId() {
        if (centerId == null) {
            centerId = new LongFilter();
        }
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
        this.centerId = centerId;
    }

    public IntegerFilter getFacilitatorType() {
        return facilitatorType;
    }

    public IntegerFilter facilitatorType() {
        if (facilitatorType == null) {
            facilitatorType = new IntegerFilter();
        }
        return facilitatorType;
    }

    public void setFacilitatorType(IntegerFilter facilitatorType) {
        this.facilitatorType = facilitatorType;
    }

    public LongFilter getTemplateId() {
        return templateId;
    }

    public LongFilter templateId() {
        if (templateId == null) {
            templateId = new LongFilter();
        }
        return templateId;
    }

    public void setTemplateId(LongFilter templateId) {
        this.templateId = templateId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TemplateFacilitatorsCriteria that = (TemplateFacilitatorsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(count, that.count) &&
            Objects.equals(centerId, that.centerId) &&
            Objects.equals(facilitatorType, that.facilitatorType) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, centerId, facilitatorType, templateId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFacilitatorsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (count != null ? "count=" + count + ", " : "") +
            (centerId != null ? "centerId=" + centerId + ", " : "") +
            (facilitatorType != null ? "facilitatorType=" + facilitatorType + ", " : "") +
            (templateId != null ? "templateId=" + templateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
