# Backend Docker

这个目录用于存放后端本地依赖的 `docker compose` 文件。

当前提供：

- `compose.yml`：快速启动 `MySQL 8` 和 `Nacos 2`

## 启动

```bash
cd /Users/wangyu/local/workspace/silver-wisdom/backend/docker
docker compose up -d
```

## 停止

```bash
cd /Users/wangyu/local/workspace/silver-wisdom/backend/docker
docker compose down
```

如果需要连带删除 MySQL 数据卷：

```bash
cd /Users/wangyu/local/workspace/silver-wisdom/backend/docker
docker compose down -v
```

## 默认配置

- MySQL
  - Host: `127.0.0.1`
  - Port: `3306`
  - Database: `silver_wisdom`
  - Username: `root`
  - Password: `root`
- Nacos
  - Console: [http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/)
  - Port: `8848`
  - 单机模式启动
  - 已关闭鉴权，便于本地开发
  - 数据目录和日志目录已挂载到 Docker 命名卷，支持持久化

## 初始化说明

MySQL 首次启动时会自动执行：

- [V1__init_schema.sql](/Users/wangyu/local/workspace/silver-wisdom/backend/docs/sql/V1__init_schema.sql:1)

如果数据库卷已经存在，初始化脚本不会再次自动执行；这时可以先执行 `docker compose down -v` 再重新启动。

## 持久化说明

- MySQL 数据使用命名卷 `mysql-data`
- Nacos 数据使用命名卷 `nacos-data`
- Nacos 日志使用命名卷 `nacos-logs`

以下操作不会删除数据：

- `docker compose restart`
- `docker compose down`
- `docker compose up -d`

以下操作会删除持久化数据：

- `docker compose down -v`
- 手动删除对应 Docker 卷
