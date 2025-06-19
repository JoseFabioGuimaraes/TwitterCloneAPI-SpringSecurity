package br.com.jfabiodev.TwitterClone.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postID")
public class Post {

    @Column(name = "post_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;
    @Setter
    private String content;
    @CreationTimestamp
    private Instant creationTimestamp;
}
