package com.damon.entity.third;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.persistence.Version;
import lombok.Data;

import java.time.LocalTime;

/**
 * 订单实体类
 *
 * @author damon du/minghongdud
 */
@Data
@TableName("orders")
public class OrderEntity {

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private Long customerId;

    private String orderStatus;

    private Double totalAmount;

    private String shippingAddress;

    private String notes;

    private Long createBy;

    private Long updateBy;

    private LocalTime createTime;

    private LocalTime updateTime;

    private @Version Long version;

    private Integer delFlag;

}
