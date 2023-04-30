package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hashtags")
public class Hashtags {

    @Id
    @SequenceGenerator(name = "hashtags_id_generator", sequenceName = "hashtags_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "hashtags_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty("id"))
    private Integer id;

    @Column(name = "general")
    @JsonProperty("general")
    private String general;

    @Column(name = "stream_talk")
    @JsonProperty("stream_talk")
    private String streamTalk;

    @Column(name = "fanart")
    @JsonProperty("fanart")
    private String fanart;

    @Column(name = "others")
    @JsonProperty("others")
    private List<String> others;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talents_id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
    private Talent talent;
}
