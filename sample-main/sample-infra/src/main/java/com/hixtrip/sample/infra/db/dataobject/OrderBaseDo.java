package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "order_base", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderBaseDo {
    @TableId
    private String orderId; // 订单ID，主键自增
    private Long buyerId; // 买家ID
    private Long sellerId; // 卖家ID
    private Long skuId; // 商品id
    private Long amount; // 商品数量
    private String orderStatus; // 订单状态
    private BigDecimal orderAmount; // 订单金额
    private String payState; // 支付状态
    private String payType; // 支付方式
    private String payAction; // 支付详细方式
    private BigDecimal payAmount; // 支付金额
    private String payOrderNo; // 支付商户号
    private Date payTime; // 支付时间
    private String couponCode; // 优惠券编码
    private BigDecimal couponAmount; // 优惠券金额
    private BigDecimal refundAmount; // 退款金额
    private Date refundTime; // 退款时间
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private String state; // 状态
    private String createUserId; // 创建人
    private Date createTime; // 订单创建时间
    private String modifyUserId; // 修改人
    private Date modifyTime; // 订单修改时间

}
