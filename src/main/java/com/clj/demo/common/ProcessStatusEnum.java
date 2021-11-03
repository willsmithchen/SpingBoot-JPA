package com.clj.demo.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * The ProcessStatusEnum enumeration.
 */
public enum ProcessStatusEnum {
    /**
     * 待处理
     */
    PENDING,
    /**
     * 处理中
     */
    PROCESSING,
    /**
     * 处理完成
     */
    DONE,
    /**
     * 处理失败
     */
    FAILED,
    /**
     * 异常
     */
    EXCEPTION,
    /**
     * 循环处理结束
     */
    END,
    ;

    private static final List<ProcessStatusEnum> END_STATUS = Lists.newArrayList(DONE, FAILED, EXCEPTION, END);
    private static final List<ProcessStatusEnum> SUCC_STATUS_ENUMS = Lists.newArrayList(DONE, END);

    private static final List<ProcessStatusEnum> PROCESS_STATUS_ENUMS = Lists.newArrayList(PROCESSING, PENDING);

    private static final List<ProcessStatusEnum> FAIL_STATUS_ENUMS = Lists.newArrayList(FAILED, EXCEPTION);

    public static final List<String> isContainsEnum = Lists.newArrayList("DONE", "FAILED", "EXCEPTION", "END");

    public static boolean isEndStatus(ProcessStatusEnum status) {
        return END_STATUS.stream().anyMatch(e -> e.equals(status));
    }

    public static List<ProcessStatusEnum> transProcessStatusEnums(ProcessStatusEnum status) {
        switch (status) {
            case DONE:
            case END:
                return SUCC_STATUS_ENUMS;
            case EXCEPTION:
            case FAILED:
                return FAIL_STATUS_ENUMS;
            case PROCESSING:
            case PENDING:
                return PROCESS_STATUS_ENUMS;
            default:
                return Lists.newArrayList();
        }
    }


}
