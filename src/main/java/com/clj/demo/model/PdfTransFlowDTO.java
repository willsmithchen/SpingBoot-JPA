package com.clj.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * 支付宝微信pdf流水明细
 */
@ApiModel(description = "支付宝微信pdf流水明细表")
public class PdfTransFlowDTO implements Serializable {
    
    private Long id;

    /**
     * 关联wxzfb_pdf_trans_account的id
     */
    @ApiModelProperty(value = "关联wxzfb_pdf_trans_account的id")
    private Long accountId;

    /**
     * 外部系请求编号
     */
    @Size(max = 32)
    @ApiModelProperty(value = "外部系请求编号")
    private String outReqNo;

    /**
     * 交易单号
     */
    @Size(max = 64)
    @ApiModelProperty(value = "交易单号")
    private String transNo;

    /**
     * 交易时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "交易时间")
    private Instant transTime;

    /**
     * 交易类型
     */
    @Size(max = 32)
    @ApiModelProperty(value = "交易类型")
    private String transType;

    /**
     * 收/支/其他
     */
    @Size(max = 32)
    @ApiModelProperty(value = "收/支/其他")
    private String transVariable;

    /**
     * 交易方式
     */
    @Size(max = 32)
    @ApiModelProperty(value = "交易方式")
    private String transMode;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal transAmt;

    /**
     * 交易对方
     */
    @Size(max = 128)
    @ApiModelProperty(value = "交易对方")
    private String opponentName;

    /**
     * 商户单号
     */
    @Size(max = 64)
    @ApiModelProperty(value = "商户单号")
    private String businessNo;

    /**
     * 商品说明
     */
    @Size(max = 64)
    @ApiModelProperty(value = "商品说明")
    private String remark;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOutReqNo() {
        return outReqNo;
    }

    public void setOutReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Instant getTransTime() {
        return transTime;
    }

    public void setTransTime(Instant transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransVariable() {
        return transVariable;
    }

    public void setTransVariable(String transVariable) {
        this.transVariable = transVariable;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfTransFlowDTO)) {
            return false;
        }

        return id != null && id.equals(((PdfTransFlowDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfTransFlowDTO{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", outReqNo='" + getOutReqNo() + "'" +
            ", transNo='" + getTransNo() + "'" +
            ", transTime='" + getTransTime() + "'" +
            ", transType='" + getTransType() + "'" +
            ", transVariable='" + getTransVariable() + "'" +
            ", transMode='" + getTransMode() + "'" +
            ", transAmt=" + getTransAmt() +
            ", opponentName='" + getOpponentName() + "'" +
            ", businessNo='" + getBusinessNo() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
