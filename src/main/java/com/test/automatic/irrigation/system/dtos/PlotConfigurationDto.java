package com.test.automatic.irrigation.system.dtos;

import java.sql.Time;
import java.util.List;

public class PlotConfigurationDto {
    public List<SlotConfiguration> slots;
}

//{ "slots": [{ "startTime": "11:00", duration: 10, "amount": 2}, {...}]}