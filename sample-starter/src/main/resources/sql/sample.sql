#todo你的建表语句
,包含索引
-- 基础订单表结构
CREATE TABLE order_base
(
    order_id       varchar(255)   NOT NULL PRIMARY KEY COMMENT '订单ID，主键自增',
    buyer_id       BIGINT UNSIGNED NOT NULL COMMENT '买家ID',
    seller_id      BIGINT UNSIGNED NOT NULL COMMENT '卖家ID',
    sku_id         BIGINT UNSIGNED NOT NULL COMMENT '商品id',
    amount         BIGINT UNSIGNED NOT NULL COMMENT '商品数量',
    order_status   ENUM('CREATED', 'PAID', 'SHIPPED', 'COMPLETED', 'CANCELED') NOT NULL COMMENT '订单状态',
    order_amount   DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
    pay_state      varchar(3)              DEFAULT NULL COMMENT '支付状态',
    pay_type       varchar(10)             DEFAULT NULL COMMENT '支付方式',
    pay_action     varchar(10)             DEFAULT NULL COMMENT '支付详细方式',
    pay_amount     decimal(12, 2)          DEFAULT NULL COMMENT '支付金额',
    pay_order_no   varchar(50)             DEFAULT NULL COMMENT '支付商户号',
    pay_time       datetime                DEFAULT NULL COMMENT '支付时间',
    coupon_code    varchar(255)            DEFAULT NULL COMMENT '优惠券编码',
    coupon_amount  decimal(12, 2)          DEFAULT NULL COMMENT '优惠券金额',
    refund_amount  decimal(12, 2)          DEFAULT NULL COMMENT '退款金额',
    refund_time    datetime                DEFAULT NULL COMMENT '退款时间',
    state          varchar(3)     NOT NULL DEFAULT '0' COMMENT '状态',
    create_user_id varchar(36)    NOT NULL COMMENT '创建人',
    create_time    timestamp(3)   NOT NULL DEFAULT current_timestamp(3) ON UPDATE current_timestamp (3) COMMENT '订单创建时间',
    modify_user_id varchar(36)    NOT NULL COMMENT '修改人',
    modify_time    timestamp(3)   NOT NULL DEFAULT '0000-00-00 00:00:00.000' COMMENT '订单修改时间',
    -- 其他订单相关字段...
    INDEX          idx_buyer_id (buyer_id) COMMENT '买家ID索引，用于买家查询订单',
    INDEX          idx_seller_id_create_time (seller_id, create_time DESC) COMMENT '卖家ID和创建时间降序索引，用于卖家查询订单'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单基础表';

-- 分库分表设计方案
-- 1. 分库键
-- 考虑到买家和卖家可能都会有大量的订单数据，并且买家查询对实时性要求更高，我们可以选择buyer_id作为分库键。这样，同一个买家的所有订单都会落在同一个数据库上，便于买家实时查询。
--
-- 2. 分表键
-- 分表键可以选择时间相关的字段，如create_time。我们可以按照日期进行分表，每天一张表，这样可以保持表的大小在可控范围内，同时查询时也能减少扫描的数据量。
--
-- 3. 分库分表实现
-- 在实际应用中，可以通过中间件（如Sharding-JDBC、MyCAT等）或者自定义分库分表逻辑来实现。这些中间件通常支持配置分库分表规则，我们可以根据buyer_id的范围来划分数据库，根据create_time的日期来划分表。
--
-- 4. 索引优化
-- 对于买家查询，由于数据已经按buyer_id分库，所以直接在每个库上建立buyer_id索引即可满足实时查询的需求。
-- 对于卖家查询，由于允许秒级延迟，我们可以建立复合索引idx_seller_id_create_time，首先按seller_id分区，然后按create_time降序排列。这样卖家查询时，可以迅速定位到某个卖家的最新订单。
-- 5. 读写分离读写
-- 为了进一步提高性能，可以考虑读写分离读写操作。写操作（如创建订单）只在一个主库上进行，而读操作（如查询订单）可以在多个从库上进行。这样可以分摊读请求的负载，提高系统的吞吐量。


