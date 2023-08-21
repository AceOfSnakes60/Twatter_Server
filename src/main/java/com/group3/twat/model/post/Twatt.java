package com.group3.twat.model.post;


import com.group3.twat.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "twatt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Twatt {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;
    private LocalDate date;
}
