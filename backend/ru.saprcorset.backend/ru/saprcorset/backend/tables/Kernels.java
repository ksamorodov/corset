/*
 * This file is generated by jOOQ.
 */
package ru.saprcorset.backend.tables;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.saprcorset.backend.Keys;
import ru.saprcorset.backend.Public;
import ru.saprcorset.backend.tables.records.KernelsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Kernels extends TableImpl<KernelsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.kernels</code>
     */
    public static final Kernels KERNELS = new Kernels();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<KernelsRecord> getRecordType() {
        return KernelsRecord.class;
    }

    /**
     * The column <code>public.kernels.id</code>.
     */
    public final TableField<KernelsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.kernels.kernel_size</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> KERNEL_SIZE = createField(DSL.name("kernel_size"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.kernels.crosssectionalarea</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> CROSSSECTIONALAREA = createField(DSL.name("crosssectionalarea"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.kernels.elasticmodulus</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> ELASTICMODULUS = createField(DSL.name("elasticmodulus"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.kernels.allowablestress</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> ALLOWABLESTRESS = createField(DSL.name("allowablestress"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.kernels.concentratedload</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> CONCENTRATEDLOAD = createField(DSL.name("concentratedload"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.kernels.linearvoltage</code>.
     */
    public final TableField<KernelsRecord, BigDecimal> LINEARVOLTAGE = createField(DSL.name("linearvoltage"), SQLDataType.NUMERIC, this, "");

    private Kernels(Name alias, Table<KernelsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Kernels(Name alias, Table<KernelsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.kernels</code> table reference
     */
    public Kernels(String alias) {
        this(DSL.name(alias), KERNELS);
    }

    /**
     * Create an aliased <code>public.kernels</code> table reference
     */
    public Kernels(Name alias) {
        this(alias, KERNELS);
    }

    /**
     * Create a <code>public.kernels</code> table reference
     */
    public Kernels() {
        this(DSL.name("kernels"), null);
    }

    public <O extends Record> Kernels(Table<O> child, ForeignKey<O, KernelsRecord> key) {
        super(child, key, KERNELS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<KernelsRecord, Integer> getIdentity() {
        return (Identity<KernelsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<KernelsRecord> getPrimaryKey() {
        return Keys.KERNELS_PK;
    }

    @Override
    public Kernels as(String alias) {
        return new Kernels(DSL.name(alias), this);
    }

    @Override
    public Kernels as(Name alias) {
        return new Kernels(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Kernels rename(String name) {
        return new Kernels(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Kernels rename(Name name) {
        return new Kernels(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}