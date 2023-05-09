package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.dto.RankResponse;
import kr.stylerent.StyleRent.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RankController {
    private final RankService rankService;


    @PostMapping("/api/v1/setrank")
    public ResponseEntity<RankResponse> setrank(
            @RequestBody RankDto request
    ) {
        return ResponseEntity.ok(rankService.setRank(request));
    }
}
