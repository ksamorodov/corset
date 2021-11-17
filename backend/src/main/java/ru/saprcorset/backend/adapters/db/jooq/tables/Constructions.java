/*
 * This file is generated by jOOQ.
 */
package ru.saprcorset.backend.adapters.db.jooq.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.saprcorset.backend.adapters.db.jooq.Keys;
import ru.saprcorset.backend.adapters.db.jooq.Public;
import ru.saprcorset.backend.adapters.db.jooq.tables.records.ConstructionsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Constructions extends TableImpl<ConstructionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.constructions</code>
     */
    public static final Constructions CONSTRUCTIONS = new Constructions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConstructionsRecord> getRecordType() {
        return ConstructionsRecord.class;
    }

    /**
     * The column <code>public.constructions.id</code>.
     */
    public final TableField<ConstructionsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.constructions.left_support</code>.
     */
    public final TableField<ConstructionsRecord, Boolean> LEFT_SUPPORT = createField(DSL.name("left_support"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.constructions.right_support</code>.
     */
    public final TableField<ConstructionsRecord, Boolean> RIGHT_SUPPORT = createField(DSL.name("right_support"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.constructions.name</code>.
     */
    public final TableField<ConstructionsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private Constructions(Name alias, Table<ConstructionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Constructions(Name alias, Table<ConstructionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.constructions</code> table reference
     */
    public Constructions(String alias) {
        this(DSL.name(alias), CONSTRUCTIONS);
    }

    /**
     * Create an aliased <code>public.constructions</code> table reference
     */
    public Constructions(Name alias) {
        this(alias, CONSTRUCTIONS);
    }

    /**
     * Create a <code>public.constructions</code> table reference
     */
    public Constructions() {
        this(DSL.name("constructions"), null);
    }

    public <O extends Record> Constructions(Table<O> child, ForeignKey<O, ConstructionsRecord> key) {
        super(child, key, CONSTRUCTIONS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ConstructionsRecord, Integer> getIdentity() {
        return (Identity<ConstructionsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ConstructionsRecord> getPrimaryKey() {
        return Keys.CONSTRUCTIONS_PK;
    }

    @Override
    public Constructions as(String alias) {
        return new Constructions(DSL.name(alias), this);
    }

    @Override
    public Constructions as(Name alias) {
        return new Constructions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Constructions rename(String name) {
        return new Constructions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Constructions rename(Name name) {
        return new Constructions(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Boolean, Boolean, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
