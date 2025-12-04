# MyBatis 开发技巧和工具推荐

## 🚀 快速生成 MyBatis XML

手工编写 MyBatis XML 文件费时费力，本文介绍多种高效的开发方式。

## 一、IDEA 插件（最推荐）

### 1.1 MyBatisCodeHelper Pro ⭐⭐⭐⭐⭐

**最强大的 MyBatis 开发插件**（付费，约 ¥199/年）

#### 核心功能

| 功能 | 说明 |
|------|------|
| 🎯 **自动生成 XML** | 根据 Mapper 方法名自动生成 SQL |
| 🔄 **快速跳转** | Mapper ↔ XML 双向跳转（Ctrl/Cmd + Click） |
| ✅ **SQL 校验** | 实时检查 SQL 语法、表名、字段名 |
| 🎨 **智能提示** | 字段名、表名智能补全 |
| 📝 **批量生成** | 一键生成完整的 CRUD 方法和 XML |
| 🔍 **SQL 格式化** | 美化 SQL 语句 |
| 🗄️ **多数据库** | 支持 MySQL、PostgreSQL、Oracle 等 |
| 🐛 **SQL 调试** | 在 IDEA 中直接运行和调试 SQL |

#### 安装方式

```
IDEA → File → Settings → Plugins → Marketplace 
→ 搜索 "MyBatisCodeHelper Pro" → Install → Restart IDEA
```

#### 使用示例

**示例 1：自动生成查询 XML**

```java
// Step 1: 在 Mapper 接口中定义方法
public interface UserMapper {
    List<User> findByUsernameAndStatus(
        @Param("username") String username, 
        @Param("status") String status
    );
}

// Step 2: 光标放在方法名上，按 Alt + Enter (Mac: Option + Enter)
// Step 3: 选择 "Generate MyBatis SQL"
// Step 4: 插件会自动在对应的 XML 文件中生成：

<select id="findByUsernameAndStatus" resultType="User">
    SELECT * FROM sys_user 
    WHERE username = #{username} 
    AND status = #{status}
</select>
```

**示例 2：方法名生成 SQL**

```java
// 按照约定的方法命名，插件会智能生成 SQL
List<User> findByUsername(String username);
// 生成：SELECT * FROM sys_user WHERE username = #{username}

List<User> findByUsernameAndStatusOrderByCreateTimeDesc(String username, String status);
// 生成：SELECT * FROM sys_user WHERE username = #{username} AND status = #{status} ORDER BY create_time DESC

int updateUsernameById(@Param("username") String username, @Param("id") String id);
// 生成：UPDATE sys_user SET username = #{username} WHERE id = #{id}

int deleteById(String id);
// 生成：DELETE FROM sys_user WHERE id = #{id}
```

**示例 3：批量生成 CRUD**

```java
// 右键 Mapper 接口 → MyBatisCodeHelper → Generate CRUD
// 自动生成：
- insert
- insertSelective
- updateByPrimaryKey
- updateByPrimaryKeySelective
- selectByPrimaryKey
- deleteByPrimaryKey
```

#### 快捷键

| 快捷键 | 功能 |
|--------|------|
| `Ctrl + Shift + F9` | Mapper 跳转到 XML |
| `Alt + Enter` | 生成 MyBatis SQL |
| `Ctrl + Q` | 查看 SQL 预览 |

### 1.2 MyBatisX ⭐⭐⭐⭐

**免费的 MyBatis 增强插件**

#### 核心功能

- ✅ Mapper ↔ XML 跳转（图标提示）
- ✅ 基础代码生成
- ✅ SQL 语句检查
- ✅ 参数提示

#### 安装方式

```
IDEA → Settings → Plugins → 搜索 "MyBatisX" → Install
```

#### 使用示例

```java
// 点击方法名左侧的图标，快速跳转到 XML
public interface UserMapper {
    List<User> findAll(); // 左侧有跳转图标
}
```

### 1.3 MyBatis Log Plugin ⭐⭐⭐⭐

**MyBatis 日志格式化插件**（免费）

#### 功能

将控制台中的 MyBatis 日志还原为完整可执行的 SQL

#### 安装方式

```
IDEA → Settings → Plugins → 搜索 "MyBatis Log" → Install
```

#### 使用效果

**原始日志**：
```
Preparing: SELECT * FROM sys_user WHERE id = ? AND status = ?
Parameters: 1(String), ACTIVE(String)
```

**格式化后**（可直接在数据库执行）：
```sql
SELECT * FROM sys_user WHERE id = '1' AND status = 'ACTIVE';
```

点击即可复制到剪贴板，方便在数据库客户端中调试。

## 二、AI 工具辅助

### 2.1 Cursor AI（推荐）

**智能代码编辑器，内置 AI 助手**

#### 使用方式

**方式 1：使用注释提示**

```java
public interface UserMapper {
    /**
     * 查询用户列表
     * 支持用户名模糊查询、状态精确查询
     * 可按创建时间范围筛选
     * 按创建时间降序排序
     * 所有条件都是可选的
     */
    List<SysUser> findByAll(@Param("query") SysUserQuery query);
}
```

在 XML 文件中，Cursor AI 会自动生成完整的 SQL。

**方式 2：使用 Cmd + K 生成**

1. 打开 XML 文件
2. 按 `Cmd + K` (Windows: `Ctrl + K`)
3. 输入提示："根据 findByAll 方法生成 MyBatis XML"
4. AI 自动生成 SQL

### 2.2 GitHub Copilot

**GitHub 官方 AI 编程助手**

#### 使用方式

在 Mapper 接口或 XML 文件中，编写注释或部分代码，Copilot 会自动补全：

```xml
<!-- 在 XML 中输入注释，Copilot 会自动补全 -->
<!-- 查询用户列表，支持用户名和状态查询 -->
<select id="findByUsernameAndStatus" 
<!-- Copilot 会自动补全剩余部分 -->
```

### 2.3 ChatGPT / Claude

**在线 AI 对话工具**

#### 提示词模板

```
我正在开发一个 Spring Boot 3 + MyBatis 项目，请帮我生成 MyBatis XML 查询语句。

【表结构】
表名：sys_user
字段：
- id (VARCHAR 32) 主键
- username (VARCHAR 50) 用户名
- password (VARCHAR 100) 密码
- email (VARCHAR 100) 邮箱
- mobile (VARCHAR 20) 手机号
- status (CHAR 1) 状态：0-禁用，1-启用
- del_flag (CHAR 1) 删除标志：0-未删除，1-已删除
- create_time (DATETIME) 创建时间
- update_time (DATETIME) 更新时间

【查询需求】
1. 根据 username 模糊查询（可选）
2. 根据 status 精确匹配（可选）
3. 根据 mobile 精确匹配（可选）
4. 根据 create_time 范围查询（startTime、endTime，可选）
5. 只查询未删除的数据（del_flag = '0'）
6. 按 create_time 降序排序
7. 支持分页（使用 PageHelper）

【Mapper 接口】
```java
public interface SysUserMapper {
    List<SysUser> findByAll(@Param("query") SysUserQuery query);
}
```

【Query 对象】
```java
public class SysUserQuery extends PageQuery {
    private String username;
    private String status;
    private String mobile;
    private Date startTime;
    private Date endTime;
}
```

请生成对应的 MyBatis XML 文件内容，使用 <if> 标签处理可选条件。
```

AI 会生成完整、可用的 XML 文件。

### 2.4 AI 工具对比

| 工具 | 优点 | 缺点 | 推荐场景 |
|------|------|------|----------|
| **Cursor AI** | 实时补全，上下文感知强 | 需要订阅 | 日常开发 |
| **GitHub Copilot** | 集成度高，响应快 | 需要订阅 | 日常开发 |
| **ChatGPT/Claude** | 生成质量高，可交互 | 需要复制粘贴 | 复杂 SQL |

## 三、MyBatis Generator

### 3.1 配置和使用

**适用场景**：快速搭建项目，批量生成基础 CRUD

#### Step 1: 添加 Maven 插件

```xml
<!-- pom.xml -->
<build>
    <plugins>
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.4.2</version>
            <configuration>
                <configurationFile>
                    src/main/resources/generatorConfig.xml
                </configurationFile>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>com.mysql</groupId>
                    <artifactId>mysql-connector-j</artifactId>
                    <version>8.0.33</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

#### Step 2: 创建配置文件

```xml
<!-- src/main/resources/generatorConfig.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="MySQLTables" targetRuntime="MyBatis3">
        
        <!-- 生成的代码添加注释 -->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="suppressAllComments" value="false"/>
        </commentGenerator>

        <!-- 数据库连接 -->
        <jdbcConnection 
            driverClass="com.mysql.cj.jdbc.Driver"
            connectionURL="jdbc:mysql://localhost:3306/akm_springboot?useSSL=false&amp;serverTimezone=UTC"
            userId="root"
            password="password">
        </jdbcConnection>

        <!-- 类型转换 -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- Entity 生成配置 -->
        <javaModelGenerator 
            targetPackage="com.akm.springboot3.web.sys.entity" 
            targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!-- XML 生成配置 -->
        <sqlMapGenerator 
            targetPackage="mapper.sys" 
            targetProject="src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- Mapper 接口生成配置 -->
        <javaClientGenerator 
            type="XMLMAPPER" 
            targetPackage="com.akm.springboot3.web.sys.mapper" 
            targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 指定要生成的表 -->
        <table tableName="sys_user" domainObjectName="SysUser">
            <!-- 主键生成策略 -->
            <generatedKey column="id" sqlStatement="SELECT REPLACE(UUID(),'-','')" identity="false"/>
        </table>
        
        <table tableName="sys_role" domainObjectName="SysRole"/>
        <table tableName="sys_menu" domainObjectName="SysMenu"/>
        
        <!-- 批量生成（使用通配符） -->
        <!-- <table tableName="sys_%" enableCountByExample="false"/> -->
        
    </context>
</generatorConfiguration>
```

#### Step 3: 执行生成

```bash
# 方式 1：Maven 命令
mvn mybatis-generator:generate

# 方式 2：IDEA Maven 面板
IDEA → Maven → akm-springboot3 → Plugins → mybatis-generator → mybatis-generator:generate → 右键 Run
```

#### 生成结果

```
生成文件：
├── entity/
│   └── SysUser.java              # 实体类
├── mapper/
│   └── SysUserMapper.java        # Mapper 接口
└── resources/mapper/sys/
    └── SysUserMapper.xml          # XML 文件
```

**生成的 XML 包含**：
- `insert` - 插入
- `insertSelective` - 选择性插入
- `selectByPrimaryKey` - 根据主键查询
- `updateByPrimaryKey` - 根据主键更新
- `updateByPrimaryKeySelective` - 选择性更新
- `deleteByPrimaryKey` - 根据主键删除

### 3.2 自定义模板

如需自定义生成的代码，可以：

1. 继承 `DefaultCommentGenerator` 自定义注释
2. 实现 `Plugin` 接口自定义生成逻辑
3. 使用 `<columnOverride>` 自定义字段映射

参考：[MyBatis Generator 官方文档](http://mybatis.org/generator/)

## 四、混合开发模式（最佳实践）

### 推荐流程

1. **使用 MyBatis Generator 生成基础代码**
   - 快速生成 Entity、Mapper、基础 CRUD XML

2. **使用 MyBatisCodeHelper Pro 开发业务 SQL**
   - 在 Mapper 接口定义业务方法
   - 使用插件自动生成 XML

3. **复杂 SQL 使用 AI 辅助**
   - 多表关联查询
   - 复杂的子查询
   - 动态 SQL 生成

4. **启用 SQL 日志调试**
   ```yaml
   akm:
     mybatis:
       printSql: true
   ```

5. **使用 MyBatis Log Plugin 格式化日志**
   - 快速复制可执行的 SQL
   - 在数据库客户端中调试

### 开发示例

**场景**：开发一个用户查询接口

```java
// Step 1: 定义 Mapper 方法（添加详细注释供 AI 理解）
public interface SysUserMapper {
    /**
     * 查询用户列表
     * - 支持用户名模糊查询（username）
     * - 支持状态精确查询（status）
     * - 支持手机号精确查询（mobile）
     * - 支持创建时间范围查询（startTime、endTime）
     * - 只查询未删除的用户（del_flag = '0'）
     * - 按创建时间降序排序
     * - 支持分页
     */
    List<SysUser> findByAll(@Param("query") SysUserQuery query);
}

// Step 2: 在 Cursor AI 中，AI 会自动在 XML 中生成对应的 SQL
// 或使用 MyBatisCodeHelper Pro 的 Alt + Enter 快捷生成

// Step 3: 在 Service 层使用
@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Override
    public PageResult<SysUser> queryList(SysUserQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        List<SysUser> list = userMapper.findByAll(query);
        return PageResult.of(list);
    }
}

// Step 4: 启动应用，查看日志中的 SQL
// Step 5: 使用 MyBatis Log Plugin 格式化日志，复制 SQL 到数据库客户端测试
// Step 6: 优化 SQL（添加索引、调整查询条件等）
```

## 五、SQL 编写技巧

### 5.1 动态 SQL

```xml
<!-- 使用 <if> 处理可选条件 -->
<select id="findByAll" resultType="SysUser">
    SELECT * FROM sys_user
    WHERE del_flag = '0'
    <if test="query.username != null and query.username != ''">
        AND username LIKE CONCAT('%', #{query.username}, '%')
    </if>
    <if test="query.status != null and query.status != ''">
        AND status = #{query.status}
    </if>
    ORDER BY create_time DESC
</select>

<!-- 使用 <where> 简化 -->
<select id="findByAll" resultType="SysUser">
    SELECT * FROM sys_user
    <where>
        <if test="query.username != null and query.username != ''">
            AND username LIKE CONCAT('%', #{query.username}, '%')
        </if>
        <if test="query.status != null and query.status != ''">
            AND status = #{query.status}
        </if>
    </where>
    ORDER BY create_time DESC
</select>

<!-- 使用 <choose> 实现 switch -->
<select id="findByType" resultType="SysUser">
    SELECT * FROM sys_user
    WHERE del_flag = '0'
    <choose>
        <when test="type == 'admin'">
            AND role = 'ADMIN'
        </when>
        <when test="type == 'user'">
            AND role = 'USER'
        </when>
        <otherwise>
            AND role = 'GUEST'
        </otherwise>
    </choose>
</select>

<!-- 使用 <foreach> 实现 IN 查询 -->
<select id="findByIds" resultType="SysUser">
    SELECT * FROM sys_user
    WHERE id IN
    <foreach collection="ids" item="id" open="(" close=")" separator=",">
        #{id}
    </foreach>
</select>
```

### 5.2 防止 SQL 注入

```xml
<!-- ✅ 正确：使用 #{} -->
<select id="findByUsername" resultType="User">
    SELECT * FROM sys_user WHERE username = #{username}
</select>

<!-- ❌ 错误：使用 ${} 有注入风险 -->
<select id="findByUsername" resultType="User">
    SELECT * FROM sys_user WHERE username = '${username}'
</select>

<!-- ${} 适用场景：表名、列名（确保安全的情况下） -->
<select id="findByTable" resultType="Map">
    SELECT * FROM ${tableName}
</select>
```

### 5.3 ResultMap 配置

```xml
<!-- 复杂对象映射 -->
<resultMap id="UserWithRoles" type="SysUser">
    <id column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="email" property="email"/>
    
    <!-- 一对多关联 -->
    <collection property="roles" ofType="SysRole">
        <id column="role_id" property="id"/>
        <result column="role_name" property="roleName"/>
    </collection>
</resultMap>

<select id="selectUserWithRoles" resultMap="UserWithRoles">
    SELECT 
        u.id, u.username, u.email,
        r.id as role_id, r.role_name
    FROM sys_user u
    LEFT JOIN sys_user_role ur ON u.id = ur.user_id
    LEFT JOIN sys_role r ON ur.role_id = r.id
    WHERE u.id = #{id}
</select>
```

## 六、效率对比

| 方式 | 开发速度 | 代码质量 | 学习成本 | 推荐指数 |
|------|---------|---------|---------|----------|
| 手工编写 | ⭐ | ⭐⭐⭐ | ⭐ | ⭐⭐ |
| MyBatisCodeHelper Pro | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| MyBatisX | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐ | ⭐⭐⭐⭐ |
| MyBatis Generator | ⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| Cursor AI | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐ | ⭐⭐⭐⭐⭐ |
| ChatGPT/Claude | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐ | ⭐⭐⭐⭐ |

**综合推荐**：
- 💰 **有预算**：MyBatisCodeHelper Pro + Cursor AI
- 💵 **预算有限**：MyBatisX + ChatGPT/Claude
- 🆓 **完全免费**：MyBatisX + MyBatis Generator + ChatGPT 免费版

## 七、最佳实践总结

### 7.1 开发流程

```
1. 设计数据库表
   ↓
2. 使用 MyBatis Generator 生成基础代码
   ↓
3. 定义业务 Mapper 方法（添加详细注释）
   ↓
4. 使用 MyBatisCodeHelper Pro 或 AI 生成 XML
   ↓
5. 启用 SQL 日志，查看执行的 SQL
   ↓
6. 使用 MyBatis Log Plugin 格式化日志
   ↓
7. 在数据库客户端中优化 SQL
   ↓
8. 更新 XML，添加索引，完成开发
```

### 7.2 注意事项

1. **生成的代码需要review**
   - 检查 SQL 是否正确
   - 检查字段映射是否完整
   - 检查是否有性能问题

2. **不要过度依赖生成工具**
   - 复杂业务逻辑需要手写
   - 性能优化需要人工调整

3. **保持代码风格一致**
   - 统一使用 `#{}` 而不是 `${}`
   - 统一使用 `<if>` 处理可选条件
   - 统一的缩进和格式

4. **版本控制**
   - 生成的代码要提交到 Git
   - 配置文件不要包含敏感信息

## 八、工具下载链接

- **MyBatisCodeHelper Pro**: https://gejun.tech/idea-plugins/mybatisCodeHelper/
- **MyBatisX**: IDEA Plugins Marketplace
- **MyBatis Log Plugin**: IDEA Plugins Marketplace
- **Cursor**: https://cursor.sh/
- **GitHub Copilot**: https://github.com/features/copilot
- **ChatGPT**: https://chat.openai.com/
- **Claude**: https://claude.ai/

## 相关文档

- [开发规范](./开发规范.md)
- [数据库配置](./数据库配置.md)
- [API 开发指南](./API开发指南.md)

