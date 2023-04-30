package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.dto.TalentDto;
import com.yagato.HololiveAPI.model.Talent;

import java.util.List;

public interface TalentService {

    List<TalentDto> findAll();

    List<TalentDto> findAllByOrderById();

    TalentDto findByName(String name);

    TalentDto findByChannelId(String channelId);

    TalentDto findById(int id);

    Talent save(Talent talent);

    void deleteById(int id);

    void deleteByName(String name);

}
