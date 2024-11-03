package com.boxer.assist;


import com.boxer.assist.gen.JavaClass2DDL;

import java.time.LocalDateTime;

public class OrderDTO {
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 买家ID
     */
    private Long buyerId;
    private Double totalAmt;//订单总金额
    private java.time.LocalDateTime orderDate;
    private Integer status;
    private Boolean isDeleted;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public static void main(String[] args) {
        String s = JavaClass2DDL.generateDDL(OrderDTO.class, "postgresql");
        System.out.println(s);
    }

}
