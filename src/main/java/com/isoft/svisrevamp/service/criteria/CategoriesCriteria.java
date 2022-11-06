package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.Categories} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.CategoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameAr;

    private StringFilter nameEn;

    private StringFilter code;

    private IntegerFilter status;

    private LongFilter tempCategoriesId;

    private LongFilter questionsId;

    private Boolean distinct;

    public CategoriesCriteria() {}

    public CategoriesCriteria(CategoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.tempCategoriesId = other.tempCategoriesId == null ? null : other.tempCategoriesId.copy();
        this.questionsId = other.questionsId == null ? null : other.questionsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoriesCriteria copy() {
        return new CategoriesCriteria(this);
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

    public StringFilter getNameAr() {
        return nameAr;
    }

    public StringFilter nameAr() {
        if (nameAr == null) {
            nameAr = new StringFilter();
        }
        return nameAr;
    }

    public void setNameAr(StringFilter nameAr) {
        this.nameAr = nameAr;
    }

    public StringFilter getNameEn() {
        return nameEn;
    }

    public StringFilter nameEn() {
        if (nameEn == null) {
            nameEn = new StringFilter();
        }
        return nameEn;
    }

    public void setNameEn(StringFilter nameEn) {
        this.nameEn = nameEn;
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

    public LongFilter getTempCategoriesId() {
        return tempCategoriesId;
    }

    public LongFilter tempCategoriesId() {
        if (tempCategoriesId == null) {
            tempCategoriesId = new LongFilter();
        }
        return tempCategoriesId;
    }

    public void setTempCategoriesId(LongFilter tempCategoriesId) {
        this.tempCategoriesId = tempCategoriesId;
    }

    public LongFilter getQuestionsId() {
        return questionsId;
    }

    public LongFilter questionsId() {
        if (questionsId == null) {
            questionsId = new LongFilter();
        }
        return questionsId;
    }

    public void setQuestionsId(LongFilter questionsId) {
        this.questionsId = questionsId;
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
        final CategoriesCriteria that = (CategoriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(code, that.code) &&
            Objects.equals(status, that.status) &&
            Objects.equals(tempCategoriesId, that.tempCategoriesId) &&
            Objects.equals(questionsId, that.questionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameAr, nameEn, code, status, tempCategoriesId, questionsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
            (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (tempCategoriesId != null ? "tempCategoriesId=" + tempCategoriesId + ", " : "") +
            (questionsId != null ? "questionsId=" + questionsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
