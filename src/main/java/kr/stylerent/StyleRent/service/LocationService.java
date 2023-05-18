package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.dto.Location.LocationDto;
import kr.stylerent.StyleRent.dto.Location.NearbyDto;
import kr.stylerent.StyleRent.dto.Location.NearbyUsersResponse;
import kr.stylerent.StyleRent.dto.Location.SetLocationResponse;
import kr.stylerent.StyleRent.entity.Location;
import kr.stylerent.StyleRent.entity.User;
import kr.stylerent.StyleRent.entity.UserData;
import kr.stylerent.StyleRent.repository.LocationRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    final double radius = 6371e3; // Earth's radius in meters

    // 위치 추가 기능
    public SetLocationResponse setLocation(LocationDto request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 해당 Token을 통해 사용자의 데이터 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if(request.getNamelocation().isEmpty()){
            return SetLocationResponse.builder().error("위치 이름 추가하세요!").build();
        }
        if(request.getLongitude().isEmpty() || request.getLatitude().isEmpty()){
            return SetLocationResponse.builder().error("Location Error!").build();
        }

        Location currentLocation = Location.builder()
                .locationName(request.getNamelocation())
                .user(user)
                .latitude(Double.parseDouble(request.getLatitude()))
                .longitude(Double.parseDouble(request.getLongitude()))
                .build();
        locationRepository.save(currentLocation);
        return SetLocationResponse.builder()
                .namelocation(currentLocation.getLocationName())
                .longitude(currentLocation.getLongitude().toString())
                .latitude(currentLocation.getLatitude().toString())
                .build();
    }

    // 위치 수정 기능
    public SetLocationResponse updateLocation(LocationDto request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 해당 Token을 통해 사용자의 데이터 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Location currentLocation = locationRepository.findById(user.getId()).orElseThrow();

        currentLocation.locationUpdate(request);

        locationRepository.save(currentLocation);

        return SetLocationResponse.builder()
                .namelocation(currentLocation.getLocationName())
                .longitude(currentLocation.getLongitude().toString())
                .latitude(currentLocation.getLatitude().toString())
                .build();
    }
    
    
    // Get Nearby Users data
    public List<NearbyUsersResponse> getNearbyUsers(NearbyDto request){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        
        // 자신의 위치
        Location myLocation = locationRepository.findById(user.getId()).orElseThrow();
        
        List<User> allUsers = userRepository.findAll();
        List<NearbyUsersResponse> nearByUsers = new ArrayList<>();
        
        for(User currentUser : allUsers){
            // 사용자의 위치 받기
            Location currentLocation = locationRepository.findById(currentUser.getId()).orElseThrow();

            double dLat = Math.toRadians(currentLocation.getLatitude() - myLocation.getLatitude());
            double dLon = Math.toRadians(currentLocation.getLongitude() - myLocation.getLongitude());
            double lat1 = Math.toRadians(myLocation.getLatitude());
            double lat2 = Math.toRadians(currentLocation.getLatitude());

            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double dist = radius * c;

            if (dist <= request.getDistance()) {
                NearbyUsersResponse findedUser = NearbyUsersResponse.builder()
                        .distance(dist)
                        .userName(currentUser.getUsername())
                        .longtitude(currentLocation.getLongitude().toString())
                        .latitude(currentLocation.getLatitude().toString())
                        .build();

                nearByUsers.add(findedUser);
            }
        }
        return nearByUsers;
    }
}
