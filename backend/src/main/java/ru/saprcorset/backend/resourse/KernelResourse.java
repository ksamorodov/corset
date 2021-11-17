package ru.saprcorset.backend.resourse;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.adapters.db.jooq.tables.records.KernelsRecord;
import ru.saprcorset.backend.dto.KernelDTO;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KernelResourse {
    private final DSLContext dsl;

    @Transactional
    public void save(KernelDTO kernelEntity) {
        dsl.batchInsert(kernelRecordFrom(kernelEntity)).execute();
    }

    private static KernelsRecord kernelRecordFrom(KernelDTO i) {
        return new KernelsRecord(null,
                BigDecimal.valueOf(i.getKernelSize()),
                BigDecimal.valueOf(i.getCrossSectionalArea()),
                BigDecimal.valueOf(i.getElasticModulus()),
                BigDecimal.valueOf(i.getAllowableStress()),
                BigDecimal.valueOf(i.getConcentratedLoad()),
                BigDecimal.valueOf(i.getLinearVoltage())
                );
    }

    @Transactional
    public KernelDTO getById(Long id) {
        return null;
    }

    @Transactional
    public List<KernelDTO> getListAccounts() {
        //dsl.batchInsert();
        return null;
    }
}
