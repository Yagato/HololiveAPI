package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "riggers")
public class Rigger {

    @Id
    @SequenceGenerator(name = "riggers_id_generator", sequenceName = "riggers_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "riggers_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "riggers")
    @JsonBackReference
    private List<Model> models;

    public Rigger() {

    }

    public Rigger(String name, List<Model> models) {
        this.name = name;
        this.models = models;
    }

    public Integer getId() {
        return id;
    }

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
        return "Rigger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", models=" + models +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rigger rigger = (Rigger) o;
        return Objects.equals(id, rigger.id) && Objects.equals(name, rigger.name) && Objects.equals(models, rigger.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, models);
    }
}
