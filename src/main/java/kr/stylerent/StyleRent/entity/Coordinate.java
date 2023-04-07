package kr.stylerent.StyleRent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordinate")
public class Coordinate {
    @Id
    @Column(name = "userid")
    private Integer id;

    @Column
    private String coord_long;

    @Column
    private String coord_lat;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userid")
    private User user;
}
