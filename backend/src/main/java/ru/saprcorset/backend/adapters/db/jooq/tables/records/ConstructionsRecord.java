/*
 * This file is generated by jOOQ.
 */
package ru.saprcorset.backend.adapters.db.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import ru.saprcorset.backend.adapters.db.jooq.tables.Constructions;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConstructionsRecord extends UpdatableRecordImpl<ConstructionsRecord> implements Record3<Integer, Boolean, Boolean> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.constructions.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.constructions.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.constructions.left_support</code>.
     */
    public void setLeftSupport(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.constructions.left_support</code>.
     */
    public Boolean getLeftSupport() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.constructions.right_support</code>.
     */
    public void setRightSupport(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.constructions.right_support</code>.
     */
    public Boolean getRightSupport() {
        return (Boolean) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Boolean, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, Boolean, Boolean> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Constructions.CONSTRUCTIONS.ID;
    }

    @Override
    public Field<Boolean> field2() {
        return Constructions.CONSTRUCTIONS.LEFT_SUPPORT;
    }

    @Override
    public Field<Boolean> field3() {
        return Constructions.CONSTRUCTIONS.RIGHT_SUPPORT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Boolean component2() {
        return getLeftSupport();
    }

    @Override
    public Boolean component3() {
        return getRightSupport();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Boolean value2() {
        return getLeftSupport();
    }

    @Override
    public Boolean value3() {
        return getRightSupport();
    }

    @Override
    public ConstructionsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ConstructionsRecord value2(Boolean value) {
        setLeftSupport(value);
        return this;
    }

    @Override
    public ConstructionsRecord value3(Boolean value) {
        setRightSupport(value);
        return this;
    }

    @Override
    public ConstructionsRecord values(Integer value1, Boolean value2, Boolean value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConstructionsRecord
     */
    public ConstructionsRecord() {
        super(Constructions.CONSTRUCTIONS);
    }

    /**
     * Create a detached, initialised ConstructionsRecord
     */
    public ConstructionsRecord(Integer id, Boolean leftSupport, Boolean rightSupport) {
        super(Constructions.CONSTRUCTIONS);

        setId(id);
        setLeftSupport(leftSupport);
        setRightSupport(rightSupport);
    }
}