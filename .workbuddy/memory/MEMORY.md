# Silver Wisdom 项目长期记忆

## 项目概况
- 项目名：银发知识付费平台（silver-wisdom）
- 技术栈：JDK 17、Spring Boot、Nacos、Redis、Sa-Token
- 模块：silver-common / silver-gateway / silver-user / silver-content / silver-course
- 小程序端（微信小程序，已确认不做独立 APP）+ 管理端（Web，Vue 3 + Element Plus）

## 产品需求文档
- 2026-04-15：根据思维导图生成 PRD v1.0，存放于工程根目录
  - 文件：银发知识付费平台_PRD_v1.0.docx
  - 覆盖内容：管理端（热点管理、知识分类、预置内容）+ 小程序端（AI问答、热门分类、用户画像、智能推荐、生成内容付费、学习模块）
  - 里程碑：Phase1（T+6周 MVP）/ Phase2（T+10周）/ Phase3（T+16周）
  - 预置知识分类：二次元、宠物、小说

- 2026-04-15：根据 Open Issues 确认结果更新为 PRD v1.1
  - 文件：银发知识付费平台_PRD_v1.1.docx
  - 主要变更：
    1. AI 供应商：支持多供应商配置，按权重负载均衡 + 失败自动重试（最多3次），熔断保护，热更新
    2. 定价策略：暂搁置，后续迭代确定
    3. 前端形态：确认为微信小程序，不做独立 APP
    4. 内容来源：通过 AI Tool 搜索外部内容自动采集、摘要入库，运营审核后发布（非人工预置/PGC）
    5. 用户授权合规：依托微信小程序隐私协议，不做额外合规机制
  - 新增章节：AI 多模型网关设计（多供应商注册、负载均衡、失败重试、熔断保护、可观测性、热更新）
  - silver-content 模块新增职责：AI 搜索采集工具 + AI 多模型网关层

## 关键技术决策
- AI 网关层（silver-content 内或独立模块）统一管理所有大模型调用，前端不直连，API Key 不出服务端
- AI 供应商负载均衡策略：加权轮询（Weighted Round Robin）+ RPM 配额保护
- 内容采集：AI Tool 搜索 → 自动摘要/打标 → 运营审核 → 发布（替代手动预置）
