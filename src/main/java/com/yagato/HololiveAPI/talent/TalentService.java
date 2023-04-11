package com.yagato.HololiveAPI.talent;

import java.util.List;

public interface TalentService {

    List<Talent> findAll();

    List<Talent> findAllByOrderById();

    Talent findByName(String name);

    Talent findByChannelId(String channelId);

    Talent findById(int id);

    Talent save(Talent talent);

    void deleteById(int id);

    void deleteByName(String name);

}
