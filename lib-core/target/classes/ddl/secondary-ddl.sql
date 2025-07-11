-- 创建订单表，适配 PostgreSQL 语法，遵循最佳实践
CREATE TABLE secondory.orders
(
    -- 主键，自增序列
    order_id         BIGSERIAL PRIMARY KEY,

    -- 客户ID
    customer_id      VARCHAR(64)    NOT NULL,

    -- 订单状态
    order_status     VARCHAR(64)    NOT NULL,

    -- 订单总金额，默认为 0.00
    total_amount     DECIMAL(10, 2) NOT NULL  DEFAULT 0.00,

    -- 配送地址，使用 JSONB 存储，默认为空 JSON 对象
    shipping_address JSONB          NOT NULL  DEFAULT '{}',

    -- 额外信息
    notes            TEXT,

    -- 创建人的用户ID
    create_by        BIGINT,

    -- 创建时间
    create_time      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- 更新人的用户ID
    update_by        BIGINT,

    -- 更新时间
    update_time      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- 版本号
    version          BIGINT,

    -- 删除标志（0代表未删除，1代表已删除）
    del_flag         INTEGER                  DEFAULT 0 CHECK (del_flag IN (0, 1))
);

-- 为表和字段添加中文注释
COMMENT ON TABLE orders IS '订单表';
COMMENT ON COLUMN orders.order_id IS '主键，自增序列';
COMMENT ON COLUMN orders.customer_id IS '客户ID';
COMMENT ON COLUMN orders.order_status IS '订单状态';
COMMENT ON COLUMN orders.total_amount IS '订单总金额，默认 0.00';
COMMENT ON COLUMN orders.shipping_address IS '配送地址，使用 JSONB 存储，默认为空 JSON 对象';
COMMENT ON COLUMN orders.notes IS '额外信息';
COMMENT ON COLUMN orders.create_by IS '创建人的用户ID';
COMMENT ON COLUMN orders.create_time IS '创建时间';
COMMENT ON COLUMN orders.update_by IS '更新人的用户ID';
COMMENT ON COLUMN orders.update_time IS '更新时间';
COMMENT ON COLUMN orders.version IS '版本号';
COMMENT ON COLUMN orders.del_flag IS '删除标志（0代表未删除，1代表已删除）';