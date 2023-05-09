package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.*;
import kr.stylerent.StyleRent.entity.ProfileImage;
import kr.stylerent.StyleRent.entity.Rank;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import kr.stylerent.StyleRent.repository.ProfileImageRepository;
import kr.stylerent.StyleRent.repository.RankRepository;
import kr.stylerent.StyleRent.repository.UserDataRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserDataRepository userDataRepository;

    private final ProfileImageRepository profileImageRepository;

    private final RankRepository rankRepository;

    private final UserRepository userRepository;

    public Integer getRankAverage(List<RankResponse> rankResponses){
        Integer num = rankResponses.size();
        Integer sum = 0;
        if(!rankResponses.isEmpty()){
            for(RankResponse rR : rankResponses){
                sum += rR.getRank();
            }
            return sum / rankResponses.size();
        }
        return 0;
    }


    public UserDataResponse getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        UserData userData = userDataRepository.findById(user.getId()).orElse(new UserData());


        // Find All data by marked rank
        List<RankResponse> marks = null;
        marks = rankRepository.findAllById(user.getId())
                .stream()
                .map(ranks -> RankResponse.builder()
                        .id(ranks.getId())
                        .rank(ranks.getRank())
                        .userid(ranks.getUser().getId())
                        .recieverid(ranks.getReceiver().getId())
                        .build()
                )
                .collect(Collectors.toList());


        // find all by received id
        List<RankResponse> receivedRank = null;
        receivedRank = rankRepository.findAllByReceiverId(user.getId())
                .stream()
                .map(ranks -> RankResponse.builder()
                        .id(ranks.getId())
                        .rank(ranks.getRank())
                        .userid(ranks.getUser().getId())
                        .recieverid(ranks.getReceiver().getId())
                        .build()
                )
                .collect(Collectors.toList());

        // Calculate average Rank
        Integer sum = getRankAverage(receivedRank);


        ProfileImage profileImage = profileImageRepository.findById(user.getId()).orElse(new ProfileImage());
        ImageResponse imageResponse = ImageResponse.builder().imageByte(profileImage.getData()).build();


        return UserDataResponse.builder()
                .userid(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phonenumber(userData.getPhonenumber())
                .averageRank(sum)
                .marks(marks)
                .receivedRank(receivedRank)
                .imageResponse(imageResponse)
                .build();
    }

    public UserDataUpdateResponseMessage updateUserData(UserDataDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if(request.getPhonenumber() != null){
            UserData updateData = userDataRepository.findById(user.getId()).orElse(new UserData());
            updateData.updatePhoneNumber(request.getPhonenumber(), user);

            userDataRepository.save(updateData);
        }

        return(UserDataUpdateResponseMessage.builder().message("Data updated!").build());
    }
}
