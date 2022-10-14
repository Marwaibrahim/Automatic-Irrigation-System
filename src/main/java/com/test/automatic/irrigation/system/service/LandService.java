package com.test.automatic.irrigation.system.service;

import com.test.automatic.irrigation.system.dtos.CreateDeviceDto;
import com.test.automatic.irrigation.system.dtos.CreatePlotDto;
import com.test.automatic.irrigation.system.dtos.PlotConfigurationDto;
import com.test.automatic.irrigation.system.entity.Land;
import com.test.automatic.irrigation.system.entity.Plot;
import com.test.automatic.irrigation.system.entity.SensorDevice;
import com.test.automatic.irrigation.system.entity.TimeSlot;
import com.test.automatic.irrigation.system.enums.TimeSlotStatus;
import com.test.automatic.irrigation.system.repository.LandRepository;
import com.test.automatic.irrigation.system.repository.PlotRepository;
import com.test.automatic.irrigation.system.repository.SensorDeviceRepository;
import com.test.automatic.irrigation.system.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LandService {
    @Autowired
    private LandRepository landRepository;

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private TimeSlotRepository timeSLotRepository;

    @Autowired
    private SensorDeviceRepository sensorDeviceRepository;

    public Land addLand(String location){
        Land land = new Land();
        land.setLocation(location);
        return landRepository.save(land);
    }

    public Plot addPlot(CreatePlotDto dto, Long landId){
        Land land = landRepository
                .findById(landId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Land not found", new NoSuchElementException()));
        System.out.println("Land Id : " + land.getId());
        Plot plot = new Plot();
        plot.setLand(land);
        plot.setName(dto.name);
            return plotRepository.save(plot);
    }

    public Plot configurePlot(Long plotId, PlotConfigurationDto dto){
        Plot plot = plotRepository
                .findById(plotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found", new NoSuchElementException()));
        //Map dto to entities.
        dto.slots.stream().map(s -> s.duration + " " + s.waterAmount + " " +s.startTime).forEach(System.out::println);
        List<TimeSlot> slots = dto.slots.stream().map(slotConf -> {
            TimeSlot slot = new TimeSlot();
            slot.setStartTime(slotConf.startTime);
            slot.setWaterAmount(slotConf.waterAmount);
            slot.setDuration(slotConf.duration);
            slot.setPlot(plot);
            return slot;
        }).collect(Collectors.toList());
        plot.addSlots(slots);
//        plot.getTimeSlots().stream().forEach(System.out::println);
        return plotRepository.save(plot);
    }

    public List<Plot> getPlots(){
        return plotRepository.findDetailedPlots();
    }

    public List<TimeSlot> getReadySlots(){
        Date now = new Date();
        return timeSLotRepository.findReadySlots(new Timestamp(now.getTime() -10*60*1000), new Timestamp(now.getTime()));
    }


    public SensorDevice addDevice( Long plotId, CreateDeviceDto dto){
        Plot plot = plotRepository
                .findById(plotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found", new NoSuchElementException()));
        SensorDevice sensorDevice = sensorDeviceRepository.findByDeviceId(dto.deviceId);
        System.out.println(sensorDevice);
        if (sensorDevice != null) {
            throw new  ResponseStatusException(HttpStatus.CONFLICT, "Device already exists", new NoSuchElementException());
        }
        sensorDevice = new SensorDevice();
        sensorDevice.setPlot(plot);
        sensorDevice.setUrl(dto.url);
        sensorDevice.setDeviceId(dto.deviceId);
        return sensorDeviceRepository.save(sensorDevice);
    }

    public TimeSlot saveTimeSlot(TimeSlot slot) {
        return timeSLotRepository.save(slot);
    }
}
