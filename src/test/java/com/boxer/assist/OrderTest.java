package com.boxer.assist;


import com.boxer.assist.gen.JavaClass2DDL;

public class OrderTest {
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

    public static void main(String[] args) {
        String s = JavaClass2DDL.generateDDL(OrderTest.class, "postgresql");
        System.out.println(s);
    }

}
