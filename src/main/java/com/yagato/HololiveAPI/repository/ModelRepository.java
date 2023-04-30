package com.yagato.HololiveAPI.repository;

import com.yagato.HololiveAPI.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    List<Model> findByTalentId(int talentId);

}
