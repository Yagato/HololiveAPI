package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "generations")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class Generation {

    @Id
    @SequenceGenerator(name = "generations_id_generator", sequenceName = "generations_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "generations_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "generations")
    @JsonBackReference
    private List<Talent> talents;

    public Generation() {

    }

    public Generation(String name, List<Talent> talents) {
        this.name = name;
        this.talents = talents;
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

    public List<Talent> getTalents() {
        return talents;
    }

    public void setTalents(List<Talent> talents) {
        this.talents = talents;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", talents=" + talents +
                '}';
    }
}
