package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "pass_score")
    private Double passScore;

    @Column(name = "score")
    private Double score;

    @Column(name = "time_in_sec")
    private Long timeInSec;

    @NotNull
    @Column(name = "validfrom", nullable = false)
    private Instant validfrom;

    @NotNull
    @Column(name = "validto", nullable = false)
    private Instant validto;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "submit_at")
    private Instant submitAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "examiner_id")
    private Long examinerId;

    @Column(name = "exam_code")
    private Long examCode;

    @Column(name = "exam_date")
    private Instant examDate;

    @Column(name = "exam_result")
    private Long examResult;

    @Column(name = "exported")
    private Boolean exported;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "exam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exam" }, allowSetters = true)
    private Set<ExamQuestions> examQuestions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "templateCategories", "templateFacilitators", "exams" }, allowSetters = true)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Exam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPassScore() {
        return this.passScore;
    }

    public Exam passScore(Double passScore) {
        this.setPassScore(passScore);
        return this;
    }

    public void setPassScore(Double passScore) {
        this.passScore = passScore;
    }

    public Double getScore() {
        return this.score;
    }

    public Exam score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getTimeInSec() {
        return this.timeInSec;
    }

    public Exam timeInSec(Long timeInSec) {
        this.setTimeInSec(timeInSec);
        return this;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Instant getValidfrom() {
        return this.validfrom;
    }

    public Exam validfrom(Instant validfrom) {
        this.setValidfrom(validfrom);
        return this;
    }

    public void setValidfrom(Instant validfrom) {
        this.validfrom = validfrom;
    }

    public Instant getValidto() {
        return this.validto;
    }

    public Exam validto(Instant validto) {
        this.setValidto(validto);
        return this;
    }

    public void setValidto(Instant validto) {
        this.validto = validto;
    }

    public Instant getStartAt() {
        return this.startAt;
    }

    public Exam startAt(Instant startAt) {
        this.setStartAt(startAt);
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getSubmitAt() {
        return this.submitAt;
    }

    public Exam submitAt(Instant submitAt) {
        this.setSubmitAt(submitAt);
        return this;
    }

    public void setSubmitAt(Instant submitAt) {
        this.submitAt = submitAt;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Exam userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExaminerId() {
        return this.examinerId;
    }

    public Exam examinerId(Long examinerId) {
        this.setExaminerId(examinerId);
        return this;
    }

    public void setExaminerId(Long examinerId) {
        this.examinerId = examinerId;
    }

    public Long getExamCode() {
        return this.examCode;
    }

    public Exam examCode(Long examCode) {
        this.setExamCode(examCode);
        return this;
    }

    public void setExamCode(Long examCode) {
        this.examCode = examCode;
    }

    public Instant getExamDate() {
        return this.examDate;
    }

    public Exam examDate(Instant examDate) {
        this.setExamDate(examDate);
        return this;
    }

    public void setExamDate(Instant examDate) {
        this.examDate = examDate;
    }

    public Long getExamResult() {
        return this.examResult;
    }

    public Exam examResult(Long examResult) {
        this.setExamResult(examResult);
        return this;
    }

    public void setExamResult(Long examResult) {
        this.examResult = examResult;
    }

    public Boolean getExported() {
        return this.exported;
    }

    public Exam exported(Boolean exported) {
        this.setExported(exported);
        return this;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Exam status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<ExamQuestions> getExamQuestions() {
        return this.examQuestions;
    }

    public void setExamQuestions(Set<ExamQuestions> examQuestions) {
        if (this.examQuestions != null) {
            this.examQuestions.forEach(i -> i.setExam(null));
        }
        if (examQuestions != null) {
            examQuestions.forEach(i -> i.setExam(this));
        }
        this.examQuestions = examQuestions;
    }

    public Exam examQuestions(Set<ExamQuestions> examQuestions) {
        this.setExamQuestions(examQuestions);
        return this;
    }

    public Exam addExamQuestions(ExamQuestions examQuestions) {
        this.examQuestions.add(examQuestions);
        examQuestions.setExam(this);
        return this;
    }

    public Exam removeExamQuestions(ExamQuestions examQuestions) {
        this.examQuestions.remove(examQuestions);
        examQuestions.setExam(null);
        return this;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Exam template(Template template) {
        this.setTemplate(template);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exam)) {
            return false;
        }
        return id != null && id.equals(((Exam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exam{" +
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
            "}";
    }
}
