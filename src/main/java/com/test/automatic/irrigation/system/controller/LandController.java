package com.test.automatic.irrigation.system.controller;

import com.test.automatic.irrigation.system.dtos.CreateDeviceDto;
import com.test.automatic.irrigation.system.dtos.CreatePlotDto;
import com.test.automatic.irrigation.system.dtos.PlotConfigurationDto;
import com.test.automatic.irrigation.system.entity.Land;
import com.test.automatic.irrigation.system.entity.Plot;
import com.test.automatic.irrigation.system.entity.SensorDevice;
import com.test.automatic.irrigation.system.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LandController {
    @Autowired
   private LandService landService;

    @PostMapping("/lands")
	public ResponseEntity <Land> addLand(@RequestBody Land land) {
        return new ResponseEntity<>(landService.addLand(land.getLocation()), HttpStatus.CREATED);
	}

    @PostMapping("/lands/{id}/plots")
    public ResponseEntity <Plot> addPlot(@RequestBody CreatePlotDto body, @PathVariable(value="id") Long id) {
        return new ResponseEntity(landService.addPlot(body, id), HttpStatus.CREATED);
    }

    @PatchMapping("/plots/{id}/configure")
    public ResponseEntity<Plot> configurePlot(@RequestBody PlotConfigurationDto body, @PathVariable(value="id") Long id) {
        return new ResponseEntity(landService.configurePlot(id, body), HttpStatus.OK);
    }

    @GetMapping("/plots")
    public ResponseEntity<List<Plot>> getPlots() {
        return new ResponseEntity(landService.getPlots(), HttpStatus.OK);
    }

    @PostMapping("/plots/{id}/device")
    public ResponseEntity <SensorDevice> addDevice(@RequestBody CreateDeviceDto body, @PathVariable(value="id") Long id) {
        return new ResponseEntity(landService.addDevice(id, body), HttpStatus.CREATED);
    }
}