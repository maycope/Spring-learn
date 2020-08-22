package cn.maycope.service.impl;

import cn.maycope.Entity.Article;
import cn.maycope.mapper.ArticleMapper;
import cn.maycope.service.ArtivleService;
import cn.maycope.util.ResponseCode;
import cn.maycope.util.ResultCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */

@Service
public class articleServiceIMPL implements ArtivleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseCode findById(@Param("id")Long id) {
        if(id == null)
            return ResponseCode.failuer(ResultCode.ID_IS_ERROR);
        else {
            Article article = articleMapper.getArticleById(id);
            if(article == null)
                return  ResponseCode.failuer(ResultCode.ARTICLE_IS_ERROR);
            else
                return  ResponseCode.success(article);
        }
    }
    @Override
    public ResponseCode deleteById(Long id) {
        Article article = articleMapper.getArticleById(id);
        if(article == null)
            return  ResponseCode.failuer(ResultCode.ARTICLE_IS_ERROR);
        else
        {
            int count = articleMapper.deleteById(id);
            if(count != 0)
                return ResponseCode.success();
            else
                return ResponseCode.failuer();
        }
    }
    @Override
    public ResponseCode increase(Article article) {

        String name= article.getName();
        String title = article.getTitle();
        Article articles = articleMapper.findByNameAndTitle(name,title);
        if(name==null || title==null)
        {
            return  ResponseCode.failuer(ResultCode.NAME_OR_TITLED_D);
        }
        else if(articles!=null){
            return  ResponseCode.failuer(ResultCode.ARTICLE_IS_TRUE);
        }
        else
        {
            int count = articleMapper.increase(article.getName(),article.getTitle(),article.getCreateTime());
            if(count!= 0)
                return ResponseCode.success(ResultCode.INSERT_SUCCESS,article);
            else
                return ResponseCode.failuer();
        }
    }
    @Override
    public ResponseCode update(Article article) {
        /**
         * 这里的update更新是根据我们的 name查询到具体的
         */
        Article article1 = articleMapper.selectByName(article.getName());
        if(article1 == null)
            return  ResponseCode.failuer(ResultCode.ARTICLE_IS_ERROR);
        else {
            int count = articleMapper.update(article.getName(),article.getTitle(),article.getCreateTime());
            if(count!=0)
                return  ResponseCode.success(ResultCode.UPDATE_SUCCESS,article);
            else
            return ResponseCode.failuer();
        }
    }
}
