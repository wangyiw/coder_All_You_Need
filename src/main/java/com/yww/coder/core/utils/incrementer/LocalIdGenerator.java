package com.yww.coder.core.utils.incrementer;

import java.util.UUID;

/**
 * id生成器实现
 */
public class LocalIdGenerator implements IdGenerate {

    private static final Sequence sequence = new Sequence();

    public LocalIdGenerator() {

    }

    @Override
    public long getLongId() {
        return sequence.nextId();
    }

    @Override
    public String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
