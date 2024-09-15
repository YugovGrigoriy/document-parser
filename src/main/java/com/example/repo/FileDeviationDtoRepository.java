package com.example.repo;

import com.example.file.dto.PaymentEntryDeviationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDeviationDtoRepository extends JpaRepository<PaymentEntryDeviationDto, Long> {
}
