package com.shiv.electronic.store.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    private String categoryId;

    @Column(length = 30, unique = true)
    private String title;

    @Column(length = 300)
    private String description;
    private String coverImageName;
}
