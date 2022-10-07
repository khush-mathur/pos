package com.increff.pos.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Product")
public class ProductPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)        //why not Table
    private Integer id;
    @Column(nullable = false ,unique = true)
    private String barcode;
    @Column(nullable = false)
    private Integer brandCategory;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double mrp;
}
