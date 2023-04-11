package com.yagato.HololiveAPI.talent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    List<Talent> findAllByOrderById();

    Talent findByName(String name);

    Talent findByChannelId(String channelId);

    @Transactional
    void deleteByName(String name);
}
