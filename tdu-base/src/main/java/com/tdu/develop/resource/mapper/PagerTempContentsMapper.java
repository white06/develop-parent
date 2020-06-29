package com.tdu.develop.resource.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author 志阳
 * @create 2019-08-27-9:36
 */
@Repository
public interface PagerTempContentsMapper {
    public Boolean delPageCont(String PagerTempId);
}
