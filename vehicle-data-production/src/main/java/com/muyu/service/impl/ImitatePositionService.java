package com.muyu.service.impl;

import com.muyu.mapper.ImitatePositionMapper;
import com.muyu.pojo.ImitatePosition;
import com.muyu.service.ImitatePositionI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牧鱼
 * @Classname ImitatePosition
 * @Description TODO
 * @Date 2021/9/15
 */
@Service
public class ImitatePositionService implements ImitatePositionI {

    @Autowired
    private ImitatePositionMapper positionMapper;

    @Override
    public List<ImitatePosition> selectAll() {
        return positionMapper.selectAll();
    }
}
