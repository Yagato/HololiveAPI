package com.yagato.HololiveAPI.dto;

import java.util.List;

public record GenerationDto (
        String name,
        List<String> talentsNames
) {
}
