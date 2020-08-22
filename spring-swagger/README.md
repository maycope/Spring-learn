# Spring-Boot整合Swagger

## 前言

在我们的日常学习与开发中，当完善了一个具体的功能之后，都会想要本能的进行接口的测试，测试数据的传输和
相应的功能是否都正常，在之前我们可能会使用到`postman`进行请求的模拟操作，发送对应的请求信息。而现在的`Swagger`就是
号称世界上最好的API框架，可以实现API文档与API定义同步更新，帮助我们来实现测试接口的功能。
其也与`SpringBoot`进行了深度的适配与自动化结合，帮助我们在项目开发完善之后能够进行自我检测。

下面就会来具体讲解`SpringBoot`与`Swagger2`的具体结合部署。

## 依赖注入

当前版本：

* Spring-Boot： 2.3.3
* Swagger 2.9.2 

首先是Swagger依赖的导入（此时是2020年8月21号，当前的swagger已经发行到3.0.0版本[Swagger-maven](https://mvnrepository.com/artifact/io.springfox/springfox-swagger2),但是对于2.9.2版本来说使用起来最稳定，所以暂时使用其作为演示操作）

```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.9.2</version>
</dependency>
​```
```

但是当我们出现：`java.lang.NumberFormatException: For input string: ""`，说是`example`设置问题，此时解决办法就是添加如下依赖：

```xml
<!-- 使用Swagger2.9.2避免NumberFormatException错误要引入下列两个依赖 -->
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-annotations</artifactId>
    <version>1.5.21</version>
</dependency>
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-models</artifactId>
    <version>1.5.21</version>
</dependency>
```

## 基础配置

### APiInfo

```java
    private ApiInfo apiInfo(){
        //contact 表示的是作者信息。
        return  new ApiInfoBuilder()
                .title("Spring Boot集成Swagger构建API 文档")
                .description("具体的源码地址参见")
                .termsOfServiceUrl("https://www.maycope.cn")
                .contact(new Contact("Maycope","https://www.maycope.cn",""))
                .version("1.0")
                .build();
    }
```

### Swagger 配置扫描接口

```java
  @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
.apis(RequestHandlerSelectors.basePackage("cn.maycope.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build();  
    }
```

**注意**：

* 对于Swagger来说，其`API`文档的生成是通过扫描指定的`package`下的对应的映射类，根据映射接口生成的`API`文档接口，所以在配置的时候，要注意对于`RequestHandlerSelectors.basePackage`要配置到我们具体的接收请求的具体类上。
* `paths(PathSelectors.regex("/.*"))`表示的是过滤，扫描请求的路径信息，配置`/.*` 表示的是接收所有的请求信息。

* ` RequestHandlerSelectors`表示要配置扫描接口的方式。

* `.basePackage()`表示要扫描指定的包。

* `.any()`表示的是扫描所有。

* `.none()` 表示都不扫描

##  接口注解讲解

### @APi

**解释**：用在我们`Controller`控制器类上，用于标识当前类的接口的具体信息，方便进行查询。

* value： 控制器类名称。
* tags: 控制器类标签。

**使用**：

```java
@Api(value = "ArticleController",tags = {"文章管理接口"})
public class ArticleController {
 ...
}
@Api(value = "HelloController",tags = {"测试管理接口"})
public class HelloController {
...
}
```

**展示**：

![image-20200821105456543](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200821105456543.png)

### @ApiOperation

**解释**：使用在具体的方法上面，来具体描述此方法的具体操作

* value： 接口说明，展示在接口列表上。
* notes： 接口详细说明，展示在接口的内部详情页上。
* 

**使用**：

```java
  @ApiOperation(value = "查询文章详情", notes = "文章ID大于0")
    public ResponseCode findById(@PathVariable Long id) {
        ...
    }
 @ApiOperation(value = "删除文章")
    public ResponseCode delete(@ApiParam("id信息")Long id) {
        ...
    }
```

**展示**：

![image-20200821110256087](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200821110256087.png)

### @ApiImplicitParam

**解释**：进行描述性的操作，描述一些请求的基础参数的信息。

* name： 参数名称。
* value： 参数说明。
* required： 参数是否是必须的。
* dataType： 参数类型。

**使用**：

```java
@ApiImplicitParam(name = "article", value = "文章信息实体", required = true, dataType = "Article", paramType = "body")
    }
```

**展示**：

![image-20200821112255236](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200821112255236.png)

> 实体类

### @ApiModel

**解释**：对整个类的属性的配置。

* value： 类的说明。
* description：详细描述。

**使用**：

```java
@ApiModel(value = "Article", description = "文章实体对象")
public class Article {
...
}
```

### @ApiModelProperty

**解释**：是对具体的实体类中的每一个属性具体的详解。

*  name： 字段名称。
*  value： 字段的说明。
*  required： 字段是否是必须的。
*  example： 示例值。
*  hidden： 字段是否显示：

**使用**：

```java
    @ApiModelProperty(name = "name", value = "文章名称", example = "Swagger", required = true)
    private String name; 
@ApiModelProperty(name = "title" ,value = "文章标题", example = "SpringBoot中使用Swagger", required = true)
    private String title;
```

**展示**：

![image-20200821140822136](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200821140822136.png)



## 数据库

因为本篇文章模拟在真实情况下的请求信息，有连接数据库，使用到`MyBatis`提供底层的数据库支持,首先是配置文件的具体配置，具体的依赖导入参见`pom.xml`配置文件。

```yml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring_swagger?characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

#mybatis配置
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: cn.maycope.Entity
  configuration:
    # 使用jdbc的getGeneratedKeys 可以获取数据库自增主键值
    use-generated-keys: true
    # 开启驼峰命名转换，如：Table(create_time) -> Entity(createTime)。不需要我们关心怎么进行字段匹配，mybatis会自动识别`大写字母与下划线`
    map-underscore-to-camel-case: true
```

完成以上准备之后，建立相关联的数据库，具体的建库语句如下：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(200) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```



## 代码部分

### 所有的请求信息

首先我们需要知道对于`Swagger`来说，是必须要遵循`Resultful`的接口的规范，其实这些请求信息也就是我们平时使用到的几种类型，这里我们的`hello`接口来说，并没有具体设置`@PutMapping`或者`@PostMapping`,而是直接使用到了一个`@RequestMapping("/hello")`一下就可以看出来具体的请求情况，因为我们没有进行具体的指定操作，所以所有的请求都会有：见下图所示：

![image-20200822102328071](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822102328071.png)

对于具体请求的具体含义：

| 请求方法  |                   具体含义                   |
| :-------: | :------------------------------------------: |
|    Get    |  向请求获取某一个某个路径下的具体资源信息。  |
|   Post    |           向服务端请求添加新的资源           |
|    Put    |       向服务端请求修改某个已存在的资源       |
|  Delete   |       向服务端请求删除某个已存在的资源       |
|   Head    |       用来获取某个资源的`metadata`信息       |
| Optitions | 是客户端向服务端判断对某个资源是否有访问权限 |
|   Patch   |        某个资源做局部修改,如个别字段         |

### 具体的请求信息

一般我们在编写业务端`Controller`代码时候对于`RestController`来说是`@ResponseBody`+`@Controller`的结合体，其控制器类下的所有方法的返回值都将被Spring转换成`JSON`格式。对于具体的`mapping`请求来说，我们一般都会根据具体的请求的情况进行具体逻辑的编写：

#### GetMapping

```java
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
```

#### PostMapping



```java
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
```

后续的具体操作见源码部分。

### 返回类的具体封装

这里我们模拟对于一篇文章的`增删改查`操作,并且连接数据库进行操作，就需要考虑到不同的情况：

1. 在根据具体的ID信息查找具体的文章时候，在文章的不存在时候，需要返回具体的错误信息：文章不存在。
2. 在进行新添时候，若是必要的字段`name`或者`title`不存在时候，也要具体返回错误信息：文章名称或内容不存在。
3. 在正确执行查找时候，就需要我们返回正确的状态码，并且将文章信息展示出来，在执行完成提交和修改之后都需要进行具体信息的提示。

所以综上所述，进行了具体返回类的封装处理：

#### ResponseCode

```java
public class ResponseCode implements Serializable {

    private int  code;
    private String message;
    private Object date;
    //... get与set方法
    // 成功时候 不带参数
        public static  ResponseCode success(){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(ResultCode.SUCCESS);
        return  responseCode;
    }
    // 失败时候 不带参数
    public static  ResponseCode failuer(){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(ResultCode.FAIL);
        return  responseCode;
    }
    // 具体可以参见 ResponseCode 源码部分。
}
```

#### ResultCode

当然上面是我们具体返回的类型，但是我们还需要根据具体的情况，定制不同的状态码和提示信息：

```java
public enum ResultCode {
    // 定义各种的具体返回信息。
    SUCCESS(2000,"成功"),
    INSERT_SUCCESS(2001,"文章插入成功"),
    UPDATE_SUCCESS(2002,"文章修改成功"),
    FAIL(5001,"失败"),
    RESULE_DATA_NONE(5002, "数据未找到"),
    ID_IS_ERROR(5003,"id有误"),
    ARTICLE_IS_ERROR(5004,"文章不存在"),
    NAME_OR_TITLED_D(5005,"文章名称或内容不存在"),
    ARTICLE_IS_TRUE(5006,"文章已经存在，请勿重复提交");
    
    private Integer code;
    private String message;
    ...
}
```



### 具体业务讲解（部分）

首先还是熟悉的`SpringBoot`三层架构：

![image-20200822105900915](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822105900915.png)

首先对于我们的Swagger来说，在我们完成了基础的配置之后（完成了一个`config`配置外加一个接收请求的类之后，就可访问`http://localhost:8080/swagger-ui.html`）

![image-20200822110144431](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822110144431.png)

对于测试接口管理可以自行进行逻辑的增加，供大家自行学习使用。

对于在上面写定的API注解，对应在图上的如下位置：

![image-20200822111005431](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822111005431.png)

对于我们的Swagger是如何来模拟我们的具体请求呢？是何如进行具体的操作呢？

#### 模拟请求发送

1. 点击`try it out`

![image-20200822111214972](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822111214972.png)

2. 填写发送的请求信息：

![image-20200822111423303](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822111423303.png)

3. 下拉当前页面，点击`Execute`

![image-20200822111340423](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822111340423.png)

4. 查看自定义的返回信息

![image-20200822111515176](https://gitee.com/maycopes/MyImages/raw/master//images/image-20200822111515176.png)

其中还有更多的请求信息，可以自定下载源码进行学习与掌握。

#### 其他

其实对于`Execute`来说就是相当于我们自行编写前端页面发送请求走到后端，后端再进行处理的全过程。只不过帮助我们完成信息的请求，也就是`Postman`同样的功能，当我们知道具体的请求`URL`，再填写必须的参数即可。

## 后记

1. 对于Swagger来说实在是简单到没有什么具体可以讲，主要就是配置文件`cn.maycope.config.SwaggerConfig`下具体配置信息配置完善即可。
2. 要遵循`Resultful`接口规范，不同的方法对应上不同的`mapping`请求，对应的返回信息注意Json格式的转换即可。
3. 源码地址见个人的`GitHub`仓库，最近在推出系列的`SpringBoot-`,若是感觉有帮助可以点个小小的 `star`,有什么不对的地方也希望大家能够严厉指出，我也一定虚心听教。有什么不明白的地方可以私信我，一般24小时以内都能给予回复。