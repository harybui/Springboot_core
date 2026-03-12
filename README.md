# Spring Boot — Tổng kết học tập trong tuần

>hành trình học Spring Boot từ project đầu tiên đến project ngày 4,
> bao gồm khái niệm, ví dụ code thực tế và những lỗi đã gặp.

---

## Mục lục

1. [Tổng quan kiến trúc Spring Boot](#1-tổng-quan-kiến-trúc-spring-boot)
2. [Project 1 — firstSpringBoot: CRUD cơ bản](#2-project-1--firstspringboot-crud-cơ-bản)
3. [Project 2 — Springboot2: Nâng cấp CRUD + Validation](#3-project-2--springboot2-nâng-cấp-crud--validation)
4. [Project 3 — Springboot_day3: Quan hệ OneToMany](#4-project-3--springboot_day3-quan-hệ-onetomany)
5. [Project 4 — Springboot_day4: DTO Pattern + ManyToMany + Interface](#5-project-4--springboot_day4-dto-pattern--manytomany--interface)
6. [Tóm tắt kiến thức theo chủ đề](#6-tóm-tắt-kiến-thức-theo-chủ-đề)
7. [Lỗi thường gặp và cách sửa](#7-lỗi-thường-gặp-và-cách-sửa)

---

## 1. Tổng quan kiến trúc Spring Boot

Spring Boot theo mô hình **3 tầng (3-Layer Architecture)**:

```
┌─────────────────────────────────────┐
│         Controller (API Layer)       │  ← nhận HTTP request, trả HTTP response
├─────────────────────────────────────┤
│         Service (Business Logic)     │  ← xử lý logic nghiệp vụ
├─────────────────────────────────────┤
│         Repository (Data Layer)      │  ← giao tiếp với database
└─────────────────────────────────────┘
                    │
              [ Database ]
```

**Luồng xử lý một request:**
```
Client → Controller → Service → Repository → DB
                                           ←
Client ← Controller ← Service ← Repository
```

---

## 2. Project 1 — firstSpringBoot: CRUD cơ bản

### Cấu hình database (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=1234567
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update   # tự tạo/cập nhật bảng
spring.jpa.show-sql=true               # in SQL ra console
spring.jpa.properties.hibernate.format_sql=true
```

> **`ddl-auto=update`**: Spring tự tạo bảng nếu chưa có, cập nhật cột nếu thêm field mới.
> Không dùng `create` trên production vì sẽ xóa hết data mỗi lần restart.

---

### Entity — ánh xạ class Java → bảng DB

```java
@Entity                          // đánh dấu đây là bảng trong DB
@Table(name = "Tutorial_first")  // tên bảng tùy chỉnh
@Data                            // Lombok: tự sinh getter/setter/toString
@NoArgsConstructor               // Lombok: constructor không tham số
@AllArgsConstructor              // Lombok: constructor đầy đủ tham số
public class Tutorial {
    @Id                                              // khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;
    private String title;
    private String description;
    private boolean published;
}
```

**Các annotation JPA quan trọng:**

| Annotation | Ý nghĩa |
|-----------|---------|
| `@Entity` | Class này là một bảng trong DB |
| `@Table(name="...")` | Đặt tên bảng tùy ý (không dùng thì dùng tên class) |
| `@Id` | Trường này là khóa chính (Primary Key) |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | ID tự tăng (auto increment) |
| `@Column(name="...")` | Đặt tên cột tùy ý |

---

### Repository — giao tiếp DB

```java
public interface TutorialRepo extends JpaRepository<Tutorial, Long> {
    // JpaRepository<Entity, KiểuDữLiệuCủaId>
    // Spring tự tạo các method: findAll(), findById(), save(), deleteById()...
}
```

> Chỉ cần `extends JpaRepository` là có sẵn toàn bộ CRUD, không cần viết SQL.

---

### Service — xử lý logic

```java
@Service
@RequiredArgsConstructor   // Lombok: inject qua constructor tự động
public class TutorialService {
    private final TutorialRepo repo;

    public List<Tutorial> getAll() {
        return repo.findAll();
    }

    public Tutorial getById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Not Found"));
        // findById trả về Optional<T>, dùng orElseThrow để ném lỗi nếu không tìm thấy
    }

    public Tutorial update(Long id, Tutorial data) {
        Tutorial tutorial = getById(id);   // tìm trong DB trước
        tutorial.setTitle(data.getTitle());
        tutorial.setDescription(data.getDescription());
        return repo.save(tutorial);        // save lại để UPDATE (không phải INSERT mới)
    }
}
```

**Lưu ý quan trọng về `save()`:**
- Nếu object **có id** và id đó tồn tại trong DB → `UPDATE`
- Nếu object **không có id** (null) → `INSERT` (tạo mới)

---

### Controller — nhận và trả HTTP

```java
@RestController                    // = @Controller + @ResponseBody (trả JSON)
@RequestMapping("/api/tutorial")   // prefix URL cho tất cả endpoint trong class
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService service;

    @GetMapping("/{id}")                        // GET /api/tutorial/1
    public ResponseEntity<Tutorial> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping                                // POST /api/tutorial
    public ResponseEntity<Tutorial> create(@RequestBody Tutorial tutorial) {
        return ResponseEntity.ok(service.create(tutorial));
        // @RequestBody: đọc JSON từ request body → convert thành object Java
    }

    @PutMapping("/{id}")                        // PUT /api/tutorial/1
    public ResponseEntity<Tutorial> update(@PathVariable Long id,
                                           @RequestBody Tutorial tutorial) {
        return ResponseEntity.ok(service.update(id, tutorial));
    }

    @DeleteMapping("/{id}")                     // DELETE /api/tutorial/1
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
```

**Các HTTP Method:**

| Annotation | HTTP Method | Dùng để |
|-----------|------------|---------|
| `@GetMapping` | GET | Lấy dữ liệu |
| `@PostMapping` | POST | Tạo mới |
| `@PutMapping` | PUT | Cập nhật toàn bộ |
| `@PatchMapping` | PATCH | Cập nhật một phần |
| `@DeleteMapping` | DELETE | Xóa |

---

## 3. Project 2 — Springboot2: Nâng cấp CRUD + Validation

### Chuyển sang `application.yaml`

```yaml
spring:
  application:
    name: Springboot2
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: 1234567
  jpa:
    hibernate:
      ddl-auto: update
```

> YAML dùng **indent (thụt đầu dòng)** thay cho dấu chấm. Dễ đọc hơn khi config nhiều.

---

### Constructor Injection thủ công (thay vì `@RequiredArgsConstructor`)

```java
@RestController
public class ProductController {
    private final ProductService service;

    // Inject bằng constructor tự viết
    public ProductController(ProductService service) {
        this.service = service;
    }
}
```

> Cả hai cách đều đúng. `@RequiredArgsConstructor` của Lombok chỉ là cách viết ngắn hơn.

---

### `@JsonProperty` — kiểm soát serialization

```java
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
private Long id;
```

> `READ_ONLY`: field này chỉ xuất hiện khi **trả về** (response), không được phép gửi lên trong request body.
> Dùng để tránh client tự ý set id khi tạo mới.

---

### Validation với `@Valid`

```java
// Controller
@PostMapping
public ResponseEntity<Product2> create(@Valid @RequestBody Product2 product) {
    // @Valid: kích hoạt validation các annotation trong Product2
}
```

---

## 4. Project 3 — Springboot_day3: Quan hệ OneToMany

### Bài toán

Một **User** có thể có nhiều **Tutorial** → quan hệ `1:N`

```
User (1) ──────────── (N) Tutorial
   id                      id
   name                    description
   email                   published
                           user_id  ← khóa ngoại trỏ về User
```

---

### Entity User — phía "1"

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(
        mappedBy = "user",          // tên field trong Tutorial trỏ về User
        cascade = CascadeType.ALL,  // thao tác User → tự động áp dụng cho Tutorial
        orphanRemoval = true        // xóa Tutorial nếu bị tách khỏi User
    )
    @JsonManagedReference   // phía "cha" — sẽ được serialize ra JSON
    private List<Tutorial> tutorials;
}
```

---

### Entity Tutorial — phía "N"

```java
@Entity
@Table(name = "tutorials")
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean published;

    @ManyToOne                       // nhiều Tutorial thuộc về 1 User
    @JoinColumn(name = "user_id")    // tên cột khóa ngoại trong bảng tutorials
    @JsonBackReference               // phía "con" — KHÔNG serialize (tránh vòng lặp JSON)
    private User user;
}
```

---

### Tại sao cần `@JsonManagedReference` / `@JsonBackReference`?

Nếu không có, khi serialize JSON sẽ bị **vòng lặp vô hạn**:
```
User → [Tutorial → User → [Tutorial → User → ...]]  ← stack overflow!
```

- `@JsonManagedReference` (User): in ra danh sách tutorials bình thường
- `@JsonBackReference` (Tutorial): bỏ qua field `user` khi in JSON → phá vòng lặp

---

### CascadeType — hành vi cascading

| Giá trị | Ý nghĩa |
|---------|---------|
| `CascadeType.ALL` | Tất cả thao tác (save, delete...) tự động áp dụng xuống con |
| `CascadeType.PERSIST` | Chỉ cascade khi INSERT |
| `CascadeType.REMOVE` | Chỉ cascade khi DELETE |

---

## 5. Project 4 — Springboot_day4: DTO Pattern + ManyToMany + Interface

### Các quan hệ trong project

```
User (1) ──────── (N) Post (N) ──────── (M) Tag
                       │
               bảng trung gian: post_tag
               (post_id, tag_id)
```

---

### DTO Pattern — tách entity khỏi API

**Vấn đề khi trả thẳng Entity:** lộ thông tin không cần thiết, bị vòng lặp JSON, khó kiểm soát input.

**Giải pháp:** dùng DTO (Data Transfer Object) — class riêng chỉ chứa những field cần thiết.

```
Entity (DB model)         DTO (API model)
──────────────────        ────────────────
Post                      PostDto
  id                        id
  title           ──→       title
  content                   content
  user            ──→       userId  (chỉ lấy id, không lấy cả object)
  tags            ──→       tags    (List<String> tên tag, không phải object)
```

**CreateDto** (dùng để nhận input khi tạo mới):
```java
public class CreatePostDto {
    private Long userId;      // client gửi id của user
    private String title;
    private String content;
    private List<Long> tagIds; // client gửi danh sách id của tag
}
```

**ResponseDto** (dùng để trả về):
```java
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private List<String> tags;  // chỉ trả tên tag, không cần cả object
}
```

---

### Entity Post — ManyToMany với Tag

```java
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "post_tag",                              // tên bảng trung gian
        joinColumns = @JoinColumn(name = "post_id"),    // cột của bảng hiện tại
        inverseJoinColumns = @JoinColumn(name = "tag_id") // cột của bảng kia
    )
    private List<Tag> tags;
}
```

**Entity Tag — phía mappedBy:**
```java
@Entity
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")  // "tags" = tên field trong Post
    private List<Post> posts;
}
```

> **Quy tắc ManyToMany:** một phía dùng `@JoinTable` (chủ động), phía kia dùng `mappedBy` (bị động).

---

### Service Interface — tách contract khỏi implementation

```java
// Interface: định nghĩa "cần làm gì"
public interface IUserService {
    UserDto create(CreateUserDto request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, CreateUserDto request);
    void deleteUser(Long id);
}

// Implementation: cài đặt "làm như thế nào"
@Service
public class UserServiceImpl implements IUserService {
    // ...
}
```

**Lợi ích:**
- Dễ thay đổi cách implement mà không ảnh hưởng Controller
- Dễ viết unit test (có thể mock interface)
- Code rõ ràng hơn về "contract" của service

---

### Custom Exception

```java
// Tạo class exception riêng
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

// Dùng trong Service
public UserDto getUserById(Long id) {
    User user = userRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    return map(user);
}
```

> Rõ ràng hơn `RuntimeException`, sau này có thể bắt bằng `@ExceptionHandler` để trả HTTP 404.

---

### Method `map()` trong Service — chuyển đổi Entity → DTO

```java
private UserDto map(User user) {
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    // map danh sách posts
    if (user.getPosts() != null) {
        dto.setPosts(user.getPosts().stream().map(post -> {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setUserId(user.getId());
            postDto.setTags(post.getTags().stream()
                .map(tag -> tag.getName()).toList());
            return postDto;
        }).toList());
    }
    return dto;
}
```

---

## 6. Tóm tắt kiến thức theo chủ đề

### Lombok — giảm boilerplate code

| Annotation | Sinh ra |
|-----------|---------|
| `@Data` | getter + setter + toString + equals + hashCode |
| `@NoArgsConstructor` | constructor không tham số |
| `@AllArgsConstructor` | constructor đầy đủ tham số |
| `@RequiredArgsConstructor` | constructor với các field `final` (dùng để inject) |

---

### Dependency Injection (DI) trong Spring

Spring quản lý các Bean và tự inject khi cần. Ba cách inject:

```java
// Cách 1: @Autowired (không khuyến khích)
@Autowired
private UserRepository userRepo;

// Cách 2: Constructor injection tự viết (recommended)
private final UserRepository userRepo;
public UserServiceImpl(UserRepository userRepo) {
    this.userRepo = userRepo;
}

// Cách 3: @RequiredArgsConstructor (recommended, ngắn hơn)
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepo;  // Spring tự inject
}
```

---

### Các quan hệ JPA

| Quan hệ | Annotation | Ví dụ thực tế |
|---------|-----------|--------------|
| 1 chiều 1:1 | `@OneToOne` | User ↔ Profile |
| 1:N (phía 1) | `@OneToMany(mappedBy="...")` | User → Posts |
| N:1 (phía N) | `@ManyToOne` + `@JoinColumn` | Post → User |
| N:M | `@ManyToMany` + `@JoinTable` | Post ↔ Tag |

---

### ResponseEntity — kiểm soát HTTP Response

```java
ResponseEntity.ok(data)              // 200 OK + body
ResponseEntity.notFound().build()    // 404 Not Found
ResponseEntity.badRequest().build()  // 400 Bad Request
ResponseEntity.created(uri).body(data) // 201 Created
ResponseEntity.noContent().build()   // 204 No Content
```

---

### Stream API — xử lý danh sách

```java
// Chuyển List<User> → List<UserDto>
userRepo.findAll()
    .stream()
    .map(this::map)    // gọi method map() cho từng phần tử
    .toList();

// Lấy tên từ List<Tag>
tags.stream()
    .map(tag -> tag.getName())  // hoặc viết ngắn: Tag::getName
    .toList();
```

---

## 7. Lỗi thường gặp và cách sửa

### Lỗi 1 — Gọi nhầm setter

```java
// ❌ SAI
user.setName(request.getName());
user.setName(request.getEmail());  // gọi setName hai lần!

// ✅ ĐÚNG
user.setName(request.getName());
user.setEmail(request.getEmail());
```

---

### Lỗi 2 — `new Object()` thay vì fetch từ DB khi update

```java
// ❌ SAI — tạo object mới → save() sẽ INSERT thêm record mới
public UserDto updateUser(Long id, CreateUserDto request) {
    User user = new User();
    user.setName(request.getName());
    userRepo.save(user);
}

// ✅ ĐÚNG — fetch từ DB trước → save() sẽ UPDATE
public UserDto updateUser(Long id, CreateUserDto request) {
    User user = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found"));
    user.setName(request.getName());
    userRepo.save(user);
}
```

---

### Lỗi 3 — Infinite recursion (vòng lặp JSON vô hạn)

```java
// ❌ SAI — User in Tutorial, Tutorial in User → lặp mãi
// Không dùng @JsonManagedReference / @JsonBackReference

// ✅ ĐÚNG
// Phía User (cha):
@JsonManagedReference
private List<Tutorial> tutorials;

// Phía Tutorial (con):
@JsonBackReference
private User user;
```

---

### Lỗi 4 — DTO thiếu quan hệ, API không trả về đủ data

```java
// ❌ SAI — UserDto không có posts → GET /users/1 không thấy post nào
public class UserDto {
    private Long id;
    private String name;
    private String email;
    // thiếu List<PostDto> posts
}

// ✅ ĐÚNG
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<PostDto> posts;  // thêm quan hệ vào DTO
}
// Và phải map trong Service: dto.setPosts(...)
```

---

*Cập nhật lần cuối: 06/03/2026*
