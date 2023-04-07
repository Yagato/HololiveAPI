package com.yagato.HololiveAPI.talents;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "talents")
public class Talent {

    @Id
    @SequenceGenerator(name = "talents_id_generator", sequenceName = "talents_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "talents_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "debut_date")
    private Date debutDate;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "age")
    private Integer age;

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

    public Talent(String name, Date debutDate, Date birthday, Integer age, Integer height,
                  Integer weight, Integer subscribers, String channelId, List<String> fanNames,
                  String oshiMark, String catchphrase, List<String> nicknames, Boolean isActive) {
        this.name = name;
        this.debutDate = debutDate;
        this.birthday = birthday;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.subscribers = subscribers;
        this.channelId = channelId;
        this.fanNames = fanNames;
        this.oshiMark = oshiMark;
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

    public Boolean getIsActive() {
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
                ", debutDate=" + debutDate +
                ", birthday=" + birthday +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", subscribers=" + subscribers +
                ", channelId='" + channelId + '\'' +
                ", fanNames=" + fanNames +
                ", oshiMark='" + oshiMark + '\'' +
                ", catchphrase='" + catchphrase + '\'' +
                ", nicknames=" + nicknames +
                ", isActive=" + isActive +
                '}';
    }
}
