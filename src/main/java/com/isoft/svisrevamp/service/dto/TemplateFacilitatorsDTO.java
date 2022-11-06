package com.isoft.svisrevamp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.svisrevamp.domain.TemplateFacilitators} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateFacilitatorsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer count;

    @NotNull
    private Long centerId;

    @NotNull
    private Integer facilitatorType;

    private TemplateDTO template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Integer getFacilitatorType() {
        return facilitatorType;
    }

    public void setFacilitatorType(Integer facilitatorType) {
        this.facilitatorType = facilitatorType;
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
        if (!(o instanceof TemplateFacilitatorsDTO)) {
            return false;
        }

        TemplateFacilitatorsDTO templateFacilitatorsDTO = (TemplateFacilitatorsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateFacilitatorsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFacilitatorsDTO{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", centerId=" + getCenterId() +
            ", facilitatorType=" + getFacilitatorType() +
            ", template=" + getTemplate() +
            "}";
    }
}
