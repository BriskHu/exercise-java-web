
/*
设备信息表
 */
CREATE TABLE t_device_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  alias varchar(50) DEFAULT NULL COMMENT '设备别名',
  dn varchar(20) NOT NULL COMMENT '设备编码',
  pin varchar(32) NOT NULL COMMENT '设备pin码',
  mac varchar(64) DEFAULT NULL COMMENT '设备mac地址',
  location varchar(35) DEFAULT NULL COMMENT '设备位置,值的格式为:(经度,纬度)。注意是英文括号、逗号。',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_dn (dn) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';






