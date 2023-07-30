package com.example.domain.payment.service;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import com.example.domain.seat.service.SeatService;
import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.domain.seat.domain.Sold.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentFacadeService {

    private final PaymentService paymentService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final SeatService seatService;
    private final PaidSeatService paidSeatService;

    public PaymentResponseDto getPrice(Long scheduleId, PaymentRequestDto dto) {
        Schedule schedule = scheduleService.findById(scheduleId);
        User foundUser = userService.findById(dto.getUserId());
        return new PaymentResponseDto(schedule, foundUser.getRank(), dto.getCount(), dto.getSeatList(),
                getDiscountedPrice(dto.getCount(), foundUser.getRank(), paymentService.getAmount()));
    }

    public boolean payMyMovie(Long scheduleId, FixPaymentRequestDto dto) {
        Schedule schedule = scheduleService.findById(scheduleId);
        User foundUser = userService.findById(dto.getUserId());
        Payment payment = paymentService.save(dto, foundUser, schedule);
        savePaidSeat(dto.getSeatList(), payment);
        updateSeatStatus(dto.getSeatList());
        return true;
    }

    public void savePaidSeat(List<SeatSelectedDto> seatList, Payment payment) {
        for (SeatSelectedDto selectedDto : seatList) {
            Seat seat = seatService.findById(selectedDto.getSeatId());
            paidSeatService.save(seat.getName(), payment);
        }
    }

    private void updateSeatStatus(List<SeatSelectedDto> seatList) {
        for (SeatSelectedDto selectedDto : seatList) {
            Seat seat = seatService.findById(selectedDto.getSeatId());
            Sold status = seat.getStatus();

            if (status == ABLE) {
                status = SOLD;
                seat.updateStatus(status);
            }
        }
    }

    private double getDiscountedPrice(int count, Rank rank, double amount) {
        double amountWithDc = rank.getDiscount(amount);
        return amountWithDc * count;
    }


}
