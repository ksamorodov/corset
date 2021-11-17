package ru.saprcorset.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KernelDTO {

    private Long id;
    private String string;
    private Integer digit;
}
