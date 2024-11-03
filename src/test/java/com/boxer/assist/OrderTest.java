package com.boxer.assist;

import org.mapstruct.factory.Mappers;

public class OrderTest {
    public static void main(String[] args) {
        OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

        OrderDTO dto = new OrderDTO();
        dto.setBuyerId(101L);
        dto.setOrderId(10001L);
        dto.setTotalAmt( Double.parseDouble("120.55"));

        OrderBo bo = mapper.toBo(dto);
        System.out.println("BO orderId: " + bo.getOrderId()); // 输出: John Doe
        System.out.println("BO buyerId: " + bo.getBuyerId());           // 输出: 30
        System.out.println("BO totalPrice: " + bo.getTotalPrice());   // 输出: john.doe@example.com

    }
}
