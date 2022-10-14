package com.test.automatic.irrigation.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.automatic.irrigation.system.dtos.SlotConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "plot")
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "land_id")
    @JsonIgnore
    private Land land;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    private Set<TimeSlot> timeSlots;

    @OneToOne(mappedBy = "plot", cascade = CascadeType.ALL)
    private SensorDevice sensor;

    public void addSlots(List<TimeSlot> slots) {
        this.timeSlots.addAll(slots);
    }



}
