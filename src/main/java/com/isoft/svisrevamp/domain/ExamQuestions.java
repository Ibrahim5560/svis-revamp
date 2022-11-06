package com.isoft.svisrevamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExamQuestions.
 */
@Entity
@Table(name = "exam_questions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamQuestions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "desc_ar", nullable = false)
    private String descAr;

    @NotNull
    @Column(name = "desc_en", nullable = false)
    private String descEn;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "time_in_sec")
    private Long timeInSec;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "score")
    private Double score;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "submit_at")
    private Instant submitAt;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "examQuestions", "template" }, allowSetters = true)
    private Exam exam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExamQuestions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescAr() {
        return this.descAr;
    }

    public ExamQuestions descAr(String descAr) {
        this.setDescAr(descAr);
        return this;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public String getDescEn() {
        return this.descEn;
    }

    public ExamQuestions descEn(String descEn) {
        this.setDescEn(descEn);
        return this;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getCode() {
        return this.code;
    }

    public ExamQuestions code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public ExamQuestions imgPath(String imgPath) {
        this.setImgPath(imgPath);
        return this;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getTimeInSec() {
        return this.timeInSec;
    }

    public ExamQuestions timeInSec(Long timeInSec) {
        this.setTimeInSec(timeInSec);
        return this;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Integer getType() {
        return this.type;
    }

    public ExamQuestions type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getWeight() {
        return this.weight;
    }

    public ExamQuestions weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return this.score;
    }

    public ExamQuestions score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Instant getStartAt() {
        return this.startAt;
    }

    public ExamQuestions startAt(Instant startAt) {
        this.setStartAt(startAt);
        return this;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getSubmitAt() {
        return this.submitAt;
    }

    public ExamQuestions submitAt(Instant submitAt) {
        this.setSubmitAt(submitAt);
        return this;
    }

    public void setSubmitAt(Instant submitAt) {
        this.submitAt = submitAt;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public ExamQuestions categoryId(Integer categoryId) {
        this.setCategoryId(categoryId);
        return this;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public ExamQuestions questionId(Integer questionId) {
        this.setQuestionId(questionId);
        return this;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public ExamQuestions seq(Integer seq) {
        this.setSeq(seq);
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return this.status;
    }

    public ExamQuestions status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ExamQuestions exam(Exam exam) {
        this.setExam(exam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamQuestions)) {
            return false;
        }
        return id != null && id.equals(((ExamQuestions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamQuestions{" +
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
            "}";
    }
}
