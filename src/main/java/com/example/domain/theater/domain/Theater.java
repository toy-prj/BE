package com.example.domain.theater.domain;

import com.example.domain.seat.domain.Seat;
import com.example.domain.theater.domain.request.UpdateTheaterRequestDto;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;

    @Column(name = "theater_name")
    private String name;

    @Column(name = "theater_loc")
    private String location;

    @OneToMany(mappedBy = "theater")
    private List<Seat> seats;

    public void updateTheaterInformation(UpdateTheaterRequestDto dto) {
       this.name = dto.getName();
       this.location = dto.getLocation();
    }
}
