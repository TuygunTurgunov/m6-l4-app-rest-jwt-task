package uz.pdp.online.m6l4apprestjwttask.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private Long number;


    private Double balance;

    //avtomat  +5 yil
    //    private Date expireDate=new Date(System.currentTimeMillis()+157_784_760_000L);

    @Column(nullable = false)
    private Timestamp expireDate;

    private boolean active = true;

}
