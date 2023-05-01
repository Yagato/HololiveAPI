package com.yagato.HololiveAPI.utils;

import com.yagato.HololiveAPI.model.Generation;

import java.util.Comparator;

public class JsonSort implements Comparator<Generation> {

    @Override
    public int compare(Generation generation, Generation generation2) {
        return generation.getId() - generation2.getId();
    }

}
