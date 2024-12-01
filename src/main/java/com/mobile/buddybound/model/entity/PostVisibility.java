package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_visibilities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostVisibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
