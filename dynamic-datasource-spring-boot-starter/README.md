# 用springboot-start的方式, 写一个动态数据源切换的starter
META-INF下面的spring.factories文件里  
EnableAutoConfiguration尝试自动配置,只要对应的依赖出现在classpath里  
这是通过@ConditionalOnClass注解来实现的  
@EnableConfigurationProperties里指明的类, 就是配置类, 可以在自己的配置文件里覆盖配置  
**创建自定义的starter需要2个组件**
1. 一个自动配置类, 和配置属性类
2. starter pom 用来引进依赖和自动配置项目

最后创建spring.factories, 指明自动配置类. 即可