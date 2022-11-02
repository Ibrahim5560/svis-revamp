package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.Questions} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.QuestionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descAr;

    private StringFilter descEn;

    private StringFilter code;

    private StringFilter imgPath;

    private LongFilter timeInSec;

    private IntegerFilter type;

    private DoubleFilter weight;

    private IntegerFilter status;

    private LongFilter categoriesId;

    private Boolean distinct;

    public QuestionsCriteria() {}

    public QuestionsCriteria(QuestionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descAr = other.descAr == null ? null : other.descAr.copy();
        this.descEn = other.descEn == null ? null : other.descEn.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.imgPath = other.imgPath == null ? null : other.imgPath.copy();
        this.timeInSec = other.timeInSec == null ? null : other.timeInSec.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.categoriesId = other.categoriesId == null ? null : other.categoriesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuestionsCriteria copy() {
        return new QuestionsCriteria(this);
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

    public StringFilter getDescAr() {
        return descAr;
    }

    public StringFilter descAr() {
        if (descAr == null) {
            descAr = new StringFilter();
        }
        return descAr;
    }

    public void setDescAr(StringFilter descAr) {
        this.descAr = descAr;
    }

    public StringFilter getDescEn() {
        return descEn;
    }

    public StringFilter descEn() {
        if (descEn == null) {
            descEn = new StringFilter();
        }
        return descEn;
    }

    public void setDescEn(StringFilter descEn) {
        this.descEn = descEn;
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

    public StringFilter getImgPath() {
        return imgPath;
    }

    public StringFilter imgPath() {
        if (imgPath == null) {
            imgPath = new StringFilter();
        }
        return imgPath;
    }

    public void setImgPath(StringFilter imgPath) {
        this.imgPath = imgPath;
    }

    public LongFilter getTimeInSec() {
        return timeInSec;
    }

    public LongFilter timeInSec() {
        if (timeInSec == null) {
            timeInSec = new LongFilter();
        }
        return timeInSec;
    }

    public void setTimeInSec(LongFilter timeInSec) {
        this.timeInSec = timeInSec;
    }

    public IntegerFilter getType() {
        return type;
    }

    public IntegerFilter type() {
        if (type == null) {
            type = new IntegerFilter();
        }
        return type;
    }

    public void setType(IntegerFilter type) {
        this.type = type;
    }

    public DoubleFilter getWeight() {
        return weight;
    }

    public DoubleFilter weight() {
        if (weight == null) {
            weight = new DoubleFilter();
        }
        return weight;
    }

    public void setWeight(DoubleFilter weight) {
        this.weight = weight;
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
        final QuestionsCriteria that = (QuestionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descAr, that.descAr) &&
            Objects.equals(descEn, that.descEn) &&
            Objects.equals(code, that.code) &&
            Objects.equals(imgPath, that.imgPath) &&
            Objects.equals(timeInSec, that.timeInSec) &&
            Objects.equals(type, that.type) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(status, that.status) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descAr, descEn, code, imgPath, timeInSec, type, weight, status, categoriesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descAr != null ? "descAr=" + descAr + ", " : "") +
            (descEn != null ? "descEn=" + descEn + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (imgPath != null ? "imgPath=" + imgPath + ", " : "") +
            (timeInSec != null ? "timeInSec=" + timeInSec + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (weight != null ? "weight=" + weight + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
