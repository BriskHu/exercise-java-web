# Spring Cloud Config 

Spring cloud config是对不同组件使用的配置进行集中化管理。通常，基于eureka的spring cloud config的实现架构是：
一个配置管理服务组件（以下简称组件A），一个eureka注册中心服务组件（以下简称组件B），若干个具体的业务组件（以下
简称业务组件1-N）。组件A和业务组件均作为注册到eureka注册中心的客户端组件。

## 配置管理服务组件
这个组件具有以下特点：
1. 它是一个普通的web服务，通常基于springboot实现；它通过http/https请求向业务组件提供该业务组件的配置数据；
2. 它是一个Eureka客户端，它与业务组件注册到同一个服务注册中心；
3. 它的启动类须有“@EnableConfigServer”注解，这个注解表明了该组件是一个spring cloud config组件。

配置管理服务组件很简单，主要工作就是为各业务组件创建好响应的配置资源文件，然后启动这个组件，注册到Eureka就可以了。
业务组件会在运行时自动从组件A获取它的配置信息。**注意：** 组件A自身的配置文件中需要制定profiles为native的，即：“
spring.profiles.active=native”。

springboot支持两种配置资源文件格式：yml、properties。所有的资源文件默认应该放置在项目根路径下的“resources”文件
夹下。springboot支持三种资源文件的放置方式：

- resources/{application}/{profile}[/{label}]
- resources/{label}/{application}-{profile}.[yml|properties]
- resources/{application}-{profile}.[yml|properties]

其中，“{application}”是某业务组件的“spring.application.name”值，“{profile}”是该业务组件要使用程序运行环境名，
“[]”表示可选的内容。由此，只需要按照业务组件的名称建立文件夹，将其配置文件放进去即可。

组件A启动后，可以通过下面的方式验证是否可以正常获取到配置：

```
http://localhost:port/{application}/{profile}[/{label}]
```

比如，本例中获取orderservice组件prod环境配置的url为：“http://localhost:port/orderservice/prod”。



## 业务组件的设置
所有业务组件将配置文件放置到组件A的resource文件下，各业务组件通过在其项目中创建一个的“bootstrap.properties”文件
，并在这个文件中指明对组件A的调用地址，然后在业务组件中需要使用配置的地方，像引用在application.properties文件中
引用配置那样（即，在程序中使用“@Value("${configItemName}")”获取配置项的值）即可。这个bootstrap.properties文件
基础内容如下：

```
spring.application.name=appName
server.port=8888
spring.cloud.config.profile=dev
spring.cloud.config.uri=http://localhost:9002/
```





