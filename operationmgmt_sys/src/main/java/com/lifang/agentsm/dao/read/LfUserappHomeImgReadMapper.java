package com.lifang.agentsm.dao.read;

import com.lifang.agentsm.entity.LfUserappHomeImg;

public interface LfUserappHomeImgReadMapper {

    LfUserappHomeImg selectByPrimaryKey(Long id);

    LfUserappHomeImg selectByTypeLimitOne(Integer id);
}