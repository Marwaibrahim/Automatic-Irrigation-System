package com.test.automatic.irrigation.system.tasks;

import com.test.automatic.irrigation.system.entity.SensorDevice;
import com.test.automatic.irrigation.system.entity.TimeSlot;
import com.test.automatic.irrigation.system.enums.TimeSlotStatus;
import com.test.automatic.irrigation.system.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrrigationTasks {

    @Autowired
    private LandService landService;

    @Value("${spring.application.max-sensor-call-retries}")
    private int MAX_RETRIES;

    @Value("${spring.application.sensor-call-trial-interval}")
    private int RETRIES_INTERVAL;

    @Scheduled(fixedDelay = 60000)
    public void irrigatePlots() {
        List<TimeSlot> readySlots = landService.getReadySlots();
        readySlots.stream().forEach(s -> {
            s.setStatus(TimeSlotStatus.PROCESSING);
            landService.saveTimeSlot(s);
        });

        System.out.println(readySlots);
        readySlots.stream().forEach(s -> {
            int currTrial = 1;
            SensorDevice sensor = s.getPlot().getSensor();
            boolean isSuccess = sensor.call(s.getDuration(), s.getWaterAmount());
            while(!isSuccess && currTrial <= MAX_RETRIES) {
                try {
                    Thread.sleep(RETRIES_INTERVAL);
                } catch (InterruptedException e) {
                    continue;
                }
                currTrial++;
                System.out.println("Retrial no. " + currTrial);
                isSuccess = sensor.call(s.getDuration(), s.getWaterAmount());
            }
            if (currTrial == 3){
                System.out.println("Alert the System");
            }
            s.setStatus(isSuccess ? TimeSlotStatus.DONE : TimeSlotStatus.ERROR);
            landService.saveTimeSlot(s);
        });


    }
}
