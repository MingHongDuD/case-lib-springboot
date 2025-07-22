-- 主表，主要存储客户相关的数据

CREATE TABLE `USER`
(
    `USER_ID`                     bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_NAME`                   varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `NICK_NAME`                   varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
    `USER_PASSWORD`               varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `USER_TYPE`                   char(1)     NOT NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表工作人员，2代表管理员',
    `USER_SEX`                    char(1)     NOT NULL DEFAULT '0' COMMENT '用户性别（0男，1女，2未知）',
    `USER_EMAIL`                  varchar(64)          DEFAULT NULL COMMENT '邮箱',
    `USER_Phone_Number`           varchar(32)          DEFAULT NULL COMMENT '手机号',
    `USER_PERSONALIZED_SIGNATURE` varchar(100)         DEFAULT NULL COMMENT '个性签名',
    `CREATE_BY`                   bigint               DEFAULT NULL COMMENT '创建人的用户id',
    `CREATE_TIME`                 datetime             DEFAULT NULL COMMENT '创建时间',
    `UPDATE_BY`                   bigint               DEFAULT NULL COMMENT '更新人',
    `UPDATE_TIME`                 datetime             DEFAULT NULL COMMENT '更新时间',
    `VERSION`                     bigint               DEFAULT NULL COMMENT '版本',
    `DEL_FLAG`                    int                  DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`USER_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

