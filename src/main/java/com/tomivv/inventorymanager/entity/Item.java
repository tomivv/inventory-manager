package com.tomivv.inventorymanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Item {
    @Id
    @SequenceGenerator(
        name = "item_sequence",
        sequenceName = "item_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "item_sequence"
    )
    private Long id;
    private String name;
    private String location;
    private Double weight;

    public Item() {
    }

    public Item(Long id,
                    String name,
                    String location,
                    Double weight) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.weight = weight;
    }

    public Item(String name,
                    String location,
                    Double weight) {
        this.name = name;
        this.location = location;
        this.weight = weight;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", weight='" + getWeight() + "'" +
            "}";
    }

}
