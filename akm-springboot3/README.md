# AKM Spring Boot 3 后端管理系统

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

基于 Spring Boot 3 的企业级后端管理系统，采用多租户架构，支持多种数据库和分布式部署。

## 🚀 核心特性

- **🎯 Spring Boot 3.5.7** - 基于最新 Spring 6.2.12 框架
- **☕ Java 17** - 使用 Java 17 新特性（Records、Switch表达式等）
- **🗄️ 多数据库支持** - MySQL / PostgreSQL / DaMeng（达梦）
- **💾 灵活缓存** - 支持 Redis / 本地缓存自由切换
- **☁️ 配置中心** - Spring Cloud Config 统一配置管理
- **🔄 动态刷新** - 支持配置热更新，无需重启
- **📁 文件存储** - Minio 分布式对象存储
- **🔐 安全增强** - Jasypt 配置加密、参数签名、敏感词过滤
- **📊 API 文档** - SpringDoc OpenAPI 自动生成文档
- **🎨 Magic API** - 在线编写接口，无需重启
- **📈 监控管理** - Spring Boot Actuator 健康检查

## 📋 技术栈

| 技术 | 版本 | 说明 |
|-----|------|------|
| Spring Boot | 3.5.7 | 核心框架 |
| Spring Cloud | 2025.0.0 | 微服务套件 |
| Java | 17 | 开发语言 |
| MyBatis | 3.0.5 | 持久层框架 |
| PageHelper | 2.1.1 | 分页插件 |
| MySQL | 8.0+ | 主数据库 |
| PostgreSQL | 14+ | 可选数据库 |
| DaMeng | 8.1+ | 国产数据库 |
| Redis | 6.0+ | 缓存/分布式锁 |
| Minio | 8.6.0 | 对象存储 |
| Hutool | 5.8.41 | 工具库 |
| Lombok | - | 代码简化 |
| SpringDoc | 2.8.13 | API 文档 |
| Magic API | 2.2.2 | 动态接口 |

## 🏗️ 项目结构

```
akm-springboot3/
├── src/main/java/com/akm/springboot3/
│   ├── core/                    # 核心模块
│   │   ├── advice/             # 全局响应处理
│   │   ├── annotation/         # 自定义注解
│   │   ├── aop/                # AOP 切面
│   │   ├── config/             # 配置类
│   │   ├── constant/           # 常量定义
│   │   ├── datascope/          # 数据权限
│   │   ├── domain/             # 通用领域对象
│   │   ├── exception/          # 异常处理
│   │   ├── filter/             # 过滤器
│   │   ├── interceptor/        # 拦截器
│   │   └── utils/              # 工具类
│   ├── web/                    # Web 层
│   │   ├── biz/                # 业务模块
│   │   ├── demo/               # 示例模块
│   │   └── sys/                # 系统模块
│   └── file/                   # 文件处理模块
├── src/main/resources/
│   ├── mapper/                 # MyBatis XML
│   ├── application.yaml        # 主配置文件
│   ├── application-dev.yaml    # 开发环境配置
│   └── bootstrap.yaml          # Config 客户端配置
├── doc/                        # 项目文档
├── logs/                       # 日志目录
└── pom.xml                     # Maven 配置
```

## 🚀 快速开始

### 前置要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+ / PostgreSQL 14+ / DaMeng 8.1+
- Redis 6.0+（可选，使用本地缓存可跳过）
- Minio（可选，文件上传功能需要）

### 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd akm-springboot3
```

2. **配置数据库**
   - 创建数据库：`akm_springboot`
   - 执行 SQL 脚本（请联系管理员获取）

3. **配置应用**

编辑 `src/main/resources/application-dev.yaml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/akm_springboot
    username: root
    password: your_password

akm:
  cacheType: local  # 开发环境使用本地缓存
```

4. **启动应用**

```bash
# 开发模式
mvn spring-boot:run

# 或打包运行
mvn clean package -DskipTests
java -jar target/akm-springboot3-0.0.1-SNAPSHOT.jar
```

5. **访问应用**

- API 文档：http://localhost:33000/doc.html
- Magic API：http://localhost:33000/magic/web/index.html
- Druid 监控：http://localhost:33000/druid/index.html

## 📖 文档导航

详细文档请查看 [doc](./doc) 目录：

| 文档 | 说明 |
|------|------|
| [开发规范](./doc/开发规范.md) | 项目开发规范和最佳实践 |
| [数据库配置](./doc/数据库配置.md) | 多数据库配置说明 |
| [缓存配置](./doc/缓存配置.md) | Redis/本地缓存配置 |
| [配置中心](./doc/配置中心.md) | Spring Cloud Config 使用 |
| [配置刷新](./doc/配置刷新.md) | 动态配置刷新指南 |
| [文件存储](./doc/文件存储.md) | Minio 文件存储配置 |
| [API 开发指南](./doc/API开发指南.md) | 接口开发规范和示例 |
| [MyBatis 开发技巧](./doc/MyBatis开发技巧.md) | MyBatis XML 编写工具推荐 |
| [常见问题](./doc/常见问题.md) | FAQ 常见问题解答 |

## ⚙️ 核心配置

### 缓存配置

支持 Redis 和本地缓存两种模式：

```yaml
akm:
  cacheType: local  # local: 本地缓存 | redis: Redis缓存
```

详见：[缓存配置文档](./doc/缓存配置.md)

### 配置中心

支持 Spring Cloud Config 统一配置管理：

```yaml
# bootstrap.yaml
spring:
  cloud:
    config:
      enabled: true
      uri: http://localhost:33033
```

详见：[配置中心文档](./doc/配置中心.md)

### 数据库支持

```yaml
spring:
  datasource:
    # MySQL
    url: jdbc:mysql://localhost:3306/akm_springboot
    driver-class-name: com.mysql.cj.jdbc.Driver
    
    # PostgreSQL
    # url: jdbc:postgresql://localhost:5432/akm_springboot
    # driver-class-name: org.postgresql.Driver
    
    # DaMeng (达梦)
    # url: jdbc:dm://localhost:5236/akm_springboot
    # driver-class-name: dm.jdbc.driver.DmDriver
```

详见：[数据库配置文档](./doc/数据库配置.md)

## 🔐 安全特性

- **配置加密** - Jasypt 加密敏感配置
- **参数签名** - 接口参数签名验证
- **加解密** - 请求/响应体加解密
- **敏感词过滤** - 防止 SQL 注入
- **频率限制** - 接口访问频率控制
- **CSRF 防护** - CSRF Token 校验
- **权限控制** - 基于角色的权限管理

## 📝 API 开发规范

### 路径规范

```
/xxx/view/{method}    - 查询接口
/xxx/op/{method}      - 操作接口（新增、编辑、删除）
/xxx/open/{method}    - 开放接口（不需要权限）
/xxx/public/{method}  - 公共接口（仅验证登录）
```

### 示例代码

```java
@Tag(name = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserApi {

    private final SysUserService userService;

    public SysUserApi(SysUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "查询用户列表")
    @GetMapping("/view/list")
    public PageResult<SysUser> list(SysUserQuery query) {
        return userService.queryList(query);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/op/add")
    public void add(@RequestBody SysUser user) {
        userService.add(user);
    }
}
```

详见：[API 开发指南](./doc/API开发指南.md)

## 🔧 常用工具类

| 工具类 | 说明 |
|--------|------|
| `CacheUtils` | 缓存操作（对象类型） |
| `StringCacheUtils` | 缓存操作（字符串类型） |
| `EncryptUtils` | 加解密工具 |
| `IpUtils` | IP 地址处理 |
| `SnowflakeUtils` | ID 生成器 |
| `SpringContextHolder` | 获取 Spring Bean |
| `UserThreadUtils` | 获取当前用户信息 |

## 🛠️ 开发工具推荐

### MyBatis XML 编写工具

手工编写 MyBatis XML 文件费时费力，推荐使用以下工具提升开发效率：

#### 方式一：IDEA 插件（推荐）

**MyBatisCodeHelper Pro** - 强大的 MyBatis 开发插件

- 🚀 **自动生成 XML**：根据 Mapper 接口方法自动生成 XML SQL
- 🔄 **Java ↔ XML 跳转**：Mapper 方法与 XML 快速跳转
- ✅ **SQL 校验**：实时检查 SQL 语法错误
- 🎯 **智能提示**：字段名、表名智能补全
- 📝 **批量生成**：一键生成 CRUD 方法和 XML

**安装方式**：
```
IDEA → Settings → Plugins → 搜索 "MyBatisCodeHelper Pro"
```

**使用示例**：
```java
// 在 Mapper 接口中定义方法
List<User> findByUsername(String username);

// 按 Alt + Enter → Generate MyBatis SQL → 自动生成 XML：
<select id="findByUsername" resultType="User">
    SELECT * FROM sys_user WHERE username = #{username}
</select>
```

**其他推荐插件**：
- **MyBatisX**：免费的 MyBatis 增强插件
- **MyBatis Log Plugin**：格式化 MyBatis 日志

#### 方式二：AI 工具辅助

使用 AI 工具快速生成 MyBatis XML：

**ChatGPT / Claude / Cursor AI**

提示词模板：
```
帮我生成 MyBatis XML 查询语句：
- 表名：sys_user
- 查询条件：username (模糊查询)、status (精确匹配)
- 排序：create_time 降序
- 支持分页
```

**GitHub Copilot**

直接在 Mapper 接口中编写注释，AI 自动补全：
```java
/**
 * 根据用户名和状态查询用户列表，支持分页
 * @param username 用户名（模糊查询）
 * @param status 状态
 */
List<User> findByUsernameAndStatus(String username, String status);

// Copilot 会自动在 XML 中生成对应的 SQL
```

#### 方式三：代码生成器

**MyBatis Generator** - 官方代码生成工具

```xml
<!-- pom.xml -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.2</version>
</plugin>
```

```bash
# 根据数据库表自动生成 Entity、Mapper、XML
mvn mybatis-generator:generate
```

详细配置参见：[MyBatis Generator 文档](http://mybatis.org/generator/)

#### 最佳实践

1. **使用插件生成基础 CRUD**，减少重复劳动
2. **复杂 SQL 用 AI 辅助编写**，提高准确性
3. **启用 SQL 日志**，方便调试和优化
```yaml
akm:
  mybatis:
    printSql: true
```

4. **定期检查 SQL 性能**，使用 `EXPLAIN` 分析执行计划

## 🐛 调试技巧

### 查看 SQL 日志

```yaml
akm:
  mybatis:
    printSql: true  # 打印 SQL 语句
```

### 查看配置来源

```bash
curl http://localhost:33000/demo/config/open/info
```

### 查看健康状态

```bash
curl http://localhost:33000/actuator/health
```

## 📊 性能优化

- **连接池** - HikariCP 高性能连接池
- **缓存策略** - Redis 缓存热点数据，防止缓存击穿
- **分页优化** - PageHelper 物理分页
- **异步处理** - @Async 异步任务执行
- **定时任务** - Magic API 定时任务

## 📜 提交规范

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

## ⚠️ 注意事项

1. **生产环境务必使用 Redis 缓存**，不要使用本地缓存
2. **敏感配置必须加密**，使用 Jasypt 加密密码等信息
3. **定期备份数据库**，建议每天自动备份
4. **关注安全更新**，及时更新依赖版本
5. **监控日志文件大小**，避免磁盘空间不足

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

**⭐ 如果这个项目对你有帮助，请给个 Star！**
