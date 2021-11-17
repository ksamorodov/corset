/*
 * This file is generated by jOOQ.
 */
package ru.saprcorset.backend.adapters.db.jooq;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.constructions_seq</code>
     */
    public static final Sequence<Long> CONSTRUCTIONS_SEQ = Internal.createSequence("constructions_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.kernels_seq</code>
     */
    public static final Sequence<Long> KERNELS_SEQ = Internal.createSequence("kernels_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
