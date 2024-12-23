package com.goodtown.controller;

import com.goodtown.service.StatisticService;
import com.goodtown.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public Result getStatistics(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String region) {
        return statisticService.getStatistics(startDate, endDate, region);
    }
}