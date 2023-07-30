package com.example.domain.user.domain.request;

import com.example.domain.user.domain.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public class UpgradeUserRankRequestDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Rank newRank;

}
