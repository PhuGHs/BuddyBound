package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "content")
    private String content;
}
