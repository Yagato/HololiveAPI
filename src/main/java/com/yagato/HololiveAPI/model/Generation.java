package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "generations")
    @JsonBackReference
    private List<Talent> talents;
}
