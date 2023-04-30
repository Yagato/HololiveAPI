package com.yagato.HololiveAPI.service.dto;

import com.yagato.HololiveAPI.dto.TalentDto;
import com.yagato.HololiveAPI.model.Generation;
import com.yagato.HololiveAPI.model.Talent;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TalentMapper implements Function<Talent, TalentDto> {

    @Override
    public TalentDto apply(Talent talent) {
        return new TalentDto(
                talent.getId(),
                talent.getName(),
                talent.getAltNames(),
                talent.getDebutDate(),
                talent.getBirthday(),
                talent.getAge(),
                talent.getGenerations(),
                talent.getModels(),
                talent.getHeight(),
                talent.getWeight(),
                talent.getSubscribers(),
                talent.getChannelId(),
                talent.getFanNames(),
                talent.getOshiMark(),
                talent.getSocialMedia(),
                talent.getHashtags(),
                talent.getCatchphrase(),
                talent.getNicknames(),
                talent.getIsActive(),
                talent.getGraduationDate());
    }

    public Talent toTalent(TalentDto talentDto) {
        return new Talent(
                talentDto.id(),
                talentDto.name(),
                talentDto.alternative_names(),
                talentDto.debut_date(),
                talentDto.birthday(),
                talentDto.age(),
                talentDto.generations(),
                talentDto.models(),
                talentDto.height(),
                talentDto.weight(),
                talentDto.subscribers(),
                talentDto.channel_id(),
                talentDto.fan_names(),
                talentDto.oshi_mark(),
                talentDto.social_media(),
                talentDto.hashtags(),
                talentDto.catchphrase(),
                talentDto.nicknames(),
                talentDto.is_active(),
                talentDto.graduation_date()
        );
    }
}
