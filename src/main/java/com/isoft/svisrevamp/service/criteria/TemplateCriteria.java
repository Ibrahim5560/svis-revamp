package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.Template} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.TemplateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameAr;

    private StringFilter nameEn;

    private StringFilter code;

    private LongFilter timeInSec;

    private DoubleFilter passScore;

    private IntegerFilter status;

    private LongFilter templateCategoriesId;

    private LongFilter templateFacilitatorsId;

    private LongFilter examId;

    private Boolean distinct;

    public TemplateCriteria() {}

    public TemplateCriteria(TemplateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.timeInSec = other.timeInSec == null ? null : other.timeInSec.copy();
        this.passScore = other.passScore == null ? null : other.passScore.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.templateCategoriesId = other.templateCategoriesId == null ? null : other.templateCategoriesId.copy();
        this.templateFacilitatorsId = other.templateFacilitatorsId == null ? null : other.templateFacilitatorsId.copy();
        this.examId = other.examId == null ? null : other.examId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplateCriteria copy() {
        return new TemplateCriteria(this);
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

    public DoubleFilter getPassScore() {
        return passScore;
    }

    public DoubleFilter passScore() {
        if (passScore == null) {
            passScore = new DoubleFilter();
        }
        return passScore;
    }

    public void setPassScore(DoubleFilter passScore) {
        this.passScore = passScore;
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

    public LongFilter getTemplateCategoriesId() {
        return templateCategoriesId;
    }

    public LongFilter templateCategoriesId() {
        if (templateCategoriesId == null) {
            templateCategoriesId = new LongFilter();
        }
        return templateCategoriesId;
    }

    public void setTemplateCategoriesId(LongFilter templateCategoriesId) {
        this.templateCategoriesId = templateCategoriesId;
    }

    public LongFilter getTemplateFacilitatorsId() {
        return templateFacilitatorsId;
    }

    public LongFilter templateFacilitatorsId() {
        if (templateFacilitatorsId == null) {
            templateFacilitatorsId = new LongFilter();
        }
        return templateFacilitatorsId;
    }

    public void setTemplateFacilitatorsId(LongFilter templateFacilitatorsId) {
        this.templateFacilitatorsId = templateFacilitatorsId;
    }

    public LongFilter getExamId() {
        return examId;
    }

    public LongFilter examId() {
        if (examId == null) {
            examId = new LongFilter();
        }
        return examId;
    }

    public void setExamId(LongFilter examId) {
        this.examId = examId;
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
        final TemplateCriteria that = (TemplateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(code, that.code) &&
            Objects.equals(timeInSec, that.timeInSec) &&
            Objects.equals(passScore, that.passScore) &&
            Objects.equals(status, that.status) &&
            Objects.equals(templateCategoriesId, that.templateCategoriesId) &&
            Objects.equals(templateFacilitatorsId, that.templateFacilitatorsId) &&
            Objects.equals(examId, that.examId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nameAr,
            nameEn,
            code,
            timeInSec,
            passScore,
            status,
            templateCategoriesId,
            templateFacilitatorsId,
            examId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
            (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (timeInSec != null ? "timeInSec=" + timeInSec + ", " : "") +
            (passScore != null ? "passScore=" + passScore + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (templateCategoriesId != null ? "templateCategoriesId=" + templateCategoriesId + ", " : "") +
            (templateFacilitatorsId != null ? "templateFacilitatorsId=" + templateFacilitatorsId + ", " : "") +
            (examId != null ? "examId=" + examId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
