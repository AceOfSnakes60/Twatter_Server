package com.group3.twat.twatt.model;


import com.group3.twat.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "twatt")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Twatt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private String text;
    private LocalDate date;
    private Long parentId;
}
