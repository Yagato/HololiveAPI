package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.*;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.MERGE)
    private AltNames altNames;

    @Column(name = "debut_date")
    private Date debutDate;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "talents_generations",
            joinColumns = @JoinColumn(name = "talent_id"),
            inverseJoinColumns = @JoinColumn(name = "generation_id")
    )
    //@JsonManagedReference
    private List<Generation> generations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.MERGE)
    private List<Model> models;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Double weight;

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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.MERGE)
    private SocialMedia socialMedia;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "talent", cascade = CascadeType.MERGE)
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

    @Column(name = "graduation_date")
    private Date graduationDate;
}
