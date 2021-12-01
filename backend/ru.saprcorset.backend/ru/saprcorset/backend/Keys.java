/*
 * This file is generated by jOOQ.
 */
package ru.saprcorset.backend;


import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.saprcorset.backend.tables.Constructions;
import ru.saprcorset.backend.tables.Databasechangeloglock;
import ru.saprcorset.backend.tables.Kernels;
import ru.saprcorset.backend.tables.records.ConstructionsRecord;
import ru.saprcorset.backend.tables.records.DatabasechangeloglockRecord;
import ru.saprcorset.backend.tables.records.KernelsRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ConstructionsRecord> CONSTRUCTIONS_PK = Internal.createUniqueKey(Constructions.CONSTRUCTIONS, DSL.name("constructions_pk"), new TableField[] { Constructions.CONSTRUCTIONS.ID }, true);
    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<KernelsRecord> KERNELS_PK = Internal.createUniqueKey(Kernels.KERNELS, DSL.name("kernels_pk"), new TableField[] { Kernels.KERNELS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ConstructionsRecord, KernelsRecord> CONSTRUCTIONS__CONSTRUCTIONS_KERNEL_ID_FKEY = Internal.createForeignKey(Constructions.CONSTRUCTIONS, DSL.name("constructions_kernel_id_fkey"), new TableField[] { Constructions.CONSTRUCTIONS.KERNEL_ID }, Keys.KERNELS_PK, new TableField[] { Kernels.KERNELS.ID }, true);
}