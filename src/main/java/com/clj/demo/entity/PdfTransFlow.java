package com.clj.demo.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * 支付宝微信pdf流水明细表
 */
@Entity
@Table(name = "wxzfb_pdf_trans_flow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PdfTransFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Long id;

    /**
     * 关联wxzfb_pdf_trans_account的id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 外部系请求编号
     */
    @Size(max = 32)
    @Column(name = "out_req_no", length = 32)
    private String outReqNo;

    /**
     * 交易单号
     */
    @Size(max = 128)
    @Column(name = "trans_no", length = 128)
    private String transNo;

    /**
     * 交易时间
     */
    @Column(name = "trans_time")
    private Instant transTime;

    /**
     * 交易类型
     */
    @Size(max = 32)
    @Column(name = "trans_type", length = 32)
    private String transType;

    /**
     * 收/支/其他
     */
    @Size(max = 32)
    @Column(name = "trans_variable", length = 32)
    private String transVariable;

    /**
     * 交易方式
     */
    @Size(max = 32)
    @Column(name = "trans_mode", length = 32)
    private String transMode;

    /**
     * 交易金额
     */
    @Column(name = "trans_amt", precision = 21, scale = 2)
    private BigDecimal transAmt;

    /**
     * 交易对方
     */
    @Size(max = 128)
    @Column(name = "opponent_name", length = 128)
    private String opponentName;

    /**
     * 商户单号
     */
    @Size(max = 64)
    @Column(name = "business_no", length = 64)
    private String businessNo;

    /**
     * 商品说明
     */
    @Size(max = 128)
    @Column(name = "remark", length = 128)
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public PdfTransFlow accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOutReqNo() {
        return outReqNo;
    }

    public PdfTransFlow outReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
        return this;
    }

    public void setOutReqNo(String outReqNo) {
        this.outReqNo = outReqNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public PdfTransFlow transNo(String transNo) {
        this.transNo = transNo;
        return this;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Instant getTransTime() {
        return transTime;
    }

    public PdfTransFlow transTime(Instant transTime) {
        this.transTime = transTime;
        return this;
    }

    public void setTransTime(Instant transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public PdfTransFlow transType(String transType) {
        this.transType = transType;
        return this;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransVariable() {
        return transVariable;
    }

    public PdfTransFlow transVariable(String transVariable) {
        this.transVariable = transVariable;
        return this;
    }

    public void setTransVariable(String transVariable) {
        this.transVariable = transVariable;
    }

    public String getTransMode() {
        return transMode;
    }

    public PdfTransFlow transMode(String transMode) {
        this.transMode = transMode;
        return this;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public PdfTransFlow transAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
        return this;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public PdfTransFlow opponentName(String opponentName) {
        this.opponentName = opponentName;
        return this;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public PdfTransFlow businessNo(String businessNo) {
        this.businessNo = businessNo;
        return this;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getRemark() {
        return remark;
    }

    public PdfTransFlow remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdfTransFlow)) {
            return false;
        }
        return id != null && id.equals(((PdfTransFlow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdfTransFlow{" +
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
