CREATE TABLE `wxzfb_pdf_trans_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `out_req_no` varchar(32) DEFAULT NULL COMMENT '外部系请求编号',
  `account_name` varchar(32) DEFAULT NULL COMMENT '主体名称',
  `id_card_no` varchar(32) DEFAULT NULL COMMENT '主题唯一识别号',
  `id_type` varchar(32) DEFAULT NULL COMMENT '证件类型ID_CARD_NO, CREDIT_CODE, REG_NO, OTHER',
  `data_source` varchar(32) DEFAULT NULL COMMENT 'pdf文件来源',
  `online_name` varchar(64) DEFAULT NULL COMMENT '网络账户名',
  `start_time` datetime COMMENT '开始时间',
  `end_time` datetime COMMENT '结束时间',
  `bill_number` varchar(64) DEFAULT NULL COMMENT 'pdf流水编号',
  `account_state` int(11) DEFAULT NULL COMMENT '账户状态 0-作废；1-正常',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COMMENT='支付宝微信pdf流水账户表';

CREATE TABLE `wxzfb_pdf_trans_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL COMMENT '关联wxzfb_pdf_trans_account的id',
  `out_req_no` varchar(32) DEFAULT NULL COMMENT '外部系请求编号',
  `trans_no` varchar(128) DEFAULT NULL COMMENT '交易单号',
  `trans_time` datetime COMMENT '交易时间',
  `trans_type` varchar(32) DEFAULT NULL COMMENT '交易类型',
  `trans_variable` varchar(32) DEFAULT NULL COMMENT '收/支/其他',
  `trans_mode` varchar(32) DEFAULT NULL COMMENT '交易方式',
  `trans_amt` decimal(16,4) DEFAULT NULL COMMENT '交易金额',
  `opponent_name` varchar(128) DEFAULT NULL COMMENT '交易对方',
  `business_no` varchar(64) DEFAULT NULL COMMENT '商户单号',
  `remark` varchar(128) DEFAULT NULL COMMENT '商品说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=271667 DEFAULT CHARSET=utf8mb4 COMMENT='支付宝微信pdf流水明细表';