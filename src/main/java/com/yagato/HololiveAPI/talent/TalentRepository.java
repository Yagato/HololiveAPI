package com.yagato.HololiveAPI.talent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    List<Talent> findAllByOrderById();


    /*
    @Query("SELECT t.id, t.name, t.debutDate, t.birthday, t.age, " +
                    "t.height, t.weight, t.subscribers, t.channelId, t.fanNames, t.oshiMark, " +
                    "t.socialMedia, t.hashtags, t.catchphrase, t.nicknames, t.isActive " +
            "FROM Talent t, Generation g, TalentGeneration tg, AltNames a " +
            "WHERE t.id = tg.talent AND g.id = tg.generation")
    List<Talent> findAllTalentsGenerations();
     */

    /*
    @Query("SELECT t " +
            "FROM Talent t, Generation g, TalentGeneration tg " +
            "WHERE t.id = tg.talent AND g.id = tg.generation")
    List<Talent> findAllTalentsGenerations();
     */


}
