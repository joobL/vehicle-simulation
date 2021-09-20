package com.muyu.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @program: VehicleSimulation
 * @description:
 * @author: dongzeliang
 * @create: 2021-09-20 22:22
 **/
@Mapper
public interface VehicleDataMapper {

    void updateVehicleData();
}
