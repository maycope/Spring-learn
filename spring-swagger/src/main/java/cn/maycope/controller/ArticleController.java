package cn.maycope.controller;

import cn.maycope.Entity.Article;
import cn.maycope.service.ArtivleService;
import cn.maycope.util.ResponseCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */

@RestController
@RequestMapping("/article")
@Api(value = "ArticleController",tags = {"文章管理接口"})
public class ArticleController {


    @Autowired
    private ArtivleService artivleService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 文章的查询操作 使用到的是GET请求
     */
    @GetMapping(value = "/{id}",produces = "application/json")
    @ApiOperation(value = "查询文章的详情",notes = "文章的ID大于0")
    @ApiImplicitParam(name = "id",value = "文章编号",required = true, dataType = "Long")
    public ResponseCode  findById(@PathVariable Long id){
        ResponseCode responseCode  =artivleService.findById(id);
        if(responseCode.getCode()==2000)
            logger.info("文章查询成功,文章的id是==> {}",id);
        return   responseCode;
    }
    /**
     * 文章的删除操作，使用到 DELETE请求。
     */
    @DeleteMapping(value = "/{id}",produces = "application/json")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParam(name = "id",value = "文章编号",required = true,dataType = "Long")
    public ResponseCode deleteById(@PathVariable Long id)
    {
        ResponseCode responseCode  =artivleService.deleteById(id);
        if(responseCode.getCode()==2000)
        logger.info("文章删除成功，删除的文章的id为==> {}",id);
        return  responseCode;
    }
    /**
     * 文章的提交操作 post
     */
    @PostMapping(value ="/",produces = "application/json")
    @ApiOperation(value = "保存文章")
    @ApiImplicitParam(name = "article",value = "文章详情",required = true,dataType = "Article",paramType = "body")
    public  ResponseCode postArticle(@RequestBody Article article){
        ResponseCode responseCode  =artivleService.increase(article);
        if(responseCode.getCode()==2001)
            logger.info("文章提交成功，文章的内容是==> {}",article);
        return  responseCode;
    }
    /**
     * 文章的更新操作 put
     */
    @PutMapping(value = "/",produces = "application/json")
    @ApiOperation(value = "更新文章")
    @ApiImplicitParam(name = "article",value = "文章详情",required = true,dataType = "Article",paramType = "body")
    public  ResponseCode  putArticle(@RequestBody  Article article){
        ResponseCode responseCode  =artivleService.update(article);
        if(responseCode.getCode()== 2002)
            logger.info("文章更新成功过，文章的内容是==> {}",article);
        return  responseCode;
    }

}
