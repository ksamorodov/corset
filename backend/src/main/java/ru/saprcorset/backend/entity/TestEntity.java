package ru.saprcorset.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private Long id;
    private String string;
    private Integer digit;
}