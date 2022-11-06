package com.isoft.svisrevamp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.svisrevamp.domain.Exam} entity. This class is used
 * in {@link com.isoft.svisrevamp.web.rest.ExamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter passScore;

    private DoubleFilter score;

    private LongFilter timeInSec;

    private InstantFilter validfrom;

    private InstantFilter validto;

    private InstantFilter startAt;

    private InstantFilter submitAt;

    private LongFilter userId;

    private LongFilter examinerId;

    private LongFilter examCode;

    private InstantFilter examDate;

    private LongFilter examResult;

    private BooleanFilter exported;

    private IntegerFilter status;

    private LongFilter examQuestionsId;

    private LongFilter templateId;

    private Boolean distinct;

    public ExamCriteria() {}

    public ExamCriteria(ExamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.passScore = other.passScore == null ? null : other.passScore.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.timeInSec = other.timeInSec == null ? null : other.timeInSec.copy();
        this.validfrom = other.validfrom == null ? null : other.validfrom.copy();
        this.validto = other.validto == null ? null : other.validto.copy();
        this.startAt = other.startAt == null ? null : other.startAt.copy();
        this.submitAt = other.submitAt == null ? null : other.submitAt.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.examinerId = other.examinerId == null ? null : other.examinerId.copy();
        this.examCode = other.examCode == null ? null : other.examCode.copy();
        this.examDate = other.examDate == null ? null : other.examDate.copy();
        this.examResult = other.examResult == null ? null : other.examResult.copy();
        this.exported = other.exported == null ? null : other.exported.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.examQuestionsId = other.examQuestionsId == null ? null : other.examQuestionsId.copy();
        this.templateId = other.templateId == null ? null : other.templateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ExamCriteria copy() {
        return new ExamCriteria(this);
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

    public DoubleFilter getScore() {
        return score;
    }

    public DoubleFilter score() {
        if (score == null) {
            score = new DoubleFilter();
        }
        return score;
    }

    public void setScore(DoubleFilter score) {
        this.score = score;
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

    public InstantFilter getValidfrom() {
        return validfrom;
    }

    public InstantFilter validfrom() {
        if (validfrom == null) {
            validfrom = new InstantFilter();
        }
        return validfrom;
    }

    public void setValidfrom(InstantFilter validfrom) {
        this.validfrom = validfrom;
    }

    public InstantFilter getValidto() {
        return validto;
    }

    public InstantFilter validto() {
        if (validto == null) {
            validto = new InstantFilter();
        }
        return validto;
    }

    public void setValidto(InstantFilter validto) {
        this.validto = validto;
    }

    public InstantFilter getStartAt() {
        return startAt;
    }

    public InstantFilter startAt() {
        if (startAt == null) {
            startAt = new InstantFilter();
        }
        return startAt;
    }

    public void setStartAt(InstantFilter startAt) {
        this.startAt = startAt;
    }

    public InstantFilter getSubmitAt() {
        return submitAt;
    }

    public InstantFilter submitAt() {
        if (submitAt == null) {
            submitAt = new InstantFilter();
        }
        return submitAt;
    }

    public void setSubmitAt(InstantFilter submitAt) {
        this.submitAt = submitAt;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getExaminerId() {
        return examinerId;
    }

    public LongFilter examinerId() {
        if (examinerId == null) {
            examinerId = new LongFilter();
        }
        return examinerId;
    }

    public void setExaminerId(LongFilter examinerId) {
        this.examinerId = examinerId;
    }

    public LongFilter getExamCode() {
        return examCode;
    }

    public LongFilter examCode() {
        if (examCode == null) {
            examCode = new LongFilter();
        }
        return examCode;
    }

    public void setExamCode(LongFilter examCode) {
        this.examCode = examCode;
    }

    public InstantFilter getExamDate() {
        return examDate;
    }

    public InstantFilter examDate() {
        if (examDate == null) {
            examDate = new InstantFilter();
        }
        return examDate;
    }

    public void setExamDate(InstantFilter examDate) {
        this.examDate = examDate;
    }

    public LongFilter getExamResult() {
        return examResult;
    }

    public LongFilter examResult() {
        if (examResult == null) {
            examResult = new LongFilter();
        }
        return examResult;
    }

    public void setExamResult(LongFilter examResult) {
        this.examResult = examResult;
    }

    public BooleanFilter getExported() {
        return exported;
    }

    public BooleanFilter exported() {
        if (exported == null) {
            exported = new BooleanFilter();
        }
        return exported;
    }

    public void setExported(BooleanFilter exported) {
        this.exported = exported;
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

    public LongFilter getExamQuestionsId() {
        return examQuestionsId;
    }

    public LongFilter examQuestionsId() {
        if (examQuestionsId == null) {
            examQuestionsId = new LongFilter();
        }
        return examQuestionsId;
    }

    public void setExamQuestionsId(LongFilter examQuestionsId) {
        this.examQuestionsId = examQuestionsId;
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
        final ExamCriteria that = (ExamCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(passScore, that.passScore) &&
            Objects.equals(score, that.score) &&
            Objects.equals(timeInSec, that.timeInSec) &&
            Objects.equals(validfrom, that.validfrom) &&
            Objects.equals(validto, that.validto) &&
            Objects.equals(startAt, that.startAt) &&
            Objects.equals(submitAt, that.submitAt) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(examinerId, that.examinerId) &&
            Objects.equals(examCode, that.examCode) &&
            Objects.equals(examDate, that.examDate) &&
            Objects.equals(examResult, that.examResult) &&
            Objects.equals(exported, that.exported) &&
            Objects.equals(status, that.status) &&
            Objects.equals(examQuestionsId, that.examQuestionsId) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            passScore,
            score,
            timeInSec,
            validfrom,
            validto,
            startAt,
            submitAt,
            userId,
            examinerId,
            examCode,
            examDate,
            examResult,
            exported,
            status,
            examQuestionsId,
            templateId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (passScore != null ? "passScore=" + passScore + ", " : "") +
            (score != null ? "score=" + score + ", " : "") +
            (timeInSec != null ? "timeInSec=" + timeInSec + ", " : "") +
            (validfrom != null ? "validfrom=" + validfrom + ", " : "") +
            (validto != null ? "validto=" + validto + ", " : "") +
            (startAt != null ? "startAt=" + startAt + ", " : "") +
            (submitAt != null ? "submitAt=" + submitAt + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (examinerId != null ? "examinerId=" + examinerId + ", " : "") +
            (examCode != null ? "examCode=" + examCode + ", " : "") +
            (examDate != null ? "examDate=" + examDate + ", " : "") +
            (examResult != null ? "examResult=" + examResult + ", " : "") +
            (exported != null ? "exported=" + exported + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (examQuestionsId != null ? "examQuestionsId=" + examQuestionsId + ", " : "") +
            (templateId != null ? "templateId=" + templateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
