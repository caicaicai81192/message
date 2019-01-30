package com.ipampas.example.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/30 2:32 PM
 */
public class BaseResponse {


    /**
     * 错误编码
     */
    private Integer errorNo;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 获取错误编码
     *
     * @return 0-处理成功,!0-处理失败
     */
    public Integer getErrorNo() {
        if (null == errorNo) {
            errorNo = 0;
        }
        return errorNo;
    }

    /**
     * 设置错误编码
     *
     * @param errorNo 错误编码
     */
    public void setErrorNo(Integer errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * 获取错误信息
     *
     * @return 成功-StringUtils.EMPTY,失败-其他
     */
    public String getErrorInfo() {
        if (getErrorNo() == 0) {
            errorInfo = StringUtils.EMPTY;
        }
        return errorInfo;
    }

    /**
     * 设置错误信息
     *
     * @param errorInfo 错误信息
     */
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * 判断是否成
     *
     * @return true-失败 false-成功
     */
    public Boolean isSuccess() {
        return !isError();
    }

    /**
     * 是否失败
     *
     * @return true-失败 false-成功
     */
    public Boolean isError() {
        return getErrorNo() != 0;
    }

}
