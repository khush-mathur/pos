package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData {
    private Integer id;
    private String dateTime;
    private String status;
}
