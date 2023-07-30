package com.example.domain.seat.domain;

import com.example.domain.theater.domain.Theater;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;



@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ABLE'")
    private Sold status;

    @ManyToOne
    @JoinColumn(name= "theater_id")
    private Theater theater;

    public void updateStatus(Sold status) {
        this.status = status;
    }
}
