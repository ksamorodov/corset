package ru.saprcorset.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KernelEntity {
    private Long id;
    private String string;
    private Integer digit;
}