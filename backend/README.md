# silver-wisdom backend

## 模块说明
- silver-common: 公共模型与异常处理
- silver-gateway: 网关服务
- silver-user: 用户服务
- silver-content: 内容服务
- silver-course: 课程服务

## 本地启动
在 backend 目录执行：

```bash
mvn clean package
mvn -pl silver-gateway spring-boot:run
mvn -pl silver-user spring-boot:run
mvn -pl silver-content spring-boot:run
mvn -pl silver-course spring-boot:run
```
