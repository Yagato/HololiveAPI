package com.yagato.HololiveAPI.talent.support_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.illustrator.Illustrator;
import com.yagato.HololiveAPI.rigger.Rigger;
import com.yagato.HololiveAPI.talent.Talent;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

    public Model() {

    }

    public Model(String name, String imageURL, Talent talent, List<Illustrator> illustrators, List<Rigger> riggers) {
        this.name = name;
        this.imageURL = imageURL;
        this.talent = talent;
        this.illustrators = illustrators;
        this.riggers = riggers;
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

    public List<Rigger> getRiggers() {
        return riggers;
    }

    public void setRiggers(List<Rigger> riggers) {
        this.riggers = riggers;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", talent=" + talent +
                ", illustrators=" + illustrators +
                ", riggers=" + riggers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id) && Objects.equals(name, model.name) && Objects.equals(imageURL, model.imageURL) && Objects.equals(talent, model.talent) && Objects.equals(illustrators, model.illustrators) && Objects.equals(riggers, model.riggers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageURL, talent, illustrators, riggers);
    }
}
