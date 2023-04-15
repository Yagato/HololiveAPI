package com.yagato.HololiveAPI.rigger;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiggerRepository extends JpaRepository<Rigger, Integer> {

    List<Rigger> findAllByOrderById();

    Rigger findById(int id);

    Rigger findByName(String name);

}
