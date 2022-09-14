package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalesReportData {
    private String category;
    private Integer quantity;
    private Double revenue;
}
