package com.yagato.HololiveAPI.illustrator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.talent.support_entities.Model;
import jakarta.persistence.*;

import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "illustrators", cascade = CascadeType.ALL)
    private List<Model> models;

    public Illustrator() {

    }

    public Illustrator(String name, List<Model> models) {
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
        return "Illustrator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", models=" + models +
                '}';
    }
}
