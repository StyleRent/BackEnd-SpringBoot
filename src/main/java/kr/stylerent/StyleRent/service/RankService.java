package kr.stylerent.StyleRent.service;

import jakarta.transaction.Transactional;
import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.dto.RankResponse;
import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.RankRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {



    private final UserRepository userRepository;


    private final RankRepository rankRepository;

    public RankResponse setRank(RankDto request) {
        // 받는 객체 데이터를 테이블 객체로 반환

        User user = userRepository.findById(request.getUserid()).orElseThrow(); // find Sender User
        User receiver = userRepository.findById(request.getRecieverid()).orElseThrow(); // find receiver User


        // DTO to Entity
        Rank rank = Rank.builder()
                .rank(request.getRank())
                .user(user)
                .receiver(receiver)
                .build();

        Rank saved = rankRepository.save(rank);


        return RankResponse.builder()
                .id(saved.getId())
                .rank(saved.getRank())
                .userid(request.getUserid())
                .recieverid(request.getRecieverid())
                .build();
    }
}
