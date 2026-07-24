package com.app.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponse {
private  long id;
    private  String name;
    private  String description;
    private  String imagerurl;
    private  String category;
    private BigDecimal price;
    private  Integer stockquantity;

}
