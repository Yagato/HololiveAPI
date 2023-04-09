package com.yagato.HololiveAPI.talent;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalentService {

    List<Talent> findAll();

    List<Talent> findAllByOrderById();

    /*
    @Query("SELECT t.id, t.name, a.altNames, t.debutDate, t.birthday, t.age, g.name, " +
            "t.height, t.weight, t.subscribers, t.channelId, t.fanNames, t.oshiMark, " +
            "t.socialMedia, t.hashtags, t.catchphrase, t.nicknames, t.isActive " +
            "FROM Talent t, Generation g, TalentGeneration tg, AltNames a " +
            "WHERE t.id = tg.talent AND g.id = tg.generation AND t.id = a.talent")
    List<Talent> findAllTalentsGenerations();
     */

    /*
    @Query("SELECT t " +
            "FROM Talent t, Generation g, TalentGeneration tg " +
            "WHERE t.id = tg.talent AND g.id = tg.generation")
    List<Talent> findAllTalentsGenerations();
     */

    Talent findById(int id);

    Talent save(Talent talent);

    void deleteById(int id);

}
