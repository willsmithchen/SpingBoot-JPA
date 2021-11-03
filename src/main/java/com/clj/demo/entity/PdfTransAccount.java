package com.clj.demo.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * 支付宝微信pdf流水账户表
 */
@Entity
@Table(name = "wxzfb_pdf_trans_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PdfTransAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 外部系请求编号
     */
    @Size(max = 32)
    @Column(name = "out_req_no", length = 32)
    private String outReqNo;

    /**
     * 主体名称
     */
    @Size(max = 32)
    @Column(name = "account_name", length = 32)
    private String accountName;

    /**
     * 主题唯一识别号
     */
    @Size(max = 32)
    @Column(name = "id_card_no", length = 32)
    private String idCardNo;

    /**
     * 证件类型ID_CARD_NO, CREDIT_CODE, REG_NO, OTHER
     */
    @Size(max = 32)
    @Column(name = "id_type", length = 32)
    private String idType;

    /**
     * pdf文件来源
     */
    @Size(max = 32)
    @Column(name = "data_source", length = 32)
    private String dataSource;

    /**
     * 网络账户名
     */
    @Size(max = 64)
    @Column(name = "online_name", length = 64)
    private String onlineName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Instant startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Instant endTime;

    /**
     * pdf流水编号
     */
    @Size(max = 64)
    @Column(name = "bill_number", length = 64)
    private String billNumber;

    /**
     * 账户状态 0-作废；1-正常
     */
    @Column(name = "account_state")
    private Integer accountState;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Instant createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutReqNo() {
        return outReqNo;
    }

    public PdfTransAccount outReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
        return this;
    }

    public void setOutReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public PdfTransAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public PdfTransAccount idCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
        return this;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdType() {
        return idType;
    }

    public PdfTransAccount idType(String idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public PdfTransAccount dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOnlineName() {
        return onlineName;
    }

    public PdfTransAccount onlineName(String onlineName) {
        this.onlineName = onlineName;
        return this;
    }

    public void setOnlineName(String onlineName) {
        this.onlineName = onlineName;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public PdfTransAccount startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public PdfTransAccount endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public PdfTransAccount billNumber(String billNumber) {
        this.billNumber = billNumber;
        return this;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public PdfTransAccount accountState(Integer accountState) {
        this.accountState = accountState;
        return this;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public PdfTransAccount createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfTransAccount)) {
            return false;
        }
        return id != null && id.equals(((PdfTransAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfTransAccount{" +
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
