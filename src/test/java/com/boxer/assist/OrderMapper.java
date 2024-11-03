package com.boxer.assist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper
{
    @Mapping(source = "totalAmt", target = "totalPrice")
    OrderBo toBo(OrderDTO orderDTO);
    @Mapping(source = "totalPrice", target = "totalAmt")
    OrderDTO toDTO(OrderBo orderBo);
}
