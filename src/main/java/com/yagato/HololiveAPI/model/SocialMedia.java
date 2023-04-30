package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @SequenceGenerator(name = "social_media_id_generator", sequenceName = "social_media_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "social_media_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
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
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
    private Talent talent;
}
