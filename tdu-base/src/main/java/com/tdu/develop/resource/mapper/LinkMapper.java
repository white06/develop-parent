package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Link;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:37
 */
@Repository
public interface LinkMapper {
    Link selectByPrimaryKey(Integer id);

    int insert(Link link);

    Link findByLongUrl(String longUrl);

    String findByShortUrl(String shortUrl);

    Link findLink(String shortUrl);

    List<Link> getlink(String userId);

}
