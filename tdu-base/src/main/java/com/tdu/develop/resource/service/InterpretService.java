package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Interpret;

import java.util.List;


public interface InterpretService {

    public  void addInterpret(Interpret interpret);

    public List<Interpret> getInterResultList(String nandu,  String page,String userKey,String pageKey);

}
