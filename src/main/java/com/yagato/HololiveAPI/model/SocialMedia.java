package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yagato.HololiveAPI.model.Talent;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @SequenceGenerator(name = "social_media_id_generator", sequenceName = "social_media_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "social_media_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Type(ListArrayType.class)
    @Column(
            name = "youtube",
            columnDefinition = "TEXT[]"
    )
    private List<String> youtube;

    @Type(ListArrayType.class)
    @Column(
            name = "twitch",
            columnDefinition = "TEXT[]"
    )
    private List<String> twitch;

    @Type(ListArrayType.class)
    @Column(
            name = "website",
            columnDefinition = "TEXT[]"
    )
    private List<String> website;

    @Type(ListArrayType.class)
    @Column(
            name = "twitter",
            columnDefinition = "TEXT[]"
    )
    private List<String> twitter;

    @Type(ListArrayType.class)
    @Column(
            name = "twitcasting",
            columnDefinition = "TEXT[]"
    )
    private List<String> twitcasting;

    @Type(ListArrayType.class)
    @Column(
            name = "marshmallow",
            columnDefinition = "TEXT[]"
    )
    private List<String> marshmallow;

    @Type(ListArrayType.class)
    @Column(
            name = "spotify",
            columnDefinition = "TEXT[]"
    )
    private List<String> spotify;

    @Type(ListArrayType.class)
    @Column(
            name = "reddit",
            columnDefinition = "TEXT[]"
    )
    private List<String> reddit;

    @Type(ListArrayType.class)
    @Column(
            name = "bilibili",
            columnDefinition = "TEXT[]"
    )
    private List<String> bilibili;

    @Type(ListArrayType.class)
    @Column(
            name = "others",
            columnDefinition = "TEXT[]"
    )
    private List<String> others;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talents_id")
    private Talent talent;

    public SocialMedia() {

    }

    public SocialMedia(List<String> youtube, List<String> twitch, List<String> website, List<String> twitter, List<String> twitcasting, List<String> marshmallow, List<String> spotify, List<String> reddit, List<String> bilibili, List<String> others, Talent talent) {
        this.youtube = youtube;
        this.twitch = twitch;
        this.website = website;
        this.twitter = twitter;
        this.twitcasting = twitcasting;
        this.marshmallow = marshmallow;
        this.spotify = spotify;
        this.reddit = reddit;
        this.bilibili = bilibili;
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

    public List<String> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<String> youtube) {
        this.youtube = youtube;
    }

    public List<String> getTwitch() {
        return twitch;
    }

    public void setTwitch(List<String> twitch) {
        this.twitch = twitch;
    }

    public List<String> getWebsite() {
        return website;
    }

    public void setWebsite(List<String> website) {
        this.website = website;
    }

    public List<String> getTwitter() {
        return twitter;
    }

    public void setTwitter(List<String> twitter) {
        this.twitter = twitter;
    }

    public List<String> getTwitcasting() {
        return twitcasting;
    }

    public void setTwitcasting(List<String> twitcasting) {
        this.twitcasting = twitcasting;
    }

    public List<String> getMarshmallow() {
        return marshmallow;
    }

    public void setMarshmallow(List<String> marshmallow) {
        this.marshmallow = marshmallow;
    }

    public List<String> getSpotify() {
        return spotify;
    }

    public void setSpotify(List<String> spotify) {
        this.spotify = spotify;
    }

    public List<String> getReddit() {
        return reddit;
    }

    public void setReddit(List<String> reddit) {
        this.reddit = reddit;
    }

    public List<String> getBilibili() {
        return bilibili;
    }

    public void setBilibili(List<String> bilibili) {
        this.bilibili = bilibili;
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
        return "SocialMedia{" +
                "id=" + id +
                ", youtube=" + youtube +
                ", twitch=" + twitch +
                ", website=" + website +
                ", twitter=" + twitter +
                ", twitcasting=" + twitcasting +
                ", marshmallow=" + marshmallow +
                ", spotify=" + spotify +
                ", reddit=" + reddit +
                ", bilibili=" + bilibili +
                ", others=" + others +
                ", talent=" + talent +
                '}';
    }
}
