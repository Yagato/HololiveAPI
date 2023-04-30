package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
    private List<Model> models;
}
