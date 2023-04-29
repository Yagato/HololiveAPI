package com.yagato.HololiveAPI.generation;

import java.util.List;

public class GenerationResponse {

    private Integer id;
    private String name;
    private List<String> talents;

    public GenerationResponse() {

    }

    public GenerationResponse(Integer id, String name, List<String> talents) {
        this.id = id;
        this.name = name;
        this.talents = talents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTalent() {
        return talents;
    }

    public void setTalent(List<String> talents) {
        this.talents = talents;
    }
}
