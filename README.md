# AKM 企业级管理系统

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.5-brightgreen.svg)](https://vuejs.org/)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

基于 **Spring Boot 3** + **Vue 3** 的前后端分离企业级管理系统，采用现代化技术栈，支持多租户、多数据库、分布式部署。

## ✨ 项目概述

本项目是一个完整的企业级后台管理系统解决方案，包含后端服务和前端管理界面：

- **后端服务**：基于 Spring Boot 3 + Java 17，提供强大的 RESTful API
- **前端界面**：基于 Vue 3 + Element Plus，提供现代化的管理界面
- **配置中心**：Spring Cloud Config 统一配置管理（可选）
- **数据库脚本**：开箱即用的数据库初始化脚本

## 📦 项目结构

```
akm-springboot3-vue3/
├── akm-springboot3/              # 后端服务 (Spring Boot 3)
│   ├── src/                      # 源代码
│   ├── doc/                      # 后端文档
│   ├── pom.xml                   # Maven 配置
│   └── README.md                 # 后端详细说明
│
├── akm-vue3-web/                 # 前端管理界面 (Vue 3)
│   ├── src/                      # 源代码
│   ├── doc/                      # 前端文档
│   ├── package.json              # NPM 配置
│   └── README.md                 # 前端详细说明
│
├── akm-springboot3-config/       # 配置中心服务 (可选)
│   ├── src/                      # 配置中心源码
│   └── pom.xml                   # Maven 配置
│
└── akm-springboot3-db/           # 数据库脚本
    ├── mysql/                    # MySQL 初始化脚本
    │   └── akm_springboot.sql
    └── README.md                 # 数据库说明
```

## 🚀 核心特性

### 后端特性

- ✅ **Spring Boot 3.5.7** - 最新 Spring 框架
- ✅ **Java 17** - Records、Switch表达式等新特性
- ✅ **多数据库支持** - MySQL / PostgreSQL / DaMeng（达梦）
- ✅ **灵活缓存** - Redis / 本地缓存自由切换
- ✅ **配置中心** - Spring Cloud Config 统一配置
- ✅ **动态刷新** - 支持配置热更新
- ✅ **文件存储** - Minio 分布式对象存储
- ✅ **安全增强** - Jasypt 加密、参数签名、敏感词过滤
- ✅ **API 文档** - SpringDoc OpenAPI 自动生成
- ✅ **Magic API** - 在线编写接口
- ✅ **数据权限** - 多租户架构支持

### 前端特性

- ✅ **Vue 3.5** - Options API 风格
- ✅ **Vite 6** - 极速的开发体验
- ✅ **Element Plus 2.9** - 企业级 UI 组件
- ✅ **Pinia** - 现代化状态管理
- ✅ **全局组件** - 封装常用业务组件
- ✅ **权限管理** - 菜单、按钮级权限控制
- ✅ **安全加密** - 请求加密和签名验证
- ✅ **代码规范** - ESLint + Prettier + Oxlint

## 🛠️ 技术栈

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.7 | 核心框架 |
| Spring Cloud | 2025.0.0 | 微服务套件 |
| Java | 17 | 开发语言 |
| MyBatis | 3.0.5 | 持久层框架 |
| MySQL | 8.0+ | 主数据库 |
| Redis | 6.0+ | 缓存/分布式锁 |
| Minio | 8.6.0 | 对象存储 |
| Hutool | 5.8.41 | 工具库 |
| SpringDoc | 2.8.13 | API 文档 |
| Magic API | 2.2.2 | 动态接口 |

### 前端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.5.x | JavaScript 框架 |
| Vite | 6.x | 构建工具 |
| Element Plus | 2.9.x | UI 组件库 |
| Pinia | 3.x | 状态管理 |
| Vue Router | 4.x | 路由管理 |
| Axios | 1.9.x | HTTP 请求 |
| ECharts | 5.x | 可视化图表 |
| SCSS | - | CSS 预处理 |

## 🚀 快速开始

### 前置要求

- **后端**：JDK 17+、Maven 3.6+、MySQL 8.0+ / PostgreSQL 14+ / DaMeng 8.1+
- **前端**：Node.js >= 18.0.0、npm >= 9.0.0
- **可选**：Redis 6.0+、Minio（文件存储）

### 1. 数据库初始化

```bash
# 进入数据库脚本目录
cd akm-springboot3-db/mysql

# 创建数据库
mysql -u root -p -e "CREATE DATABASE akm_springboot CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 导入数据
mysql -u root -p akm_springboot < akm_springboot.sql
```

### 2. 后端服务启动

```bash
# 进入后端目录
cd akm-springboot3

# 修改配置文件 src/main/resources/application-dev.yaml
# 配置数据库连接信息

# 启动服务（开发模式）
mvn spring-boot:run

# 或打包运行
mvn clean package -DskipTests
java -jar target/akm-springboot3-0.0.1-SNAPSHOT.jar
```

**后端访问地址：**
- API 服务：http://localhost:33000
- API 文档：http://localhost:33000/doc.html
- Magic API：http://localhost:33000/magic/web/index.html
- Druid 监控：http://localhost:33000/druid/index.html

### 3. 前端服务启动

```bash
# 进入前端目录
cd akm-vue3-web

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

**前端访问地址：**
- 前端界面：http://localhost:15137

### 4. 配置中心启动（可选）

```bash
# 进入配置中心目录
cd akm-springboot3-config

# 启动配置中心
mvn spring-boot:run
```

**配置中心地址：**
- 配置服务：http://localhost:33033

## 📖 文档导航

### 后端文档

详细文档请查看 [akm-springboot3/README.md](./akm-springboot3/README.md) 和 [akm-springboot3/doc](./akm-springboot3/doc) 目录：

| 文档 | 说明 |
|------|------|
| [后端 README](./akm-springboot3/README.md) | 后端详细介绍和快速开始 |
| [开发规范](./akm-springboot3/doc/开发规范.md) | 项目开发规范和最佳实践 |
| [API开发指南](./akm-springboot3/doc/API开发指南.md) | 接口开发规范和示例 |
| [数据库配置](./akm-springboot3/doc/数据库配置.md) | 多数据库配置说明 |
| [缓存配置](./akm-springboot3/doc/缓存配置.md) | Redis/本地缓存配置 |
| [配置中心](./akm-springboot3/doc/配置中心.md) | Spring Cloud Config 使用 |
| [配置刷新](./akm-springboot3/doc/配置刷新.md) | 动态配置刷新指南 |
| [文件存储](./akm-springboot3/doc/文件存储.md) | Minio 文件存储配置 |
| [MyBatis开发技巧](./akm-springboot3/doc/MyBatis开发技巧.md) | MyBatis XML 编写工具推荐 |
| [常见问题](./akm-springboot3/doc/常见问题.md) | FAQ 常见问题解答 |

### 前端文档

详细文档请查看 [akm-vue3-web/README.md](./akm-vue3-web/README.md) 和 [akm-vue3-web/doc](./akm-vue3-web/doc) 目录：

| 文档 | 说明 |
|------|------|
| [前端 README](./akm-vue3-web/README.md) | 前端详细介绍和快速开始 |
| [项目结构说明](./akm-vue3-web/doc/项目结构说明.md) | 前端项目结构和目录说明 |
| [全局组件](./akm-vue3-web/doc/全局组件.md) | 封装的全局可复用组件 |
| [工具类](./akm-vue3-web/doc/工具类.md) | 常用工具函数和方法 |
| [自定义指令](./akm-vue3-web/doc/自定义指令.md) | Vue 自定义指令使用说明 |
| [状态管理](./akm-vue3-web/doc/状态管理.md) | Pinia 状态管理使用指南 |
| [开发规范](./akm-vue3-web/doc/开发规范.md) | 前端代码开发规范 |

## 💡 开发建议

### 开发环境配置

**后端开发：**
- 推荐使用 IntelliJ IDEA
- 安装 MyBatisCodeHelper Pro 插件（自动生成 MyBatis XML）
- 开发环境可使用本地缓存（`cacheType: local`），无需 Redis

**前端开发：**
- 推荐使用 VSCode + Volar 或 WebStorm
- 启用 ESLint 和 Prettier 自动格式化
- 使用 Vue DevTools 进行调试

### 调试技巧

**后端调试：**
```yaml
# 开启 SQL 日志打印
akm:
  mybatis:
    printSql: true

# 查看配置来源
curl http://localhost:33000/demo/config/open/info

# 查看健康状态
curl http://localhost:33000/actuator/health
```

**前端调试：**
```
# 开启请求日志打印（用于接口加密后的调试）
http://localhost:15137/?log=1

# 查看后台运行日志（需超管权限）
http://localhost:15137/log/run
http://localhost:15137/log/error
```

## 📝 开发规范

### API 接口规范

```
/xxx/view/{method}    - 查询接口
/xxx/op/{method}      - 操作接口（新增、编辑、删除）
/xxx/open/{method}    - 开放接口（不需要权限）
/xxx/public/{method}  - 公共接口（仅验证登录）
```

### 提交规范

```
[类型] 简短描述

类型：
- feat: 新功能
- fix: 修复 bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试相关
- chore: 构建/工具链
```

示例：
```
feat: 添加用户导出功能
fix: 修复登录超时问题
docs: 更新 API 开发指南
```

## ⚠️ 注意事项

1. **生产环境务必使用 Redis 缓存**，不要使用本地缓存
2. **敏感配置必须加密**，使用 Jasypt 加密密码等信息
3. **定期备份数据库**，建议每天自动备份
4. **关注安全更新**，及时更新依赖版本
5. **前后端分离部署**，注意跨域配置
6. **生产环境关闭调试日志**，避免敏感信息泄露

## 🔒 安全特性

- ✅ **配置加密** - Jasypt 加密敏感配置
- ✅ **参数签名** - 接口参数签名验证
- ✅ **加解密** - 请求/响应体加解密
- ✅ **敏感词过滤** - 防止 SQL 注入
- ✅ **频率限制** - 接口访问频率控制
- ✅ **CSRF 防护** - CSRF Token 校验
- ✅ **权限控制** - 基于角色的权限管理

## 🤝 参与贡献

欢迎提交 Issue 和 Pull Request！

1. Fork 本项目
2. 创建特性分支：`git checkout -b feature/AmazingFeature`
3. 提交更改：`git commit -m 'feat: Add some AmazingFeature'`
4. 推送分支：`git push origin feature/AmazingFeature`
5. 提交 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Element Plus](https://element-plus.org/)
- [MyBatis](https://mybatis.org/)
- [Hutool](https://hutool.cn/)

---

**⭐ 如果这个项目对你有帮助，请给个 Star！**

