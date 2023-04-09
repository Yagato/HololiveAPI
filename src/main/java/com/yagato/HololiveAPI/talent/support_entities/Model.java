package com.yagato.HololiveAPI.talent.support_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.illustrator.Illustrator;
import com.yagato.HololiveAPI.talent.Talent;
import jakarta.persistence.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "talents_id")
    private Talent talent;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "models_illustrators",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "illustrator_id")
    )
    private List<Illustrator> illustrators;

    public Model() {

    }

    public Model(String name, String imageURL, Talent talent, List<Illustrator> illustrators) {
        this.name = name;
        this.imageURL = imageURL;
        this.talent = talent;
        this.illustrators = illustrators;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @JsonIgnore
    public Talent getTalent() {
        return talent;
    }

    @JsonProperty
    public void setTalent(Talent talent) {
        this.talent = talent;
    }

    public List<Illustrator> getIllustrators() {
        return illustrators;
    }

    public void setIllustrators(List<Illustrator> illustrators) {
        this.illustrators = illustrators;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", talent=" + talent +
                ", illustrators=" + illustrators +
                '}';
    }
}
