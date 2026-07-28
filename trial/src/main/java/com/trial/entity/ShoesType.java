package com.trial.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shoes_type")
public class ShoesType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "type_name")
    private String typeName;

    @OneToMany(mappedBy = "shoesType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shoes> shoesList = new ArrayList<>();

    public ShoesType() {
    }

    public ShoesType(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
