package com.yagato.HololiveAPI.model;

//@Entity
//@Table(name = "talents_generations")
public class TalentGeneration {
    /*

    @Id
    @SequenceGenerator(name = "talents_generations_id_generator", sequenceName = "talents_generations_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "talents_generations_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    public TalentGeneration() {

    }

    public TalentGeneration(Talent talent, Generation generation) {
        this.talent = talent;
        this.generation = generation;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Talent getTalent() {
        return talent;
    }

    @JsonProperty
    public void setTalent(Talent talent) {
        this.talent = talent;
    }

    @JsonIgnore
    public Generation getGeneration() {
        return generation;
    }

    @JsonProperty
    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    @Override
    public String toString() {
        return "TalentGeneration{" +
                "id=" + id +
                ", talent=" + talent +
                ", generation=" + generation +
                '}';
    }

     */
}