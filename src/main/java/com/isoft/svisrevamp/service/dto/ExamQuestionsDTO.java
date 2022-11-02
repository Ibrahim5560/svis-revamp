package com.isoft.svisrevamp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.svisrevamp.domain.ExamQuestions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamQuestionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String descAr;

    @NotNull
    private String descEn;

    @NotNull
    private String code;

    private String imgPath;

    private Long timeInSec;

    @NotNull
    private Integer type;

    @NotNull
    private Double weight;

    private Double score;

    private Instant startAt;

    private Instant submitAt;

    private Integer categoryId;

    private Integer questionId;

    private Integer seq;

    private Integer status;

    private ExamDTO exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescAr() {
        return descAr;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(Instant submitAt) {
        this.submitAt = submitAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamQuestionsDTO)) {
            return false;
        }

        ExamQuestionsDTO examQuestionsDTO = (ExamQuestionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examQuestionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamQuestionsDTO{" +
            "id=" + getId() +
            ", descAr='" + getDescAr() + "'" +
            ", descEn='" + getDescEn() + "'" +
            ", code='" + getCode() + "'" +
            ", imgPath='" + getImgPath() + "'" +
            ", timeInSec=" + getTimeInSec() +
            ", type=" + getType() +
            ", weight=" + getWeight() +
            ", score=" + getScore() +
            ", startAt='" + getStartAt() + "'" +
            ", submitAt='" + getSubmitAt() + "'" +
            ", categoryId=" + getCategoryId() +
            ", questionId=" + getQuestionId() +
            ", seq=" + getSeq() +
            ", status=" + getStatus() +
            ", exam=" + getExam() +
            "}";
    }
}
