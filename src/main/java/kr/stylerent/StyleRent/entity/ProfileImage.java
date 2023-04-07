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
@Table(name = "profile_image")
public class ProfileImage {
    @Id
    @Column(name = "userid")
    private Integer id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    @Lob
    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userid")
    private User user;

}
