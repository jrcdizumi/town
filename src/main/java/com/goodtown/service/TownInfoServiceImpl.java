package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodtown.mapper.PublicizeMapper;
import com.goodtown.mapper.TownInfoMapper;
import com.goodtown.pojo.TownInfo;
import com.goodtown.pojo.TownPromotional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TownInfoServiceImpl extends ServiceImpl<TownInfoMapper, TownInfo> implements TownInfoService{

    public String getTownList() {
        List<TownInfo> list = this.list();
        List<Map<String, Object>> provincesData = convertToThreeLevelFormat(list);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(provincesData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Map<String, Object>> convertToThreeLevelFormat(List<TownInfo> townInfoList) {
        // Group by province
        Map<String, List<TownInfo>> provinceGroup = townInfoList.stream().collect(Collectors.groupingBy(TownInfo::getProvinceName));

        List<Map<String, Object>> result = new ArrayList<>();

        for (Map.Entry<String, List<TownInfo>> provinceEntry : provinceGroup.entrySet()) {
            String provinceName = provinceEntry.getKey();
            List<TownInfo> citiesInProvince = provinceEntry.getValue();

            Map<String, Object> provinceMap = new HashMap<>();
            provinceMap.put("label", provinceName);

            // Group by city within the province
            Map<String, List<TownInfo>> cityGroup = citiesInProvince.stream().collect(Collectors.groupingBy(TownInfo::getCityName));

            List<Map<String, Object>> citiesData = new ArrayList<>();
            for (Map.Entry<String, List<TownInfo>> cityEntry : cityGroup.entrySet()) {
                String cityName = cityEntry.getKey();
                List<TownInfo> townsInCity = cityEntry.getValue();

                Map<String, Object> cityMap = new HashMap<>();
                cityMap.put("label", cityName);

                List<Map<String, Object>> townsData = townsInCity.stream().map(townInfo -> {
                    Map<String, Object> townMap = new HashMap<>();
                    townMap.put("label", townInfo.getTownName());
                    townMap.put("value", townInfo.getTownID());
                    return townMap;
                }).collect(Collectors.toList());

                cityMap.put("children", townsData);
                citiesData.add(cityMap);
            }

            provinceMap.put("children", citiesData);
            result.add(provinceMap);
        }

        return result;
    }
}
