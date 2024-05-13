package com.shop.shoes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productDetail")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private Double price;

    private int quantity;

    private Double discount;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name="product_Id",referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name= "color_Id",referencedColumnName = "id")
    private Color color;
    @ManyToOne
    @JoinColumn(name= "size_Id",referencedColumnName = "id")
    private Size size;

}
