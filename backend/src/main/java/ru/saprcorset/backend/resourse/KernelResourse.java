package ru.saprcorset.backend.resourse;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.adapters.db.jooq.tables.records.KernelsRecord;
import ru.saprcorset.backend.entity.KernelEntity;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KernelResourse {
    private final DSLContext dsl;

    @Transactional
    public void save(KernelEntity kernelEntity) {
        dsl.batchInsert(kernelRecordFrom(kernelEntity)).execute();
    }

    private static KernelsRecord kernelRecordFrom(KernelEntity i) {
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
    public KernelEntity getById(Long id) {
        return null;
    }

    @Transactional
    public List<KernelEntity> getListAccounts() {
        //dsl.batchInsert();
        return null;
    }
}
