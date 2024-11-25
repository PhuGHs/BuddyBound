package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "is_expired")
    private boolean isExpired = false;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationHistory location;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany(mappedBy = "posts")
    private Set<Album> albums;

    @OneToMany(mappedBy = "post")
    private List<PostVisibility> postVisibilities;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
