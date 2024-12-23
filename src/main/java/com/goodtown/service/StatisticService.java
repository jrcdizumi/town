package com.goodtown.service;

import com.goodtown.utils.Result;

public interface StatisticService {
    Result getStatistics(String startDate, String endDate, String region);
}