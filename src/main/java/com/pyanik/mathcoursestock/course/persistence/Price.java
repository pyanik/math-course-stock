package com.pyanik.mathcoursestock.course.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
class Price {

    private BigDecimal amount;
    private String currency;
}