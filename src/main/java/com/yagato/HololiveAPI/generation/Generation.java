package com.yagato.HololiveAPI.generation;

import jakarta.persistence.*;

@Entity
@Table(name = "generations")
public class Generation {

    @Id
    @SequenceGenerator(name = "generations_id_generator", sequenceName = "generations_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "generations_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Generation() {

    }

    public Generation(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Generation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
