package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
public class Model {

    @Id
    @SequenceGenerator(name = "models_id_generator", sequenceName = "models_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "models_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
    private Talent talent;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "models_illustrators",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "illustrator_id")
    )
    private List<Illustrator> illustrators;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "models_riggers",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "rigger_id")
    )
    private List<Rigger> riggers;
}
