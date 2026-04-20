# silver-wisdom 后端提示词

## 1. 技术基线
- JDK 必须使用 `17`。
- 默认技术栈使用 `Spring Boot`、`Nacos`、`Redis`、`Sa-Token`。
- 未经明确要求，不引入与上述技术栈冲突的替代方案。
- 默认沿用现有 Maven 多模块结构。

## 2. 模块边界
- `silver-common`：公共工具、通用配置、统一响应、常量、异常。
- `silver-gateway`：网关路由、统一鉴权入口、限流、跨域。
- `silver-user`：用户账号、登录态、权限。
- `silver-content`：内容发布、管理、查询。
- `silver-course`：课程信息、章节结构、课程状态。

## 3. 代码生成规范
- 新代码必须兼容 `JDK 17`。
- 默认采用清晰分层：`controller`、`service`、`service.impl`、`mapper`、`entity`、`dto`、`request`、`response`、`query`、`command`。
- `controller` 负责入参校验、调用编排、统一响应；`service` 负责业务语义；`mapper` 负责数据访问。
- 数据访问默认采用 `Mapper + InfraService + BusinessService` 三层：
  - `IXxxMapper`：MyBatis Mapper 接口，负责 SQL 映射与数据访问。
  - `IXxxInfraService extends IService<XxxEntity>`：基础数据访问接口。
  - `XxxInfraServiceImpl extends ServiceImpl<IXxxMapper, XxxEntity> implements IXxxInfraService`，并使用 `@Service` 标注。
  - `XxxService`：业务服务层，负责业务语义、事务、跨服务编排，不直接承载通用 CRUD。
- 通用 CRUD 与实体级查询优先放在 `InfraService`；复杂 SQL 放在 `Mapper`；业务编排放在业务 `Service`。
- 禁止把 `Mapper` 直接当成业务层使用。
- 仅跨模块复用的通用能力才沉淀到 `silver-common`，例如统一响应、异常基类、通用工具、基础配置；领域错误码、领域枚举、领域常量优先保留在各自业务模块。
- 分页或列表查询需明确查询对象、返回对象、排序字段及索引建议。
- 禁止直接使用 `throw new BusinessException(400, "标签不能为空")` 这类硬编码错误码和错误文案。
- 业务异常必须基于业务域统一定义错误码，例如 `UserErrorCodes`、`ContentErrorCodes`、`CourseErrorCodes`。
- `BusinessException` 应优先接收业务错误码对象、错误码枚举或统一异常定义，不直接传裸数字状态码。
- HTTP 状态码不能直接作为业务异常码；新增业务异常时，先补充对应领域的异常码定义，再在业务代码中引用。

## 4. 注释与对象规范
- 所有新建或修改的 Java 类、接口、枚举、方法，定义上方都必须补充规范 JavaDoc。
- 方法 JavaDoc 至少说明用途；有入参写 `@param`，有返回值写 `@return`，有异常写 `@throws`。
- 禁止空洞注释，如“方法说明”“TODO”“处理逻辑”等占位内容。
- `entity`、`pojo`、`dto`、`request`、`response`、`query`、`command` 等数据对象字段上方必须补充中文注释，准确描述业务含义。
- `entity`、`dto`、`request`、`response`、`query`、`command` 等常规数据对象，优先使用 `Lombok` 注解简化样板代码；非必要不要手写大段重复 `get/set`、构造方法。
- `VO` 不是默认通用命名；仅在明确表达展示聚合对象时使用，普通接口返回对象优先命名为 `XxxResponse`。
- 状态、枚举、布尔、时间、ID 等字段需明确取值语义或用途。

## 5. 对象转换规范
- `entity`、`dto`、`response`、`po`、`pojo`、`command`、`query` 等对象转换默认使用 `MapStruct`。
- 禁止使用 `BeanUtils.copyProperties`、`Spring BeanUtils`、`Hutool BeanUtil` 等属性拷贝方案。
- 禁止在业务代码中编写大段重复 `set/get` 拷贝逻辑。
- 转换逻辑统一放在明确的 `converter` 或 `assembler` 组件中，避免与 MyBatis `Mapper` 命名冲突。
- 忽略字段、枚举转换、时间转换等特殊规则必须显式声明。

## 6. 认证、缓存与配置
- 认证鉴权默认采用 `Sa-Token`，并明确登录、鉴权、权限校验位置。
- 缓存默认采用 `Redis`，并说明 key 设计、TTL、空值保护、击穿/穿透或一致性策略。
- 服务注册发现与配置管理默认采用 `Nacos`，并区分本地关闭与联调开启场景。
- 网关相关方案需明确粗粒度校验和细粒度鉴权的职责边界。

## 7. 接口与分布式约束
- 方案说明必须包含：模块归属、核心流程、关键接口、请求对象、响应对象、持久化对象。
- 接口设计应语义清晰、边界明确，避免一个接口承担多个业务动作。
- 表结构设计需说明主键、状态字段、审计字段、逻辑删除和必要索引。
- 状态流转需明确状态枚举、触发条件、幂等策略和非法状态处理。
- 跨服务调用需明确调用链路、发起方、被调方、同步/异步方式及失败补偿思路。
- 新增 Feign 或 RPC 调用时，需说明接口归属、超时、重试、降级或容错策略。
- 能在本服务闭环完成的逻辑，不默认拆成远程调用。

## 8. 交付要求
- 方案和代码示例必须给出可落地的类名、方法名、字段名，避免伪代码。
- 如果用户只问后端问题，不要输出前端组件、页面布局、CSS 或视觉规范。
- 如与用户当次明确指令冲突，以用户指令为最高优先级。
