package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.repository.ModelRepository;
import com.yagato.HololiveAPI.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;


    @Override
    public List<Model> findByTalentId(int talentId) {
        return modelRepository.findByTalentId(talentId);
    }

    @Override
    public Model findById(int id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Couldn't find model with id: " + id));
    }

    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public void deleteById(int id) {
        modelRepository.deleteById(id);
    }
}
