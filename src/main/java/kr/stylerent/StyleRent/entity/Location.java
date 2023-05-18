package kr.stylerent.StyleRent.entity;

import jakarta.persistence.*;
import kr.stylerent.StyleRent.dto.Location.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "userid")
    private Integer locationid;

    @Column
    private String locationName;

    @Column
    private Double latitude;

    @Column
    private Double longitude;


    @OneToOne
    @MapsId
    @JoinColumn(name = "userid")
    private User user;


    public void locationUpdate(LocationDto request){
        if(!request.getNamelocation().equals(locationName)){
            this.locationName = request.getNamelocation();
        }
        if(Double.parseDouble(request.getLongitude()) != longitude){
            this.longitude = Double.parseDouble(request.getLongitude());
        }
        if(Double.parseDouble(request.getLatitude()) != latitude){
            this.latitude = Double.parseDouble(request.getLatitude());
        }
    }
}
