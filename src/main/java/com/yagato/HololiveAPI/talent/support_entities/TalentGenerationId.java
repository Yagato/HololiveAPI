package com.yagato.HololiveAPI.talent.support_entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TalentGenerationId implements Serializable {

    @Column(name = "talent_id")
    private Integer talendId;

    @Column(name = "generation_id")
    private Integer generationId;

    public TalentGenerationId() {

    }

    public TalentGenerationId(Integer talendId, Integer generationId) {
        this.talendId = talendId;
        this.generationId = generationId;
    }

    //@JsonIgnore
    public Integer getTalendId() {
        return talendId;
    }

    //@JsonProperty
    public void setTalendId(Integer talendId) {
        this.talendId = talendId;
    }

    //@JsonIgnore
    public Integer getGenerationId() {
        return generationId;
    }

    //@JsonProperty
    public void setGenerationId(Integer generationId) {
        this.generationId = generationId;
    }

    @Override
    public String toString() {
        return "TalentGenerationId{" +
                "talendId=" + talendId +
                ", generationId=" + generationId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalentGenerationId that = (TalentGenerationId) o;
        return Objects.equals(talendId, that.talendId) && Objects.equals(generationId, that.generationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talendId, generationId);
    }
}
