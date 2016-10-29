CREATE TABLE user_main (
  /*用户id*/
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
  
  /*微信openId*/
  wechat VARCHAR(32) DEFAULT NULL COMMENT '微信openId',
  
  /*微信昵称*/
  wechat_nickname VARCHAR(32) DEFAULT NULL COMMENT '微信昵称',
  
  /*QQ*/
  qq varchar(20) DEFAULT NULL COMMENT 'QQ',
  
  /*已绑定的手机号码,要求唯一*/
  phone VARCHAR(11) NOT NULL COMMENT '已绑定的手机号码',
  
  /*密码*/
  password VARCHAR(32) NOT NULL COMMENT '密码',
  
  /*注册时间*/
  register_time TIMESTAMP NOT NULL COMMENT '注册时间',
  
  /*注册ip*/
  register_ip INT UNSIGNED NOT NULL COMMENT '激活ip',
  
  /*用户状态，1正常，2冻结*/
  status SMALLINT UNSIGNED DEFAULT 1 COMMENT '用户状态，1正常，2冻结',
  
  /*用户等级*/
  level SMALLINT UNSIGNED DEFAULT 1 COMMENT '用户等级，1初级',
  
  /*该用户的邀请人*/
  invitor INT UNSIGNED DEFAULT NULL COMMENT '该用户的邀请人',
  
  /*邀请码*/
  inviter_code varchar(64) DEFAULT NULL COMMENT '邀请码',
  
  /*用户来源*/
  source SMALLINT UNSIGNED DEFAULT 1 COMMENT '用户来源，1 H5注册',
  
  UNIQUE INDEX user_main_phone (phone),
  UNIQUE INDEX user_main_inviter_code (inviter_code)
);

/*用户个人信息表*/
CREATE TABLE user_personal_info (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*主表用户id*/
  user_id INT UNSIGNED NOT NULL,
  
  /*真实姓名*/
  real_name VARCHAR(30) DEFAULT NULL COMMENT '真实姓名',
  
  /*性别，女：false；男：true*/
  gender BOOLEAN DEFAULT NULL COMMENT '性别，女：false；男：true',
  
  /*身份证号*/
  id_card VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  
  /*用户身份证认证状态*/
  id_card_status TINYINT DEFAULT 0 COMMENT '用户身份证认证状态',
  
  /*身份证正面照*/
  id_card_face VARCHAR(100) DEFAULT NULL COMMENT '身份证正面照',
  
  /*身份证反面照*/
  id_card_back VARCHAR(100) DEFAULT NULL COMMENT '份证反面照',
  
  /*认证次数*/
  verify_time tinyint DEFAULT 0 COMMENT '认证次数',
 
  CONSTRAINT fk_user_personal_info_user_id FOREIGN KEY (user_id) REFERENCES user_main (id)
);

/*用户需求表*/
CREATE TABLE user_requirement (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*主表用户id*/
  user_id INT UNSIGNED NOT NULL,
  
  /*提交时间*/
  submit_time TIMESTAMP NOT NULL COMMENT '提交时间',
  
  /*提交内容*/
  submit_content varchar(500) NOT NULL COMMENT '提交内容',
  
   /*已绑定的手机号码,要求唯一*/
  phone VARCHAR(11) NULL COMMENT '已绑定的手机号码',
  
  CONSTRAINT fk_user_requirement_user_id FOREIGN KEY (user_id) REFERENCES user_main (id)
);

/*学生点评表*/
CREATE TABLE student_comment (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*主表用户id*/
  user_id INT UNSIGNED NOT NULL,
  
   /*课程id*/
  lesson_id INT UNSIGNED NOT NULL,
  
   /*老师id*/
  teacher_id INT UNSIGNED NOT NULL,
  
  /*订单id*/
  lesson_order_id INT UNSIGNED NOT NULL,
  
  /*已绑定的手机号码,要求唯一*/
  phone VARCHAR(11) NOT NULL COMMENT '已绑定的手机号码',
  
  /*提交时间*/
  submit_time TIMESTAMP NOT NULL COMMENT '提交时间',
  
  /*提交内容*/
  submit_content VARCHAR(100) NOT NULL COMMENT '提交内容',
  
   /*学生评分*/
  grade DECIMAL(14,2) UNSIGNED NOT NULL COMMENT '学生评分'
);

/*老师信息表*/
CREATE TABLE teacher (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*真实姓名*/
  real_name VARCHAR(30) DEFAULT NULL COMMENT '真实姓名',
  
  /*性别，女：false；男：true*/
  gender BOOLEAN NOT NULL COMMENT '性别，女：false；男：true',
  
  /*身份证号*/
  id_card VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  
  /*昵称*/
  nick_name VARCHAR(20) DEFAULT NULL COMMENT '昵称',
  
  /*主授课地点*/
  address_main VARCHAR(10) DEFAULT NULL COMMENT '主授课地点',
  
  /*次授课地点*/
  address_secondary_1 VARCHAR(10) DEFAULT NULL COMMENT '次授课地点',
  
  /*次授课地点*/
  address_secondary_2 VARCHAR(10) DEFAULT NULL COMMENT '次授课地点',
  
  /*次授课地点*/
  address_secondary_3 VARCHAR(10) DEFAULT NULL COMMENT '次授课地点',
  
  /*次授课地点*/
  address_secondary_4 VARCHAR(10) DEFAULT NULL COMMENT '次授课地点',
  
  /*主授课行业*/
  industry_main VARCHAR(20) DEFAULT NULL COMMENT '主授课行业',
  
  /*次授课行业*/
  industry_secondary_1 VARCHAR(20) DEFAULT NULL COMMENT '次授课行业',
  
  /*次授课行业*/
  industry_secondary_2 VARCHAR(20) DEFAULT NULL COMMENT '次授课行业',
  
  /*次授课行业*/
  industry_secondary_3 VARCHAR(20) DEFAULT NULL COMMENT '次授课行业',
  
  /*一级岗位*/
  major_type VARCHAR(10) DEFAULT NULL COMMENT '一级岗位',
  
  /*二级岗位*/
  minor_type VARCHAR(10) DEFAULT NULL COMMENT '二级岗位',
  
  /*目前就职公司*/
  company VARCHAR(20) NOT NULL COMMENT '目前就职公司',
  
  /*目前职位*/
  title VARCHAR(20) NOT NULL COMMENT '目前职位',
  
  /*评分等级*/
  degree DECIMAL(14,2) NOT NULL COMMENT '评分等级',
  
  /*电话*/
  phone VARCHAR(11) NOT NULL COMMENT '电话',
  
  /*微信*/
  wechat VARCHAR(32) DEFAULT NULL COMMENT '微信',
  
  /*QQ*/
  qq VARCHAR(10) DEFAULT NULL COMMENT 'QQ',
  
  /*简介*/
  introduction VARCHAR(500) NOT NULL COMMENT '简介',
  
  /*备注*/
  comment VARCHAR(200) NOT NULL COMMENT '备注',
  
  /*推荐*/
  recommend BOOLEAN DEFAULT FALSE COMMENT '推荐,false为不推荐，true为推荐',
  
   /*结算方式*/
  remittance TINYINT DEFAULT 1 COMMENT '结算方式,1为周结，2为月结',
  
  /*结算费率*/
  rate DECIMAL(14,2) DEFAULT 0 COMMENT '结算费率',
  
  /*银行*/
  bank VARCHAR(20) DEFAULT NULL COMMENT '银行',
  
  /*银行卡号*/
  card_no VARCHAR(20) DEFAULT NULL COMMENT '银行卡号',
  
  /*是否上架*/
  status BOOLEAN DEFAULT FALSE NOT NULL COMMENT '是否上架,false为下架，true为上架',
  
  /*上线时间*/
  add_time TIMESTAMP NOT NULL COMMENT '上线时间',
  
  /*上架下架备注*/
  status_comment VARCHAR(100) DEFAULT NULL COMMENT '上架下架备注'
);

/*主岗位*/
CREATE TABLE major_position(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*主岗位名称*/
  position_name VARCHAR(20) NOT NULL COMMENT '主岗位名称'
)

/*次岗位*/
CREATE TABLE minor_position(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*主岗位id*/
  major_id INT UNSIGNED NOT NULL,
  
  /*主岗位名称*/
  major_position_name VARCHAR(20) NOT NULL COMMENT '主岗位名称',
  
  /*次岗位名称*/
  position_name VARCHAR(20) NOT NULL COMMENT '次岗位名称',
  
  CONSTRAINT fk_minor_position FOREIGN KEY (major_id) REFERENCES major_position (id)
)

/*适用人群*/
CREATE TABLE suitable_crowd(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*人群名称*/
  crowd_name VARCHAR(20) NOT NULL COMMENT '人群名称'
)

/*课程*/
CREATE TABLE lesson(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*老师id*/
  teacher_id INT UNSIGNED NOT NULL,
  
  /*课程名称*/
  lesson_name VARCHAR(50) NOT NULL COMMENT '课程名称',
  
  /*课程时长*/
  lesson_time DECIMAL(14,1) NOT NULL COMMENT '课程时长',
  
  /*课程价格*/
  cost DECIMAL(14,2) NOT NULL COMMENT '课程价格',
  
  /*上课时间说明*/
  lesson_time_comment VARCHAR(100) NOT NULL COMMENT '上课时间说明',
  
   /*上课地点说明*/
  lesson_address_comment VARCHAR(100) NOT NULL COMMENT '上课地点说明',
  
  /*上课内容*/
  lesson_content VARCHAR(300) NOT NULL COMMENT '上课内容',
  
  /*评分等级*/
  degree DECIMAL(14,2) NOT NULL COMMENT '评分等级',
  
  /*备注*/
  status_comment DEFAULT NULL VARCHAR(100) NOT NULL COMMENT '备注',
  
  /*推荐*/
  recommend BOOLEAN DEFAULT FALSE COMMENT '推荐,false为不推荐，true为推荐',
  
  /*是否上架*/
  status BOOLEAN DEFAULT FALSE COMMENT '是否上架,false为下架，true为上架',
  
  /*适用人群*/
  crowd_property VARCHAR(20) NOT NULL COMMENT '适用人群',
  
  /*适用岗位*/
  position_property VARCHAR(100) NOT NULL COMMENT '适用岗位',
  
  /*主适用行业*/
  industry_main VARCHAR(20) DEFAULT NULL COMMENT '主适用行业',
  
  /*次适用行业*/
  industry_secondary_1 VARCHAR(20) DEFAULT NULL COMMENT '次适用行业',
  
  /*次适用行业*/
  industry_secondary_2 VARCHAR(20) DEFAULT NULL COMMENT '次适用行业',
  
  /*次适用行业*/
  industry_secondary_3 VARCHAR(20) DEFAULT NULL COMMENT '次适用行业',
  
  /*成交次数*/
  deal_count SMALLINT DEFAULT 0 COMMENT '成交次数',

  CONSTRAINT fk_lesson FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

/*订单*/
CREATE TABLE lesson_order (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  
  /*课程id*/
  lesson_id INT UNSIGNED NOT NULL,
  
  /*学生id*/
  user_id INT UNSIGNED NOT NULL,
  
  /*上课时间*/
  lesson_time VARCHAR(80) DEFAULT NULL,
  
  /*上课地点*/
  lesson_address VARCHAR(80) DEFAULT NULL,
  
  /*订单状态*/
  status TINYINT DEFAULT 1 COMMENT '订单状态',
  
  /*下单时间*/
  order_time DATETIME NOT NULL COMMENT '下单时间',
  
  /*付款时间*/
  pay_time DATETIME DEFAULT NULL COMMENT '付款时间',
  
  /*点评时间*/
  evaluate_time DATETIME DEFAULT NULL COMMENT '点评时间',
  
  /*约课时间*/
  communicate_time DATETIME DEFAULT NULL COMMENT '约课时间',
  
  /*完成时间*/
  complete_time DATETIME DEFAULT NULL COMMENT '完成时间',
  
  /*取消时间*/
  cancel_time DATETIME DEFAULT NULL COMMENT '取消时间',
  
  /*取消原因*/
  cancel_reason VARCHAR(80) DEFAULT NULL COMMENT '取消原因',
  
  /*取消方式*/
  cancel_type TINYINT DEFAULT 0 COMMENT '取消原因',
  
  /*用户是否在前台点击了取消按钮*/
  in_cancel_status TINYINT DEFAULT 0 COMMENT '用户是否在前台点击了取消按钮',
  
  /*原课程金额*/
  lesson_amount DECIMAL(14,2) NOT NULL COMMENT '原课程金额',
  
  /*退款金额*/
  refund_amount DECIMAL(14,2) DEFAULT 0 COMMENT '退款金额',
  
  /*退款比率*/
  refund_rate DECIMAL(14,2) DEFAULT 0 COMMENT '退款比率',
  
  /*退款情况*/
  refund_status TINYINT DEFAULT 0 COMMENT '退款情况',
  
  /*退款审核备注*/
  refund_comment VARCHAR(80) DEFAULT NULL,
  
  /*订单取消审核备注*/
  order_cancel_comment VARCHAR(80) DEFAULT NULL,
  
   /*学生下订单时的学习建议*/
  lesson_suggestion VARCHAR(200) DEFAULT NULL,
  
  CONSTRAINT fk_order_user_id FOREIGN KEY (user_id) REFERENCES user_main (id),
  CONSTRAINT fk_order_lesson_id FOREIGN KEY (lesson_id) REFERENCES lesson (id)
)


/*用户登陆失败次数表*/
CREATE TABLE user_login_failure (
  user_id INT UNSIGNED NOT NULL COMMENT '用户id',
  attempt_times int(10) unsigned NOT NULL DEFAULT '1' COMMENT '尝试登录失败次数',
  last_attemped timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上一次尝试时间戳',
  last_locked datetime DEFAULT NULL COMMENT '上一次锁定时间',
  last_login_ip int(10) unsigned DEFAULT NULL COMMENT '上一次尝试登录ip',
  PRIMARY KEY (user_id),
  CONSTRAINT fk_user_login_failure_userId FOREIGN KEY (user_id) REFERENCES user_main(user_id)
);

/*用户短信发送时间间隔表*/
CREATE TABLE mobile_message_interval (
  /*ID*/
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
  /*用户IP*/
  ip BIGINT NOT NULL COMMENT '用户IP',
  /*此IP发送短信次数*/
  count INTEGER NOT NULL COMMENT '此IP发送短信次数',
  /*上次发送时间*/
  send_time datetime NOT NULL COMMENT '上次发送时间',
  
  UNIQUE KEY unique_key_mobile_message_interval_ip(ip)
)

CREATE TABLE manager (
  /*用户id*/
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
  
  user_name VARCHAR(32) NOT NULL COMMENT '用户名',
  
  /*密码*/
  password VARCHAR(32) NOT NULL COMMENT '密码'
)

CREATE TABLE customer_service (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
  
  /*已绑定的手机号码,要求唯一*/
  phone VARCHAR(11) NOT NULL COMMENT '已绑定的手机号码',
  
  UNIQUE INDEX customer_service_phone (phone)
)

CREATE TABLE wechat_pay_result (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  
  /*返回状态码*/
  return_code VARCHAR(16) NOT NULL COMMENT '返回状态码',
  
  /*返回信息*/
  return_msg VARCHAR(128) DEFAULT NULL COMMENT '返回信息',
  
  /*公众账号ID*/
  app_id VARCHAR(32) DEFAULT NULL COMMENT '公众账号ID',
  
  /*商户号*/
  mch_id VARCHAR(32) DEFAULT NULL COMMENT '商户号',
  
  /*设备号*/
  device_info VARCHAR(32) DEFAULT NULL COMMENT '设备号',
  
  /*随机字符串*/
  nonce_str VARCHAR(32) DEFAULT NULL COMMENT '随机字符串',
  
  /*签名*/
  sign VARCHAR(32) DEFAULT NULL COMMENT '签名',
  
  /*业务结果*/
  result_code VARCHAR(16) DEFAULT NULL COMMENT '业务结果',
  
  /*错误代码*/
  err_code VARCHAR(32) DEFAULT NULL COMMENT '错误代码',
  
  /*错误代码描述*/
  err_code_des VARCHAR(128) DEFAULT NULL COMMENT '错误代码描述',
  
  /*用户标识*/
  open_id VARCHAR(128) DEFAULT NULL COMMENT '用户标识',
  
  /*是否关注公众账号*/
  is_subscribe VARCHAR(1) DEFAULT NULL COMMENT '是否关注公众账号',
  
  /*交易类型*/
  trade_type VARCHAR(16) DEFAULT NULL COMMENT '交易类型',
  
  /*付款银行*/
  bank_type VARCHAR(16) DEFAULT NULL COMMENT '付款银行',
  
  /*银行名称*/
  bank_name VARCHAR(20) DEFAULT NULL COMMENT '付款银行',
  
  /*总金额*/
  total_fee INTEGER DEFAULT NULL COMMENT '总金额',
  
  /*货币种类*/
  fee_type VARCHAR(8) DEFAULT NULL COMMENT '货币种类',
  
  /*现金支付金额*/
  cash_fee INTEGER DEFAULT NULL COMMENT '现金支付金额',
  
  /*现金支付货币种类*/
  cash_fee_type VARCHAR(16) DEFAULT NULL COMMENT '现金支付货币种类',
  
  /*微信支付订单号*/
  transaction_id VARCHAR(32) DEFAULT NULL COMMENT '微信支付订单号',
  
  /*商户订单号*/
  out_trade_no VARCHAR(32) DEFAULT NULL COMMENT '商户订单号',
  
  /*商家数据包*/
  attach VARCHAR(128) DEFAULT NULL COMMENT '商家数据包',
  
  /*支付完成时间*/
  time_end VARCHAR(14) DEFAULT NULL COMMENT '支付完成时间',
  
  key wechat_pay_result_trade_no (out_trade_no)
)