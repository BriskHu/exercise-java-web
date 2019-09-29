此项目为使用本地配置文件资源的spring cloud config服务端程序。其他项目使用到
所有的配置文件都放在本项目中.

使用Eureka做为注册中心是，Eureka客户端一定要在pom中引入“spring-cloud-starter-netflix-eureka-client”依赖，不然
程序启动起来后，会注册不到eureka中心。这是比较坑的一点。另外，只要在启动上使用“@EnableDiscoveryClient”注解，不在
配置文件中使用“eureka.client.register-with-eureka=true”、“eureka.client.fetch-registry=true”也能正常注册到eureka。

