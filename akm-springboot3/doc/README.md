# AKM Spring Boot 3 项目文档

欢迎查阅 AKM Spring Boot 3 项目文档，这里包含了项目开发、配置和部署的完整指南。

## 📚 文档目录

### 🚀 快速开始

| 文档 | 说明 |
|------|------|
| [../README.md](../README.md) | 项目介绍和快速开始指南 |
| [常见问题.md](./常见问题.md) | FAQ 常见问题解答 |

### 📖 开发指南

| 文档 | 说明 |
|------|------|
| [开发规范.md](./开发规范.md) | 代码规范、命名规范、最佳实践 |
| [API开发指南.md](./API开发指南.md) | API 开发规范和示例 |
| [MyBatis开发技巧.md](./MyBatis开发技巧.md) | MyBatis XML 编写工具和技巧 |

### ⚙️ 配置指南

| 文档 | 说明 |
|------|------|
| [数据库配置.md](./数据库配置.md) | MySQL / PostgreSQL / DaMeng 配置 |
| [缓存配置.md](./缓存配置.md) | Redis / 本地缓存配置和使用 |
| [文件存储.md](./文件存储.md) | Minio 文件存储配置和使用 |
| [配置中心.md](./配置中心.md) | Spring Cloud Config 配置中心 |
| [配置刷新.md](./配置刷新.md) | 配置动态刷新指南 |

### 🛠️ 工具和脚本

| 文件 | 说明 |
|------|------|
| [test-config-refresh.sh](./test-config-refresh.sh) | 配置刷新测试脚本 |

## 📋 文档分类

### 新手入门

如果你是第一次接触本项目，建议按以下顺序阅读：

1. [../README.md](../README.md) - 了解项目概况
2. [开发规范.md](./开发规范.md) - 熟悉开发规范
3. [数据库配置.md](./数据库配置.md) - 配置数据库
4. [API开发指南.md](./API开发指南.md) - 开始开发第一个接口
5. [常见问题.md](./常见问题.md) - 遇到问题时查阅

### 环境配置

配置开发或生产环境：

- [数据库配置.md](./数据库配置.md) - 配置 MySQL/PostgreSQL/DaMeng
- [缓存配置.md](./缓存配置.md) - 配置 Redis 或本地缓存
- [文件存储.md](./文件存储.md) - 配置 Minio 文件服务
- [配置中心.md](./配置中心.md) - 配置 Spring Cloud Config

### API 开发

开发新的 API 接口：

- [开发规范.md](./开发规范.md) - 代码规范和命名规范
- [API开发指南.md](./API开发指南.md) - API 开发流程和示例
- [缓存配置.md](./缓存配置.md) - 使用缓存优化性能
- [文件存储.md](./文件存储.md) - 实现文件上传下载

### 运维部署

生产环境部署和运维：

- [配置中心.md](./配置中心.md) - 统一配置管理
- [配置刷新.md](./配置刷新.md) - 动态更新配置
- [数据库配置.md](./数据库配置.md) - 数据库优化和备份
- [常见问题.md](./常见问题.md) - 故障排查

## 🔍 快速查找

### 我想...

**配置数据库**
→ [数据库配置.md](./数据库配置.md)

**使用 Redis 缓存**
→ [缓存配置.md](./缓存配置.md)

**上传/下载文件**
→ [文件存储.md](./文件存储.md)

**开发新接口**
→ [API开发指南.md](./API开发指南.md)

**编写 MyBatis XML**
→ [MyBatis开发技巧.md](./MyBatis开发技巧.md)

**配置不重启更新**
→ [配置刷新.md](./配置刷新.md)

**解决启动问题**
→ [常见问题.md](./常见问题.md)

**了解代码规范**
→ [开发规范.md](./开发规范.md)

**接入配置中心**
→ [配置中心.md](./配置中心.md)

## 📞 获取帮助

### 文档没有解决我的问题

1. **查看日志**：`logs/spring.log`
2. **查看 Swagger 文档**：http://localhost:33000/doc.html
3. **提交 Issue**：[GitHub Issues](https://github.com/your-org/akm-springboot3/issues)
4. **联系技术支持**：support@example.com

### 如何贡献文档

如果您发现文档有误或需要补充：

1. Fork 项目
2. 修改文档
3. 提交 Pull Request

## 📝 文档更新日志

| 日期 | 说明 |
|------|------|
| 2024-01-xx | 创建完整项目文档 |
| - | 添加开发规范、API 开发指南 |
| - | 添加数据库、缓存、文件存储配置文档 |
| - | 添加配置中心和配置刷新文档 |
| - | 添加常见问题解答 |

## 📚 相关资源

### 官方文档

- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Cloud Config 文档](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
- [MyBatis 文档](https://mybatis.org/mybatis-3/zh/index.html)
- [Redis 文档](https://redis.io/documentation)
- [Minio 文档](https://min.io/docs/minio/linux/index.html)

### 技术社区

- [Spring 中文社区](https://spring.io/community)
- [MyBatis 中文网](http://www.mybatis.cn/)
- [Hutool 官网](https://hutool.cn/)

---

**💡 提示**：建议收藏本页面，方便快速查找所需文档。

