package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowlegcontent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KnowlegcontentMapper {
	
	public List<Knowlegcontent> seleAll(String id);
	
	public Knowlegcontent seleOne(String id);

	public void filein(Knowlegcontent knowlegcontent);
	
	public void deSel(String id);
	
	public void upcontent(@Param("name") String name, @Param("id") String id);
	
	public Integer seleContentNum();
	
	public List<Knowlegcontent> seleAllContent();
}
