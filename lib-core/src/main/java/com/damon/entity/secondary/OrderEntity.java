package com.damon.entity.secondary;


import jakarta.persistence.Version;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author ming.hong.du.d
 */
@Data
public class OrderEntity {

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
