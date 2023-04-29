package com.yagato.HololiveAPI.repository;

import com.yagato.HololiveAPI.model.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenerationRepository extends JpaRepository<Generation, Integer> {

    List<Generation> findAllByOrderById();

    Generation findById(int id);
    Generation findByName(String name);

}
