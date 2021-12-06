package ru.saprcorset.backend.dto;

import java.util.List;
import java.util.UUID;

public record CalculateResponseDTO(Integer id, List<Double> delta,
                                   List<Double> n0, List<Double> nL,
                                   List<Double> u0, List<Double> uL,
                                   List<Double> uExtr, List<Double> xExtr,
                                   List<Double> sigma0, List<Double> sigmaL) {
}
