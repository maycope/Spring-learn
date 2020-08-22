
# SpringBoot整合Swagger

## 前言
在我们的日常学习与开发中，当完善了一个具体的功能之后，都会想要本能的进行接口的测试，测试数据的传输和
相应的功能是否都正常，在之前我们可能会使用到`postman`进行请求的模拟操作，发送对应的请求信息。而现在的`Swagger`就是
号称世界上最好的API框架，可以实现API文档与API定义同步更新，帮助我们来实现测试接口的功能。
其也与SpringBoot进行了深度的结合与自动化结合，帮助我们在项目开发完善之后能够进行自我检测。

下面就会来具体讲解`SpringBoot`与`Swagger2`的具体结合部署。

### 基础配置
当前版本：
* Spring-Boot： 2.3.3.RELEASE
* Swagger 2.9.2 
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
```
