package ru.pnz.floridov.RestDemo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientBalanceDetail {

    private BigDecimal debetSum;
    private BigDecimal creditSum;
    private BigDecimal total;
}
