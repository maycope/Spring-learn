package cn.maycope.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */


@ApiModel(value = "Article", description = "文章实体对象")
public class Article {


    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id; //文章ID
    @ApiModelProperty(name = "name", value = "文章名称", example = "Swagger", required = true)
    private String name; //文章名称
    @ApiModelProperty(name = "title" ,value = "文章标题", example = "SpringBoot中使用Swagger", required = true)
    private String title; //文章
    @ApiModelProperty(name = "createTime", value = "创建时间", required = false)
    private Date createTime; //创建时间

    public Article() {
    }
    public Article(Long id, String name, String title, Date createTime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
