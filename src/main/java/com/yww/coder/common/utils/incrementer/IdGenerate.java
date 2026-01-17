package com.yww.coder.common.utils.incrementer;

/**
 * ID生成接口
 */
public interface IdGenerate {

    /**
     * 获取Long类型的ID
     * 
     * @return ID
     */
    long getLongId();

    /**
     * 获取32位UUID
     * 
     * @return ID
     */
    String get32UUID();
}
