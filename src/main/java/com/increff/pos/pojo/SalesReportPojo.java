package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class SalesReportPojo {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String brand;
    private String category;
}
