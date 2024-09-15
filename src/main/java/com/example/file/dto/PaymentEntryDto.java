package com.example.file.dto;

import jakarta.persistence.*;
import lombok.*;


@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String recordNumber;
    @Column
    private String paymentID;
    @Column
    private String companyName;
    @Column
    private String payerINN;
    @Column
    private String amount;

    private String fileName;


}
