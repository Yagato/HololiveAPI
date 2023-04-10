package com.yagato.HololiveAPI.talent;

import com.fasterxml.jackson.annotation.*;
import com.yagato.HololiveAPI.generation.Generation;
import com.yagato.HololiveAPI.talent.support_entities.*;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "talents")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Talent {

    @Id
    @SequenceGenerator(name = "talents_id_generator", sequenceName = "talents_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "talents_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.ALL)
    private AltNames altNames;

    @Column(name = "debut_date")
    private Date debutDate;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "talents_generations",
            joinColumns = @JoinColumn(name = "talent_id"),
            inverseJoinColumns = @JoinColumn(name = "generation_id")
    )
    @JsonManagedReference
    private List<Generation> generations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.ALL)
    private List<Model> models;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "subscribers")
    private Integer subscribers;

    @Column(name = "channel_id")
    private String channelId;

    @Type(ListArrayType.class)
    @Column(
            name = "fan_names",
            columnDefinition = "TEXT[]"
    )
    private List<String> fanNames;

    @Column(name = "oshi_mark")
    private String oshiMark;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.ALL)
    private SocialMedia socialMedia;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.ALL)
    private Hashtags hashtags;

    @Column(name = "catchphrase")
    private String catchphrase;

    @Type(ListArrayType.class)
    @Column(
            name = "nicknames",
            columnDefinition = "TEXT[]"
    )
    private List<String> nicknames;

    @Column(name = "is_active")
    private Boolean isActive;

    public Talent() {

    }

    public Talent(String name, AltNames altNames, Date debutDate, Date birthday, Integer age, List<Generation> generations, List<Model> models, Integer height, Integer weight, Integer subscribers, String channelId, List<String> fanNames, String oshiMark, SocialMedia socialMedia, Hashtags hashtags, String catchphrase, List<String> nicknames, Boolean isActive) {
        this.name = name;
        this.altNames = altNames;
        this.debutDate = debutDate;
        this.birthday = birthday;
        this.age = age;
        this.generations = generations;
        this.models = models;
        this.height = height;
        this.weight = weight;
        this.subscribers = subscribers;
        this.channelId = channelId;
        this.fanNames = fanNames;
        this.oshiMark = oshiMark;
        this.socialMedia = socialMedia;
        this.hashtags = hashtags;
        this.catchphrase = catchphrase;
        this.nicknames = nicknames;
        this.isActive = isActive;
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

    public AltNames getAltNames() {
        return altNames;
    }

    public void setAltNames(AltNames altNames) {
        this.altNames = altNames;
    }

    public Date getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(Date debutDate) {
        this.debutDate = debutDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Generation> getGenerations() {
        return generations;
    }

    public void setGenerations(List<Generation> generations) {
        this.generations = generations;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public List<String> getFanNames() {
        return fanNames;
    }

    public void setFanNames(List<String> fanNames) {
        this.fanNames = fanNames;
    }

    public String getOshiMark() {
        return oshiMark;
    }

    public void setOshiMark(String oshiMark) {
        this.oshiMark = oshiMark;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public Hashtags getHashtags() {
        return hashtags;
    }

    public void setHashtags(Hashtags hashtags) {
        this.hashtags = hashtags;
    }

    public String getCatchphrase() {
        return catchphrase;
    }

    public void setCatchphrase(String catchphrase) {
        this.catchphrase = catchphrase;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Talent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", altNames=" + altNames +
                ", debutDate=" + debutDate +
                ", birthday=" + birthday +
                ", age=" + age +
                ", generations=" + generations +
                ", models=" + models +
                ", height=" + height +
                ", weight=" + weight +
                ", subscribers=" + subscribers +
                ", channelId='" + channelId + '\'' +
                ", fanNames=" + fanNames +
                ", oshiMark='" + oshiMark + '\'' +
                ", socialMedia=" + socialMedia +
                ", hashtags=" + hashtags +
                ", catchphrase='" + catchphrase + '\'' +
                ", nicknames=" + nicknames +
                ", isActive=" + isActive +
                '}';
    }
}
