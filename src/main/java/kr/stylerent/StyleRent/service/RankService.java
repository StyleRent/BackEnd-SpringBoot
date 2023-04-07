package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.dto.RankResponse;
import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.repository.RankRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {



    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    public RankResponse setRank(RankDto request) {
        // 받는 객체 데이터를 테이블 객체로 반환
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail(authentication.getName()).orElseThrow(null); // find Sender User
        User receiver = userRepository.findById(request.getRecieverid()).orElseThrow(null); // find receiver User

        if(currentUser == null){
            return RankResponse.builder()
                    .error("찾을 수 없는 아이디입니다.")
                    .build();
        }

        if(receiver == null){
            return RankResponse.builder()
                    .error("찾을 수 없는 아이디입니다.")
                    .build();
        }

        if(rankRepository.checkUnique(currentUser.getId(), request.getRecieverid()).isPresent()){
            return RankResponse.builder()
                    .error("이미 평가된 사용자입니다.")
                    .build();
        };

        if(currentUser.getId().equals(request.getRecieverid())){
            return RankResponse.builder()
                    .error("평가 할 수 없는 아이디입니다.")
                    .build();
        }


        // DTO to Entity
        Rank rank = Rank.builder()
                .rank(request.getRank())
                .user(currentUser)
                .receiver(receiver)
                .build();

        Rank saved = rankRepository.save(rank);


        return RankResponse.builder()
                .id(saved.getId())
                .rank(saved.getRank())
                .userid(currentUser.getId())
                .recieverid(request.getRecieverid())
                .build();
    }
}
