# API 开发指南

## 一、API 规范

### 1.1 路径规范

```
基础路径格式：/{module}/{resource}/{type}/{method}

- module: 模块名（biz, sys, demo 等）
- resource: 资源名（user, role, order 等）
- type: 接口类型
- method: 方法名
```

### 1.2 接口类型

| 类型 | 路径 | 说明 | 权限要求 |
|------|------|------|----------|
| 查询接口 | `/view/{method}` | 查询、列表、详情 | 需要权限 |
| 操作接口 | `/op/{method}` | 新增、编辑、删除 | 需要权限 |
| 开放接口 | `/open/{method}` | 登录、注册等 | 无需权限 |
| 公共接口 | `/public/{method}` | 需登录的公共接口 | 仅验证登录 |

### 1.3 示例

```
GET  /sys/user/view/list        # 查询用户列表
GET  /sys/user/view/detail      # 查询用户详情
POST /sys/user/op/add           # 新增用户
POST /sys/user/op/edit          # 编辑用户
POST /sys/user/op/delete        # 删除用户
POST /auth/open/login           # 登录（开放接口）
GET  /sys/dict/public/list      # 字典列表（公共接口）
```

## 二、Controller 开发

### 2.1 基础模板

```java
@Tag(name = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserApi {

    private final SysUserService userService;

    // 使用构造器注入
    public SysUserApi(SysUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "查询用户列表")
    @GetMapping("/view/list")
    public PageResult<SysUser> list(SysUserQuery query) {
        return userService.queryList(query);
    }

    @Operation(summary = "查询用户详情")
    @GetMapping("/view/detail")
    public SysUser detail(@Parameter(description = "用户ID") @RequestParam String id) {
        return userService.getById(id);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/op/add")
    public void add(@RequestBody @Valid SysUser user) {
        userService.add(user);
    }

    @Operation(summary = "编辑用户")
    @PostMapping("/op/edit")
    public void edit(@RequestBody @Valid SysUser user) {
        userService.update(user);
    }

    @Operation(summary = "删除用户")
    @PostMapping("/op/delete")
    public void delete(@RequestBody String id) {
        userService.delete(id);
    }

    @Operation(summary = "批量删除用户")
    @PostMapping("/op/batchDelete")
    public void batchDelete(@RequestBody List<String> ids) {
        userService.batchDelete(ids);
    }
}
```

### 2.2 参数接收

```java
// Query 参数（URL 参数）
@GetMapping("/view/list")
public PageResult<User> list(UserQuery query) {
    // query 对象自动绑定 URL 参数
}

// Body 参数（JSON）
@PostMapping("/op/add")
public void add(@RequestBody @Valid User user) {
    // user 对象从请求体解析
}

// Path 参数
@GetMapping("/view/detail/{id}")
public User detail(@PathVariable String id) {
    return userService.getById(id);
}

// 单个参数
@GetMapping("/view/detail")
public User detail(@RequestParam String id) {
    return userService.getById(id);
}
```

### 2.3 响应封装

系统自动封装响应为统一格式，无需手动封装：

```java
// 返回对象会自动封装
@GetMapping("/view/detail")
public User detail(@RequestParam String id) {
    return userService.getById(id);  // 自动封装为 RestResult
}

// 响应格式
{
    "code": 100200,
    "message": "成功",
    "data": {
        "id": "1",
        "username": "admin"
    }
}

// 如果不需要自动封装，使用 @IgnoreResultHandler
@IgnoreResultHandler
@GetMapping("/download")
public void download(HttpServletResponse response) {
    // 直接操作 response
}
```

## 三、Service 开发

### 3.1 Service 接口

```java
public interface SysUserService {
    
    /**
     * 查询用户列表
     */
    PageResult<SysUser> queryList(SysUserQuery query);
    
    /**
     * 根据ID查询用户
     */
    SysUser getById(String id);
    
    /**
     * 新增用户
     */
    void add(SysUser user);
    
    /**
     * 更新用户
     */
    void update(SysUser user);
    
    /**
     * 删除用户
     */
    void delete(String id);
    
    /**
     * 批量删除用户
     */
    void batchDelete(List<String> ids);
}
```

### 3.2 Service 实现

```java
@Service
public class SysUserServiceImpl implements SysUserService {
    
    private final SysUserMapper userMapper;
    
    public SysUserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Override
    public PageResult<SysUser> queryList(SysUserQuery query) {
        // 分页查询
        PageHelper.startPage(query.getPage(), query.getSize());
        List<SysUser> list = userMapper.findByAll(query);
        return PageResult.of(list);
    }
    
    @Override
    public SysUser getById(String id) {
        SysUser user = userMapper.selectByPrimaryKey(id);
        AssertUtils.notNull(user, "用户不存在");
        return user;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUser user) {
        // 校验用户名是否存在
        SysUser exists = userMapper.selectByUsername(user.getUsername());
        AssertUtils.isNull(exists, "用户名已存在");
        
        // 设置默认值
        user.setId(SnowflakeUtils.nextIdStr());
        user.setCreateTime(new Date());
        user.setCreateUserId(UserThreadUtils.getUserId());
        
        // 密码加密
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        
        // 插入数据库
        userMapper.insertSelective(user);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser user) {
        // 校验用户是否存在
        SysUser exists = userMapper.selectByPrimaryKey(user.getId());
        AssertUtils.notNull(exists, "用户不存在");
        
        // 设置更新信息
        user.setUpdateTime(new Date());
        user.setUpdateUserId(UserThreadUtils.getUserId());
        
        // 更新数据库
        userMapper.updateByPrimaryKeySelective(user);
        
        // 删除缓存
        CacheUtils.del("user:" + user.getId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        // 校验用户是否存在
        SysUser user = userMapper.selectByPrimaryKey(id);
        AssertUtils.notNull(user, "用户不存在");
        
        // 逻辑删除
        user.setDelFlag("1");
        user.setUpdateTime(new Date());
        user.setUpdateUserId(UserThreadUtils.getUserId());
        userMapper.updateByPrimaryKeySelective(user);
        
        // 删除缓存
        CacheUtils.del("user:" + id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<String> ids) {
        ids.forEach(this::delete);
    }
}
```

## 四、数据校验

### 4.1 参数校验注解

```java
@Getter
@Setter
public class UserAddDTO {
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度3-20个字符")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", 
             message = "密码必须包含大小写字母和数字，至少8位")
    private String password;
    
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄不能小于18岁")
    @Max(value = 100, message = "年龄不能大于100岁")
    private Integer age;
}

// Controller 中使用 @Valid
@PostMapping("/op/add")
public void add(@RequestBody @Valid UserAddDTO dto) {
    userService.add(dto);
}
```

### 4.2 业务校验

```java
// 使用断言工具
AssertUtils.notNull(user, "用户不存在");
AssertUtils.isNull(exists, "用户名已存在");
AssertUtils.isTrue(user.isEnabled(), "用户已禁用");
AssertUtils.isFalse(user.isLocked(), "用户已锁定");

// 或抛出业务异常
if (user == null) {
    throw new BusinessException(CodeMsg.USER_NOT_FOUND);
}
if (exists != null) {
    throw new BusinessException("用户名已存在");
}
```

## 五、分页查询

### 5.1 分页参数

```java
@Getter
@Setter
public class PageQuery {
    @Parameter(description = "页码", example = "1")
    private Integer page = 1;
    
    @Parameter(description = "每页数量", example = "10")
    private Integer size = 10;
}

@Getter
@Setter
public class UserQuery extends PageQuery {
    @Parameter(description = "用户名")
    private String username;
    
    @Parameter(description = "状态")
    private String status;
}
```

### 5.2 分页查询实现

```java
@Override
public PageResult<SysUser> queryList(UserQuery query) {
    // 开启分页
    PageHelper.startPage(query.getPage(), query.getSize());
    
    // 查询列表
    List<SysUser> list = userMapper.findByAll(query);
    
    // 封装分页结果
    return PageResult.of(list);
}
```

### 5.3 分页响应

```json
{
    "code": 100200,
    "message": "成功",
    "data": {
        "total": 100,
        "list": [
            {
                "id": "1",
                "username": "admin"
            }
        ],
        "pageNum": 1,
        "pageSize": 10,
        "pages": 10
    }
}
```

## 六、文件上传下载

### 6.1 文件上传

```java
@Operation(summary = "文件上传")
@PostMapping("/upload")
public String upload(@RequestParam("file") MultipartFile file) {
    // 校验文件
    AssertUtils.notNull(file, "文件不能为空");
    AssertUtils.isTrue(!file.isEmpty(), "文件内容为空");
    
    // 生成文件路径
    String objectKey = FileUtils.genFileKey(file.getOriginalFilename());
    
    // 上传到 Minio
    MinioUtils.putObject(
        objectKey,
        file.getInputStream(),
        file.getSize(),
        file.getContentType()
    );
    
    return objectKey;
}
```

### 6.2 文件下载

```java
@Operation(summary = "文件下载")
@GetMapping("/download")
@IgnoreResultHandler  // 不封装响应
public void download(@RequestParam String objectKey, HttpServletResponse response) {
    // 校验文件路径
    if (FileUtils.isInvalidKey(allowType, objectKey)) {
        throw new BusinessException("文件路径无效");
    }
    
    // 获取文件
    GetObjectResponse obj = MinioUtils.getObject(objectKey);
    
    // 设置响应头
    response.setContentType(obj.headers().get("Content-Type"));
    response.setHeader("Content-Disposition", 
        "attachment; filename=" + FileUtils.getFileName(objectKey));
    
    // 输出文件流
    IoUtil.copy(obj, response.getOutputStream());
}
```

## 七、异步处理

### 7.1 异步方法

```java
@Service
public class NotificationService {
    
    @Async  // 异步执行
    public void sendEmail(String email, String content) {
        // 发送邮件逻辑
        log.info("发送邮件到: {}", email);
    }
    
    @Async
    public CompletableFuture<String> processAsync(String data) {
        // 异步处理并返回结果
        String result = doProcess(data);
        return CompletableFuture.completedFuture(result);
    }
}

// 使用
@Service
public class UserService {
    private final NotificationService notificationService;
    
    public void register(User user) {
        // 保存用户
        userMapper.insert(user);
        
        // 异步发送欢迎邮件
        notificationService.sendEmail(user.getEmail(), "欢迎注册");
    }
}
```

## 八、缓存使用

```java
@Service
public class UserService {
    
    private final UserMapper userMapper;
    
    public User getById(String id) {
        // 使用缓存（防止缓存击穿）
        return CacheUtils.cacheAndGet("user:" + id, 3600, () -> {
            return userMapper.selectByPrimaryKey(id);
        });
    }
    
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        
        // 删除缓存
        CacheUtils.del("user:" + user.getId());
    }
}
```

## 九、常用工具类

### 9.1 用户信息获取

```java
// 获取当前用户ID
String userId = UserThreadUtils.getUserId();

// 获取当前用户名
String username = UserThreadUtils.getUsername();

// 获取当前租户ID
String tenantId = UserThreadUtils.getTenantId();
```

### 9.2 ID 生成

```java
// 生成雪花ID（字符串）
String id = SnowflakeUtils.nextIdStr();

// 生成雪花ID（长整型）
Long id = SnowflakeUtils.nextId();
```

### 9.3 IP 获取

```java
// 获取客户端IP
String ip = IpUtils.getIpAddr(request);
```

## 十、API 文档

### 10.1 Swagger 注解

```java
@Tag(name = "用户管理", description = "用户相关接口")
@RestController
public class UserApi {
    
    @Operation(summary = "查询用户", description = "根据ID查询用户详情")
    @Parameter(name = "id", description = "用户ID", required = true, example = "1")
    @GetMapping("/view/detail")
    public User detail(@RequestParam String id) {
        return userService.getById(id);
    }
}

@Schema(description = "用户信息")
@Getter
@Setter
public class User {
    
    @Schema(description = "用户ID", example = "1")
    private String id;
    
    @Schema(description = "用户名", example = "admin")
    private String username;
    
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;
}
```

### 10.2 访问文档

- Swagger UI: http://localhost:33000/doc.html
- OpenAPI JSON: http://localhost:33000/akm-docs

## 相关文档

- [开发规范](./开发规范.md)
- [数据库配置](./数据库配置.md)
- [缓存配置](./缓存配置.md)
- [文件存储](./文件存储.md)

