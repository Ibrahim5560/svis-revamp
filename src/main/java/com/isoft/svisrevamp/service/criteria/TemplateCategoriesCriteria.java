package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.TemplateCategories} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.TemplateCategoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /template-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateCategoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private IntegerFilter noOfQuestions;

    private IntegerFilter seq;

    private IntegerFilter status;

    private LongFilter templateId;

    private LongFilter categoriesId;

    private Boolean distinct;

    public TemplateCategoriesCriteria() {}

    public TemplateCategoriesCriteria(TemplateCategoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.noOfQuestions = other.noOfQuestions == null ? null : other.noOfQuestions.copy();
        this.seq = other.seq == null ? null : other.seq.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.templateId = other.templateId == null ? null : other.templateId.copy();
        this.categoriesId = other.categoriesId == null ? null : other.categoriesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplateCategoriesCriteria copy() {
        return new TemplateCategoriesCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getNoOfQuestions() {
        return noOfQuestions;
    }

    public IntegerFilter noOfQuestions() {
        if (noOfQuestions == null) {
            noOfQuestions = new IntegerFilter();
        }
        return noOfQuestions;
    }

    public void setNoOfQuestions(IntegerFilter noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public IntegerFilter getSeq() {
        return seq;
    }

    public IntegerFilter seq() {
        if (seq == null) {
            seq = new IntegerFilter();
        }
        return seq;
    }

    public void setSeq(IntegerFilter seq) {
        this.seq = seq;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public IntegerFilter status() {
        if (status == null) {
            status = new IntegerFilter();
        }
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
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

    public LongFilter getCategoriesId() {
        return categoriesId;
    }

    public LongFilter categoriesId() {
        if (categoriesId == null) {
            categoriesId = new LongFilter();
        }
        return categoriesId;
    }

    public void setCategoriesId(LongFilter categoriesId) {
        this.categoriesId = categoriesId;
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
        final TemplateCategoriesCriteria that = (TemplateCategoriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(noOfQuestions, that.noOfQuestions) &&
            Objects.equals(seq, that.seq) &&
            Objects.equals(status, that.status) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, noOfQuestions, seq, status, templateId, categoriesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCategoriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (noOfQuestions != null ? "noOfQuestions=" + noOfQuestions + ", " : "") +
            (seq != null ? "seq=" + seq + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (templateId != null ? "templateId=" + templateId + ", " : "") +
            (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
