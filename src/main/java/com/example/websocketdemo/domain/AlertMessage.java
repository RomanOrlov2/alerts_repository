package com.example.websocketdemo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "alert_message")
public class AlertMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "message", updatable = false, nullable = false)
    private String message;

    @Column(name = "date", updatable = false, nullable = false)
    private Date date;
}
