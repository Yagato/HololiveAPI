package com.yagato.HololiveAPI.illustrator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.talent.support_entities.Model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "illustrators")
public class Illustrator {

    @Id
    @SequenceGenerator(name = "illustrators_id_generator", sequenceName = "illustrators_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "illustrators_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "illustrators")
    @JsonBackReference
    private List<Model> models;

    public Illustrator() {

    }

    public Illustrator(String name, List<Model> models) {
        this.name = name;
        this.models = models;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Model> getModels() {
        return models;
    }

    @JsonProperty
    public void setModels(List<Model> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "Illustrator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", models=" + models +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Illustrator that = (Illustrator) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(models, that.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, models);
    }
}
