package kr.stylerent.StyleRent.entity;

import jakarta.persistence.*;
import kr.stylerent.StyleRent.dto.RankDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rank")
public class Rank {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer rank;

    @Column
    private String evaluationText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}