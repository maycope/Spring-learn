package cn.maycope.service;

import cn.maycope.Entity.Article;
import cn.maycope.util.ResponseCode;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */



public interface ArtivleService {

    ResponseCode findById(Long id);

    ResponseCode deleteById(Long id);

    ResponseCode increase(Article article);

    ResponseCode update(Article article);
}
