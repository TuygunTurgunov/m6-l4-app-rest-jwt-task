package uz.pdp.online.m6l4apprestjwttask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private  Card fromCardId;

    @ManyToOne
    private  Card toCardId;

    private Double amount;

    @CreationTimestamp
    private Timestamp dataWithTime;

}
