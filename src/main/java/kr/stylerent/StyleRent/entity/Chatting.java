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
@Table(name = "chat")
public class Chatting {
    @Id
    @GeneratedValue
    private Integer chat_id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageInit messageInit;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column
    private String message;
}
