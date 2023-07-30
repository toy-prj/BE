package com.example.domain.theater.service;

import com.example.domain.theater.repository.TheaterRepository;
import com.example.domain.theater.domain.Theater;
import com.example.domain.theater.domain.request.SaveTheaterRequestDto;
import com.example.domain.theater.domain.request.UpdateTheaterRequestDto;
import com.example.domain.theater.domain.response.TheaterListResponseDto;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public List<TheaterListResponseDto> getList() {
        return theaterRepository.findAll().stream().map(TheaterListResponseDto::new).collect(Collectors.toList());
    }

    public boolean save(SaveTheaterRequestDto dto) {
        checkDuplicateTheater(dto.getName(), dto.getLocation());
        theaterRepository.save(dto.toEntity());
        return true;
    }

    public boolean updateById(Long id, UpdateTheaterRequestDto dto) {
        findById(id).updateTheaterInformation(dto);
        return true;
    }

    public boolean deleteById(Long id) {
        theaterRepository.delete(findById(id));
        return true;
    }
    public Theater findById(Long id) {
       return theaterRepository.findById(id).orElseThrow(() -> new CustomException(THEATER_NOT_FOUND.getMessage(), THEATER_NOT_FOUND));
    }

    private void checkDuplicateTheater(String name, String location) {
        theaterRepository.findByNameAndLocation(name, location).ifPresent(
                x -> {
                    throw new CustomException(DUPLICATE_THEATER.getMessage(), DUPLICATE_THEATER);
                }
        );

    }



}
