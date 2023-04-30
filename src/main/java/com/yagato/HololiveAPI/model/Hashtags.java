package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.model.Talent;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hashtags")
public class Hashtags {

    @Id
    @SequenceGenerator(name = "hashtags_id_generator", sequenceName = "hashtags_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "hashtags_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "general")
    private String general;

    @Column(name = "stream_talk")
    private String streamTalk;

    @Column(name = "fanart")
    private String fanart;

    @Column(name = "others")
    private List<String> others;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talents_id")
    private Talent talent;

    public Hashtags() {

    }

    public Hashtags(String general, String streamTalk, String fanart, List<String> others, Talent talent) {
        this.general = general;
        this.streamTalk = streamTalk;
        this.fanart = fanart;
        this.others = others;
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

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getStreamTalk() {
        return streamTalk;
    }

    public void setStreamTalk(String streamTalk) {
        this.streamTalk = streamTalk;
    }

    public String getFanart() {
        return fanart;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
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
        return "Hashtags{" +
                "id=" + id +
                ", general='" + general + '\'' +
                ", streamTalk='" + streamTalk + '\'' +
                ", fanart='" + fanart + '\'' +
                ", others=" + others +
                ", talent=" + talent +
                '}';
    }
}
