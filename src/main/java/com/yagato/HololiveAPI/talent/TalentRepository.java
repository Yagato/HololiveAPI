package com.yagato.HololiveAPI.talent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    List<Talent> findAllByOrderById();

    Talent findByName(String name);
}
