package com.example.domain.payment.domain;

import com.example.domain.payment.domain.response.PaidSeatResponseDto;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    private LocalDateTime time;

    @Builder.Default
    @Column(name = "amount")
    private double amount = 10000;

    private int count;

    private String status;

    private LocalDateTime cancelPayment;

    @OneToMany(mappedBy = "payment" , fetch = FetchType.LAZY)
    private List<PaidSeat> seatList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void cancelPayment(String cancel, LocalDateTime now) {
        this.status = cancel;
        this.cancelPayment = now;
    }
}
