package com.test.automatic.irrigation.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.automatic.irrigation.system.enums.TimeSlotStatus;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "time_slots")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time" )
    private Timestamp startTime;

    @Column(name = "duration")
    private int duration; //no. of minutes to irrigate.

    @Column(name = "water_amount")
    private float waterAmount;

    @ManyToOne
    @JoinColumn(name = "plot_id")
    @JsonIgnore
    private Plot plot;

    @Enumerated(EnumType.STRING)
    private TimeSlotStatus status = TimeSlotStatus.PENDING;

    public String toString() {
        return "TimeSLot(Id=" + this.id + ", starts=" + this.startTime + ", duration=" + this.duration + ")";
    }
}
