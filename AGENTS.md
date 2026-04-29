# silver-wisdom 项目级提示词

## 1. 使用方式
- 本文件只承担"项目共性约束 + 目录路由规则"，不要在这里混入大量前端或后端专属细节。
- 当任务发生在某个子目录时，优先遵循对应子目录下的 `AGENTS.md`；根目录规则只作为公共基线。
- 禁止把前端视觉、交互、构建细节用于后端代码生成；也禁止把后端分层、数据库、接口约束用于前端页面生成。
- 如用户明确指定技术方案、目录范围或实现方式，以用户当次指令为最高优先级。

## 2. 项目结构路由
- `backend/`：后端多模块微服务工程，生成或修改后端代码时，必须优先读取并遵循 `backend/AGENTS.md`。
- `pc-admin/`：管理后台前端工程，生成或修改后台前端代码时，必须优先读取并遵循 `pc-admin/AGENTS.md`。
- `miniapp/`：小程序前端工程，生成或修改小程序代码时，必须优先读取并遵循 `miniapp/AGENTS.md`。
- 若任务跨越前后端多个目录，先按模块拆分输出，再分别遵循各目录提示词，不要用单一混合提示词一次性覆盖全部实现。

## 3. 项目公共约束
- 输出方案时需明确：模块归属、核心流程、关键接口、数据对象、边界条件。
- 代码示例必须给出可落地的类名、方法名、字段名或组件名，避免只写伪代码。
- 涉及跨模块协作时，先说明谁提供接口、谁消费接口、谁负责状态变更。
- 默认优先复用现有模块和公共能力，避免无必要新增重复组件、重复 DTO、重复工具类。
- 涉及固定范围取值的字段时，例如状态、类型、类别、分类、来源、端类型、业务标记等，业务代码优先使用领域枚举表达取值语义，避免散落魔法字符串或魔法数字；数据库层默认继续使用 `VARCHAR`、`INT` 等基础类型存储稳定值，并在持久化或转换层完成枚举与存储值映射，非必要不直接依赖数据库原生 `ENUM` 类型。

## 4. 跨端协作约束
- 后端输出应重点描述接口契约、鉴权、缓存、事务、一致性、异常码，不展开前端 UI 细节。
- 前端输出应重点描述页面结构、状态管理、交互流程、接口调用、容错展示，不擅自改写后端技术选型。
- 如果任务只是"定义接口"或"联调约定"，可在根目录层描述前后端契约，但实现细节仍回到各自子目录提示词。

## 5. 最佳实践
- 采用"1 个根提示词 + N 个子域提示词"的分层结构，而不是"一个超大万能提示词"。
- 根提示词解决项目认知和目录路由问题；子域提示词解决专业规则问题；这样能显著降低上下文污染和错误生成。
- 当新增新端或新服务时，在对应目录补充新的 `AGENTS.md`，不要持续膨胀根提示词。

## 6. 常用开发命令

### 后端（backend/）
```bash
# 全量构建
mvn clean package

# 启动单个服务
mvn -pl silver-gateway spring-boot:run
mvn -pl silver-user spring-boot:run
mvn -pl silver-content spring-boot:run
mvn -pl silver-course spring-boot:run

# 运行测试
mvn test
# 运行单个测试类
mvn test -Dtest=UserServiceTest
```
注意：启动服务前需确保 Nacos、Redis 已运行。

### 管理后台（pc-admin/）
```bash
npm install
npm run dev      # 开发模式
npm run build    # 构建生产版本
npm run preview  # 预览构建结果
```

### 小程序（miniapp/）
```bash
npm install
npm run typecheck  # 类型检查
```

## 7. 系统整体架构

### 技术栈
- **后端**：JDK 17, Spring Boot 3.2, Spring Cloud, Nacos, Redis, Sa-Token, MyBatis-Plus, MapStruct
- **管理后台**：Vue 3, Vite, Element Plus, UnoCSS, TypeScript
- **小程序**：微信小程序, TypeScript

### 服务模块
- **silver-gateway**：网关层，统一鉴权入口
- **silver-user**：用户服务，账号与权限
- **silver-content**：内容服务，发布与管理
- **silver-course**：课程服务，章节与学习
- **silver-common**：公共模块，工具类、统一响应、异常基类

### 调用链路
```
小程序/PC端 → Gateway → (User/Content/Course) → 数据库
                      ↓
                  Nacos（服务发现）+ Redis（缓存/会话）
```
