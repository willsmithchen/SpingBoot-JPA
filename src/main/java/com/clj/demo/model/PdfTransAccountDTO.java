package com.clj.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * 支付宝微信pdf流水账户
 */
@ApiModel(description = "支付宝微信pdf流水账户表")
public class PdfTransAccountDTO implements Serializable {
    
    private Long id;

    /**
     * 外部系请求编号
     */
    @Size(max = 32)
    @ApiModelProperty(value = "外部系请求编号")
    private String outReqNo;

    /**
     * 主体名称
     */
    @Size(max = 32)
    @ApiModelProperty(value = "主体名称")
    private String accountName;

    /**
     * 主题唯一识别号
     */
    @Size(max = 32)
    @ApiModelProperty(value = "主题唯一识别号")
    private String idCardNo;

    /**
     * 证件类型ID_CARD_NO, CREDIT_CODE, REG_NO, OTHER
     */
    @Size(max = 32)
    @ApiModelProperty(value = "证件类型ID_CARD_NO, CREDIT_CODE, REG_NO, OTHER")
    private String idType;

    /**
     * pdf文件来源
     */
    @Size(max = 32)
    @ApiModelProperty(value = "pdf文件来源")
    private String dataSource;

    /**
     * 网络账户名
     */
    @Size(max = 64)
    @ApiModelProperty(value = "网络账户名")
    private String onlineName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Instant endTime;

    /**
     * pdf流水编号
     */
    @Size(max = 64)
    @ApiModelProperty(value = "pdf流水编号")
    private String billNumber;

    /**
     * 账户状态 0-作废；1-正常
     */
    @ApiModelProperty(value = "账户状态 0-作废；1-正常")
    private Integer accountState;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant createTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutReqNo() {
        return outReqNo;
    }

    public void setOutReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOnlineName() {
        return onlineName;
    }

    public void setOnlineName(String onlineName) {
        this.onlineName = onlineName;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfTransAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((PdfTransAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfTransAccountDTO{" +
            "id=" + getId() +
            ", outReqNo='" + getOutReqNo() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", idCardNo='" + getIdCardNo() + "'" +
            ", idType='" + getIdType() + "'" +
            ", dataSource='" + getDataSource() + "'" +
            ", onlineName='" + getOnlineName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", billNumber='" + getBillNumber() + "'" +
            ", accountState=" + getAccountState() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
