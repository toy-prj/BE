package com.example.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {

    STANDARD("Standard", 0) {
        @Override
        public double getDiscount(double amount) {
            return amount;
        }
    },
    SILVER("Silver", 0.1) {
        @Override
        public double getDiscount(double amount) {
            return amount * 0.9;
        }
    },
    GOLD("Gold", 0.2) {
        @Override
        public double getDiscount(double amount) {
            return amount * 0.8;
        }
    },
    VIP("Vip", 0.3) {
        @Override
        public double getDiscount(double amount) {
            return amount * 0.7;
        }
    };

    private final String rank;
    private final double discountRate;

    public abstract double getDiscount(double amount);

}
