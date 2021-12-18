package ru.saprcorset.backend.resourse;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.adapters.db.jooq.tables.Constructions;
import ru.saprcorset.backend.adapters.db.jooq.tables.Kernels;
import ru.saprcorset.backend.adapters.db.jooq.tables.records.KernelsRecord;
import ru.saprcorset.backend.dto.ConstructionsDTO;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ConstructionsResource {
    private final DSLContext dsl;

    @Transactional
    public Optional<Integer> save(ConstructionsDTO constructionsDTO) {
        Optional<Integer> id = dsl.insertInto(Constructions.CONSTRUCTIONS).
                set(Constructions.CONSTRUCTIONS.LEFT_SUPPORT, constructionsDTO.leftSupport())
                .set(Constructions.CONSTRUCTIONS.NAME, constructionsDTO.name())
                .set(Constructions.CONSTRUCTIONS.RIGHT_SUPPORT, constructionsDTO.rightSupport()).returning(Constructions.CONSTRUCTIONS.ID).fetchOptional().map(e -> e.getId());
        if (id.isPresent()) {
            constructionsDTO.kernels().forEach(i ->{
                dsl.insertInto(Kernels.KERNELS)
                        .set(Kernels.KERNELS.KERNEL_SIZE, i.kernelSize())
                        .set(Kernels.KERNELS.CONSTRUCTIONS_ID, id.get())
                        .set(Kernels.KERNELS.ALLOWABLE_STRESS, i.allowableStress())
                        .set(Kernels.KERNELS.CONCENTRATED_LOAD, i.concentratedLoad())
                        .set(Kernels.KERNELS.CROSS_SECTIONAL_AREA, i.crossSectionalArea())
                        .set(Kernels.KERNELS.ELASTIC_MODULUS, i.elasticModulus())
                        .set(Kernels.KERNELS.LINEAR_VOLTAGE, i.linearVoltage()).execute();
            });

            return Optional.of(id.get());
        }
        return Optional.empty();
    }

    private static KernelsRecord kernelRecordFrom(ConstructionsDTO.KernelDTO i, Integer constructionId) {
        return new KernelsRecord(null,
                i.kernelSize(),
                i.crossSectionalArea(),
                i.elasticModulus(),
                i.allowableStress(),
                i.concentratedLoad(),
                i.linearVoltage(),
                constructionId);
    }

    @Transactional
    public Optional<ConstructionsDTO> getById(Integer id) {
        return dsl.select(
                Constructions.CONSTRUCTIONS.ID,
                Constructions.CONSTRUCTIONS.NAME,
                Constructions.CONSTRUCTIONS.LEFT_SUPPORT,
                Constructions.CONSTRUCTIONS.RIGHT_SUPPORT
                ).from(Constructions.CONSTRUCTIONS).where(Constructions.CONSTRUCTIONS.ID.eq(id)).stream().map(result -> new ConstructionsDTO(result.value1(), result.value2(), result.value3(), result.value4(), getKernelsListByConstructionId(result.value1()))).findFirst();
    }

    @Transactional
    public List<ConstructionsDTO.KernelDTO> getKernelsListByConstructionId(Integer id) {
        return dsl.select(
                Kernels.KERNELS.ID,
                Kernels.KERNELS.KERNEL_SIZE,
                Kernels.KERNELS.CROSS_SECTIONAL_AREA,
                Kernels.KERNELS.ELASTIC_MODULUS,
                Kernels.KERNELS.ALLOWABLE_STRESS,
                Kernels.KERNELS.CONCENTRATED_LOAD,
                Kernels.KERNELS.LINEAR_VOLTAGE,
                Kernels.KERNELS.CONSTRUCTIONS_ID
        ).from(Kernels.KERNELS).where(Kernels.KERNELS.CONSTRUCTIONS_ID.eq(id)).stream()
                .map(result -> new ConstructionsDTO.KernelDTO(result.value1(),
                        result.value2(), result.value3(), result.value4(),
                        result.value5(), result.value6(), result.value7(),
                        result.value8())).toList();

    }

    @Transactional
    public List<ConstructionsDTO> getConstructionsList() {
        return dsl.select(
                Constructions.CONSTRUCTIONS.ID,
                Constructions.CONSTRUCTIONS.NAME,
                Constructions.CONSTRUCTIONS.LEFT_SUPPORT,
                Constructions.CONSTRUCTIONS.RIGHT_SUPPORT
        ).from(Constructions.CONSTRUCTIONS).stream()
                .map(result -> new ConstructionsDTO(result.value1(), result.value2(),
                        result.value3(), result.value4(), getKernelsListByConstructionId(result.value1()))).toList();

    }

    @Transactional
    public void deleteByName(String name) {
        Optional<ConstructionsDTO> constructionsDTO = dsl.select(
                        Constructions.CONSTRUCTIONS.ID,
                        Constructions.CONSTRUCTIONS.NAME,
                        Constructions.CONSTRUCTIONS.LEFT_SUPPORT,
                        Constructions.CONSTRUCTIONS.RIGHT_SUPPORT
                ).from(Constructions.CONSTRUCTIONS).where(Constructions.CONSTRUCTIONS.NAME.eq(name)).stream().findFirst()
                .map(result -> new ConstructionsDTO(result.value1(), result.value2(),
                        result.value3(), result.value4(), getKernelsListByConstructionId(result.value1())));
        if (constructionsDTO.isPresent()) {
            dsl.deleteFrom(Kernels.KERNELS).where(Kernels.KERNELS.CONSTRUCTIONS_ID.eq(constructionsDTO.get().id())).execute();
            dsl.deleteFrom(Constructions.CONSTRUCTIONS).where(Constructions.CONSTRUCTIONS.ID.eq(constructionsDTO.get().id())).execute();
        }
    }
}
