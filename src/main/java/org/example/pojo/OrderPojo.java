package org.example.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Order1")
public class OrderPojo {
    @Id
    private int id;
    private ZonedDateTime dateTime;
}
