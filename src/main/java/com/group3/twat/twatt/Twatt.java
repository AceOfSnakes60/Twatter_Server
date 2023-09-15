package com.group3.twat.twatt;


import com.group3.twat.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "twatt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Twatt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;
    private LocalDate date;
    private Long parentId;
}
