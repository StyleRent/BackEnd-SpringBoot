package kr.stylerent.StyleRent.controller;

import kr.stylerent.StyleRent.dto.ImageMessageResponse;
import kr.stylerent.StyleRent.dto.Location.LocationDto;
import kr.stylerent.StyleRent.dto.Location.NearbyDto;
import kr.stylerent.StyleRent.dto.Location.NearbyUsersResponse;
import kr.stylerent.StyleRent.dto.Location.SetLocationResponse;
import kr.stylerent.StyleRent.dto.RankDto;
import kr.stylerent.StyleRent.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class LocationController {
    @Autowired
    LocationService locationService;

    // 위치 설정
    @PostMapping("/api/v1/setcurrentlocation")
    public ResponseEntity<SetLocationResponse> setLocation(@RequestBody LocationDto request) {
        return ResponseEntity.ok(locationService.setLocation(request));
    }

    // Update Location
    @PostMapping("/api/v1/updatecurrentlocation")
    public ResponseEntity<SetLocationResponse> updateLocation(@RequestBody LocationDto request) {
        return ResponseEntity.ok(locationService.updateLocation(request));
    }

    // Get Nearby Users
    @PostMapping("/api/v1/getnearbyusers")
    public ResponseEntity<List<NearbyUsersResponse>> getNearby(@RequestBody NearbyDto request) {
        List<NearbyUsersResponse> nearbyUsers =  locationService.getNearbyUsers(request);
        return ResponseEntity.ok(nearbyUsers);
    }
}
