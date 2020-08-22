package cn.maycope.mapper;

import cn.maycope.Entity.Article;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Date;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */


public interface ArticleMapper {

     Article getArticleById(Long id);

     int deleteById(Long id);

     int increase( @Param("name") String name,@Param("title") String title,@Param("date") Date date);

     Article findByNameAndTitle(@Param("name") String name, @Param("title") String title);

     Article selectByName(String name);

     int update(@Param("name") String name,@Param("title") String title,@Param("date") Date createTime);
}
