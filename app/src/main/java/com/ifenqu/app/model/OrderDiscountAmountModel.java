package com.ifenqu.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDiscountAmountModel implements Serializable {
    private BigDecimal ticketAmount;
    private BigDecimal redPacketAmount;
    private BigDecimal discount;
}
