package com.example.domain.user.service;

import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.payment.service.PaymentFacadeService;
import com.example.domain.payment.service.PaymentService;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.Sold;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.global.exception.CustomException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.domain.payment.domain.PaymentType.CASH;
import static com.example.domain.seat.domain.Sold.ABLE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserFacadeServiceTest {

    @Autowired
    UserFacadeService userFacadeService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleFacadeService scheduleFacadeService;

    @Autowired
    PaymentFacadeService paymentFacadeService;

    @Autowired
    PaymentService paymentService;


    Long afterTime = 1L;
    Long beforeTime = 3L;
    Long afterUser = 3L;
    Long beforeUser = 4L;


    @Test
    @DisplayName("유저가 상영 시간이 지난 후 구매 내역 취소를 요청하면 에러가 발생한다.")
    void cancelPayment() {

        // given
        List<SeatListResponseDto> seatListByScheduleId = scheduleFacadeService.getSeatListByScheduleId(afterTime);

        Long id = seatListByScheduleId.get(0).getId();
        Long id1 = seatListByScheduleId.get(2).getId();

        List<SeatSelectedDto> seatList = selectAndSaveSeat(id, id1);

        PaymentResponseDto price = paymentFacadeService.getPrice(afterTime,
                PaymentRequestDto.builder()
                        .scheduleId(afterTime)
                        .count(seatList.size())
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(afterUser)
                        .build());


        paymentFacadeService.payMyMovie(afterTime,
                FixPaymentRequestDto.builder().
                        amountToPay(price.getAmountToPay()).
                        count(price.getCount()).
                        payment(price.getPaymentType()).
                        seatList(price.getSeatList()).
                        userId(afterUser).
                        scheduleId(price.getScheduleId()).
                        build());

        // when
        List<Payment> paymentList = paymentService.findAll(afterUser);
        Long paymentId = paymentList.get(0).getId();


        // then
        assertThrows(CustomException.class,
                () -> {
                    userFacadeService.cancel(afterUser, paymentId);
                }
        );
    }

    @NotNull
    private static List<SeatSelectedDto> selectAndSaveSeat(Long id, Long id1) {
        SeatSelectedDto dto = new SeatSelectedDto(id);
        SeatSelectedDto dto1 = new SeatSelectedDto(id1);

        List<SeatSelectedDto> seatList = new ArrayList<>();
        seatList.add(dto);
        seatList.add(dto1);
        return seatList;
    }


    @Test
    @DisplayName("유저가 상영 시간 전에 구매 내역 취소요청 하면 결제 취소 및 status 가 변경 되어야 한다.")
    void cancelPaymentOk() {
        // given
        List<SeatListResponseDto> seatListByScheduleId = scheduleFacadeService.getSeatListByScheduleId(beforeTime);

        Long id = seatListByScheduleId.get(5).getId();
        Long id1 = seatListByScheduleId.get(6).getId();

        List<SeatSelectedDto> seatList = selectAndSaveSeat(id, id1);

        PaymentResponseDto price = paymentFacadeService.getPrice(beforeTime,
                PaymentRequestDto.builder()
                        .scheduleId(beforeTime)
                        .count(seatList.size())
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(beforeUser)
                        .build());


        paymentFacadeService.payMyMovie(beforeTime,
                FixPaymentRequestDto.builder().
                        amountToPay(price.getAmountToPay()).
                        count(price.getCount()).
                        payment(price.getPaymentType()).
                        seatList(price.getSeatList()).
                        userId(beforeUser).
                        scheduleId(price.getScheduleId()).
                        build());

        // when
        List<Payment> paymentList = paymentService.findAll(beforeUser);
        Long paymentId = paymentList.get(0).getId();
        assertTrue(userFacadeService.cancel(beforeUser, paymentId));

        List<SeatListResponseDto> seatListByScheduleId1 = scheduleFacadeService.getSeatListByScheduleId(price.getScheduleId());
        Sold status1 = seatListByScheduleId1.get(5).getStatus();
        Sold status2 = seatListByScheduleId1.get(6).getStatus();

        assertEquals(ABLE,status1);
        assertEquals(ABLE,status2);


    }

}