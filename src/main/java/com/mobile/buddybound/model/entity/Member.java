package com.mobile.buddybound.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "is_approved")
    private boolean isApproved;

    @CreatedDate
    @Column(name = "join_date")
    private LocalDateTime joinDate = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    private List<Message> messages;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @OneToMany(mappedBy = "member")
    private List<PostVisibility> postVisibilities;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;
}
