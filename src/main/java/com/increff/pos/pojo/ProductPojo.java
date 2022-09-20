package com.increff.pos.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Product")
public class ProductPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)        //why not Table
    private Integer id;
    private String barcode;
    private Integer brandCategory;
    private String name;
    private Double mrp;
}
