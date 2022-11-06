package com.isoft.svisrevamp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.svisrevamp.domain.Template} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameAr;

    @NotNull
    private String nameEn;

    @NotNull
    private String code;

    private Long timeInSec;

    private Double passScore;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(Long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public Double getPassScore() {
        return passScore;
    }

    public void setPassScore(Double passScore) {
        this.passScore = passScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateDTO)) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", timeInSec=" + getTimeInSec() +
            ", passScore=" + getPassScore() +
            ", status=" + getStatus() +
            "}";
    }
}
