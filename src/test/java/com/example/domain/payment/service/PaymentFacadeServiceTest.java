package com.example.domain.payment.service;

import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.Sold;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.service.SeatService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.domain.payment.domain.PaymentType.*;
import static com.example.domain.seat.domain.Sold.ABLE;
import static com.example.domain.seat.domain.Sold.SOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Rollback
@Transactional
class PaymentFacadeServiceTest {

    @Autowired
    SeatService seatService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleFacadeService scheduleFacadeService;

    @Autowired
    PaymentFacadeService paymentFacadeService;

    @Autowired
    PaymentService paymentService;

    long scheduleId = 9L;
    long silverUser =3L;
    long vipUser =4L;

    PaymentResponseDto vipPrice;

    @Test
    @DisplayName("Silver 랭크 유저는 결제금액을 확인 할 때 10%의 할인을 제공 받을 수 있다.")
    void getSilverUserPayment() {

        // given
        List<SeatListResponseDto> seatListByScheduleId = scheduleFacadeService.getSeatListByScheduleId(scheduleId);

        Long id1 = seatListByScheduleId.get(6).getId();
        Long id2 = seatListByScheduleId.get(7).getId();
        Long id3 = seatListByScheduleId.get(8).getId();

        SeatSelectedDto dto1 = new SeatSelectedDto(id1);
        SeatSelectedDto dto2 = new SeatSelectedDto(id2);
        SeatSelectedDto dto3 = new SeatSelectedDto(id3);

        List<SeatSelectedDto> seatList = new ArrayList<>();
        seatList.add(dto1);
        seatList.add(dto2);
        seatList.add(dto3);

        // when
        PaymentResponseDto price = paymentFacadeService.getPrice(scheduleId,
                PaymentRequestDto.builder()
                        .scheduleId(scheduleId)
                        .count(seatList.size())
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(silverUser)
                        .build());

        // then
        assertEquals(27000.0, price.getAmountToPay());

    }

    @Test
    @DisplayName("Vip 랭크 유저는 결제금액을 확인 할 때 30%의 할인을 제공 받을 수 있다.")
    void getVipUserPayment() {

        // given
        vipUserPayment();

        // then
        assertEquals(28000.0, vipPrice.getAmountToPay());
    }



    @Test
    @DisplayName("Vip 유저가 선택 한 인원수와 좌석을 토대로 결제를 하고 결제 된 건 으로 좌석의 status 를 변경 할 수 있다.")
    void postPayment() {

        // given
        vipUserPayment();

        boolean b = paymentFacadeService.payMyMovie(
                scheduleId, FixPaymentRequestDto.builder()
                        .amountToPay(vipPrice.getAmountToPay())
                        .count(vipPrice.getCount())
                        .payment(vipPrice.getPaymentType())
                        .seatList(vipPrice.getSeatList())
                        .userId(vipUser)
                        .scheduleId(vipPrice.getScheduleId())
                        .build()
        );

        // then
        List<SeatListResponseDto> seatListByScheduleId1 = scheduleFacadeService.getSeatListByScheduleId(scheduleId);
        Sold status1 = seatListByScheduleId1.get(5).getStatus();
        Sold status2 = seatListByScheduleId1.get(6).getStatus();

        assertTrue(b);
        assertEquals(ABLE,status1);
        assertEquals(SOLD,status2);

    }

    private void vipUserPayment() {
        List<SeatListResponseDto> seatListByScheduleId = scheduleFacadeService.getSeatListByScheduleId(scheduleId);

        Long id1 = seatListByScheduleId.get(6).getId();
        Long id2 = seatListByScheduleId.get(7).getId();
        Long id3 = seatListByScheduleId.get(8).getId();
        Long id4 = seatListByScheduleId.get(9).getId();

        SeatSelectedDto dto1 = new SeatSelectedDto(id1);
        SeatSelectedDto dto2 = new SeatSelectedDto(id2);
        SeatSelectedDto dto3 = new SeatSelectedDto(id3);
        SeatSelectedDto dto4 = new SeatSelectedDto(id4);

        List<SeatSelectedDto> seatList = new ArrayList<>();
        seatList.add(dto1);
        seatList.add(dto2);
        seatList.add(dto3);
        seatList.add(dto4);

        // when
        vipPrice = paymentFacadeService.getPrice(scheduleId,
                PaymentRequestDto.builder()
                        .scheduleId(scheduleId)
                        .count(seatList.size())
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(vipUser)
                        .build());
    }
}