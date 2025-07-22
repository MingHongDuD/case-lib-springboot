-- 从表，主要存储primary表的数据，实现高可用
-- 创建订单表，适配 PostgreSQL 语法，遵循最佳实践
CREATE TABLE postgres.order
(
    order_id         BIGSERIAL PRIMARY KEY,
    customer_id      VARCHAR(64)    NOT NULL,
    order_status     VARCHAR(64)    NOT NULL,
    total_amount     DECIMAL(10, 2) NOT NULL  DEFAULT 0.00,
    shipping_address JSONB          NOT NULL  DEFAULT '{}',
    notes            TEXT,
    create_by        BIGINT,
    create_time      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_by        BIGINT,
    update_time      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    version          BIGINT,
    del_flag         INTEGER
);

COMMENT ON TABLE order IS '订单表';
COMMENT ON COLUMN order.order_id IS '主键，自增序列';
COMMENT ON COLUMN order.customer_id IS '客户ID';
COMMENT ON COLUMN order.order_status IS '订单状态';
COMMENT ON COLUMN order.total_amount IS '订单总金额，默认 0.00';
COMMENT ON COLUMN order.shipping_address IS '配送地址，使用 JSONB 存储，默认为空 JSON 对象';
COMMENT ON COLUMN order.notes IS '额外信息';
COMMENT ON COLUMN order.create_by IS '创建人的用户ID';
COMMENT ON COLUMN order.create_time IS '创建时间';
COMMENT ON COLUMN order.update_by IS '更新人的用户ID';
COMMENT ON COLUMN order.update_time IS '更新时间';
COMMENT ON COLUMN order.version IS '版本号';
COMMENT ON COLUMN order.del_flag IS '删除标志（0代表未删除，1代表已删除）';