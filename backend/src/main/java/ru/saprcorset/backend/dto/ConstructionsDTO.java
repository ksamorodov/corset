package ru.saprcorset.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record ConstructionsDTO (Integer id, String name, boolean leftSupport, boolean rightSupport, List<KernelDTO> kernels) {
    public record KernelDTO (
            Integer id,
        BigDecimal kernelSize,
        BigDecimal crossSectionalArea,
        BigDecimal elasticModulus,
        BigDecimal allowableStress,
        BigDecimal concentratedLoad,
        BigDecimal linearVoltage,
        Integer constructionsId) {
        public KernelDTO ofConstructions(Integer id) {
            return new KernelDTO(null, kernelSize, crossSectionalArea, elasticModulus, allowableStress, concentratedLoad, linearVoltage, id);
        }
    }
}
