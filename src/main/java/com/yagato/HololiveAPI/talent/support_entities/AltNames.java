package com.yagato.HololiveAPI.talent.support_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.talent.Talent;
import jakarta.persistence.*;

@Entity
@Table(name = "alt_names")
public class AltNames {

    @Id
    @SequenceGenerator(name = "alt_names_id_generator", sequenceName = "alt_names_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "alt_names_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "japanese_name")
    private String japaneseName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "chinese_name")
    private String chineseName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    private Talent talent;

    public AltNames() {

    }

    public AltNames(String japaneseName, String englishName, String chineseName, Talent talent) {
        this.japaneseName = japaneseName;
        this.englishName = englishName;
        this.chineseName = chineseName;
        this.talent = talent;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }

    public String getJapaneseName() {
        return japaneseName;
    }

    public void setJapaneseName(String japaneseName) {
        this.japaneseName = japaneseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @JsonIgnore
    public Talent getTalent() {
        return talent;
    }

    @JsonProperty
    public void setTalent(Talent talent) {
        this.talent = talent;
    }

    @Override
    public String toString() {
        return "AltNames{" +
                "id=" + id +
                ", japaneseName='" + japaneseName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", talent=" + talent +
                '}';
    }
}
