package com.yagato.HololiveAPI.dto;

import com.yagato.HololiveAPI.model.*;

import java.sql.Date;
import java.util.List;

public record TalentDto(
        Integer id,
        String name,
        AltNames alternative_names,
        Date debut_date,
        Date birthday,
        Integer age,
        List<Generation> generations,
        List<Model> models,
        Integer height,
        Double weight,
        Integer subscribers,
        String channel_id,
        List<String> fan_names,
        String oshi_mark,
        SocialMedia social_media,
        Hashtags hashtags,
        String catchphrase,
        List<String> nicknames,
        Boolean is_active,
        Date graduation_date
) {
}
