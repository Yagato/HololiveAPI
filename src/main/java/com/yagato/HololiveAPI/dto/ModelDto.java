package com.yagato.HololiveAPI.dto;

import java.util.List;

public record ModelDto (
        String name,
        List<IllustratorDto> illustrators,
        List<RiggerDto> riggers
) {
}
