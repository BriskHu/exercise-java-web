# Eureka 



## 遇到的坑

1. 
需要注意eureka引用的包是“spring-cloud-starter-netflix-eureka-server”，不是“spring-cloud-netflix-eureka-server”。
如果这里引错成“spring-cloud-netflix-eureka-server”，在启动时会报“The bean 'eurekaRegistration', defined 
in class path resource”错误。

**这个错误跟是否在pom文件中引用“spring-boot-starter”或者引用“spring-cloud-dependencies”没有关系。**
