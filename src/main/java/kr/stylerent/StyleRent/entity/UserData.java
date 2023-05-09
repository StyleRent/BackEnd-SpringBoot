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
@Table(name = "userdata")
public class UserData {
    @Id
    @Column(name = "userdataid")
    private Integer userdataid;

    @Column
    private String phonenumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userdataid")
    private User user;

    public void updatePhoneNumber(String phonenumber,User user){
        this.phonenumber = phonenumber;
        this.user = user;
    }

}
