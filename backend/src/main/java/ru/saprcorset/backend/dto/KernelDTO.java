package ru.saprcorset.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KernelDTO {
    private Long id;
    private Integer kernelSize;
    private Integer crossSectionalArea;
    private Integer elasticModulus;
    private Integer allowableStress;
    private Integer concentratedLoad;
    private Integer linearVoltage;
}
