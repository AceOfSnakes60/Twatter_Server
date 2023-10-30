package com.group3.twat.group.model;

import com.group3.twat.twatt.model.Twatt;
import com.group3.twat.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<User> users;

    @ManyToMany
    @JoinColumn(name = "twatt_id")
    private List<Twatt> twatts;
}
