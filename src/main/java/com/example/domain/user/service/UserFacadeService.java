package com.example.domain.user.service;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.service.PaymentService;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import com.example.domain.user.domain.User;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final PaymentService paymentService;

    public boolean cancel(Long id, Long paymentId) {
        List<MyPaymentResponseDto> ticket = getTicket(id);
        findMyPayment(paymentId, ticket);
        cancelPayment(paymentId);
        cancelSeatStatus(paymentId);
        return true;
    }

    private void cancelSeatStatus(Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        List<Seat> seats = payment.getSchedule().getTheater().getSeats();
        for (Seat seat : seats) {
            Sold status = seat.getStatus();
            if (status == Sold.SOLD) {
                status = Sold.ABLE;
                seat.updateStatus(status);

            }
        }
    }

    private void cancelPayment(Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        LocalDateTime startTime = payment.getSchedule().getTime();

        if (startTime.isBefore(LocalDateTime.now())) throw new CustomException(TIME_OVER.getMessage(), TIME_OVER);
        else payment.cancelPayment("CANCEL", LocalDateTime.now());
    }

    private void findMyPayment(Long paymentId, List<MyPaymentResponseDto> ticket) {
        boolean payIdExist = ticket.stream()
                .map(MyPaymentResponseDto::getPaymentId)
                .anyMatch(rawId -> rawId.equals(paymentId));

        if (!payIdExist) throw new CustomException(PAYMENT_NOT_FOUND.getMessage(), PAYMENT_NOT_FOUND);
    }

    public List<MyPaymentResponseDto> getTicket(Long userId) {
        User user = userService.findById(userId);
        return getTicketDetail(user.getId());
    }

    private List<MyPaymentResponseDto> getTicketDetail(Long userId) {

        List<Payment> paymentList = paymentService.findAll(userId);
        List<MyPaymentResponseDto> paymentResponseDto = new ArrayList<>();
        for (Payment payment : paymentList) {
            List<PaidSeat> seatList = payment.getSeatList();
            MyPaymentResponseDto responseDto = new MyPaymentResponseDto(payment, seatList);
            paymentResponseDto.add(responseDto);
        }
        return paymentResponseDto;


    }
}
