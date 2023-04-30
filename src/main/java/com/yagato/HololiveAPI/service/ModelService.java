package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.model.Model;

import java.util.List;

public interface ModelService {

    List<Model> findByTalentId(int talentId);

    Model findById(int id);

    Model save(Model model);

    void deleteById(int id);

}
