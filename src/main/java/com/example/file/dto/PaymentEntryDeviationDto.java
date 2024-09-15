package com.example.file.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class PaymentEntryDeviationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;
}
