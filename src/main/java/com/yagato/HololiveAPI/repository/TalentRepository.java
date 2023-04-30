package com.yagato.HololiveAPI.repository;

import com.yagato.HololiveAPI.model.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    List<Talent> findAllByOrderById();

    Optional<Talent> findByName(String name);

    Optional<Talent> findByChannelId(String channelId);

    @Transactional
    void deleteByName(String name);
}
