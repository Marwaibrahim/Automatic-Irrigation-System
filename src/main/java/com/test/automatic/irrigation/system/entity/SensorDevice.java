package com.test.automatic.irrigation.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@Setter
@Table(name = "sensor_devices")
public class SensorDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="device_id", unique = true)
    private String deviceId;

    @Column(name="device_url", unique = true)
    private String url;

    @OneToOne
    @JoinColumn(name = "plot_id", unique = true)
    @JsonIgnore
    private Plot plot;

    public boolean call(int duration, float amount) {
        System.out.println("Calling sensor(" + this.deviceId + ") on " + this.url);
        System.out.println("Duration of irrigation = " + duration + ", amount of water = " + amount);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("INTERRUPT");
            return false;
        }
        boolean isSuccess = new Random().nextBoolean();
        System.out.println(isSuccess ? "Sensor responded successfully" : "Sensor not responding");
        return isSuccess;
    }

}
