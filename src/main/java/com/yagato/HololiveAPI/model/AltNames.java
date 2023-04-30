package com.yagato.HololiveAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alt_names")
public class AltNames {

    @Id
    @SequenceGenerator(name = "alt_names_id_generator", sequenceName = "alt_names_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "alt_names_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty("id"))
    private Integer id;

    @Column(name = "japanese_name")
    @JsonProperty("japanese_name")
    private String japaneseName;

    @Column(name = "english_name")
    @JsonProperty("english_name")
    private String englishName;

    @Column(name = "chinese_name")
    @JsonProperty("chinese_name")
    private String chineseName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    @Getter(onMethod_ = @JsonIgnore)
    @Setter(onMethod_ = @JsonProperty)
    private Talent talent;
}
