package com.muyu.mapper;

import com.muyu.pojo.ImitatePosition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 牧鱼
 * @Classname ImitatePositionMapper
 * @Description TODO
 * @Date 2021/9/15
 */
@Mapper
public interface ImitatePositionMapper {
    List<ImitatePosition> selectAll();

    String selectPositionByPositId(Long imitatePositionId);
}
