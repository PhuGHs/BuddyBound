package com.mobile.buddybound.model.entity;

import com.mobile.buddybound.model.enumeration.MemorableDestinationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memorable_destinations")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class MemorableDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "note")
    private String note;

    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private MemorableDestinationType locationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private LocationHistory location;
}
