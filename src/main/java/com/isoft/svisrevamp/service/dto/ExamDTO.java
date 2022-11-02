package com.isoft.svisrevamp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.svisrevamp.domain.Exam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamDTO implements Serializable {

    private Long id;

    private Double passScore;

    private Double score;

    private Long timeInSec;

    @NotNull
    private LocalDate validfrom;

    @NotNull
    private LocalDate validto;

    private Instant startAt;

    private Instant submitAt;

    private Long userId;

    private Long examinerId;

    private Long examCode;

    private LocalDate examDate;

    private Long examResult;

    private Boolean exported;

    @NotNull
    private Integer status;

    private TemplateDTO template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPassScore() {
        return passScore;
    }

    public void setPassScore(Double passScore) {
        this.passScore = passScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public LocalDate getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(LocalDate validfrom) {
        this.validfrom = validfrom;
    }

    public LocalDate getValidto() {
        return validto;
    }

    public void setValidto(LocalDate validto) {
        this.validto = validto;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(Long examinerId) {
        this.examinerId = examinerId;
    }

    public Long getExamCode() {
        return examCode;
    }

    public void setExamCode(Long examCode) {
        this.examCode = examCode;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public Long getExamResult() {
        return examResult;
    }

    public void setExamResult(Long examResult) {
        this.examResult = examResult;
    }

    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamDTO)) {
            return false;
        }

        ExamDTO examDTO = (ExamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", passScore=" + getPassScore() +
            ", score=" + getScore() +
            ", timeInSec=" + getTimeInSec() +
            ", validfrom='" + getValidfrom() + "'" +
            ", validto='" + getValidto() + "'" +
            ", startAt='" + getStartAt() + "'" +
            ", submitAt='" + getSubmitAt() + "'" +
            ", userId=" + getUserId() +
            ", examinerId=" + getExaminerId() +
            ", examCode=" + getExamCode() +
            ", examDate='" + getExamDate() + "'" +
            ", examResult=" + getExamResult() +
            ", exported='" + getExported() + "'" +
            ", status=" + getStatus() +
            ", template=" + getTemplate() +
            "}";
    }
}
