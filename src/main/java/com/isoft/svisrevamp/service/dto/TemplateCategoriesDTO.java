package com.isoft.svisrevamp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.svisrevamp.domain.TemplateCategories} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateCategoriesDTO implements Serializable {

    private Long id;

    private String code;

    @NotNull
    private Integer noOfQuestions;

    @NotNull
    private Integer seq;

    private Integer status;

    private TemplateDTO template;

    private CategoriesDTO categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
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

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateCategoriesDTO)) {
            return false;
        }

        TemplateCategoriesDTO templateCategoriesDTO = (TemplateCategoriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateCategoriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCategoriesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", noOfQuestions=" + getNoOfQuestions() +
            ", seq=" + getSeq() +
            ", status=" + getStatus() +
            ", template=" + getTemplate() +
            ", categories=" + getCategories() +
            "}";
    }
}
