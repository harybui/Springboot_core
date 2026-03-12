# 📓 NOTE KINH NGHIỆM - BUỔI 5 (Bookstore API)

Tổng hợp các kiến thức cốt lõi và bài học "nhớ đời" từ quá trình làm project Buổi 5.

---

## 1. 🏗️ Chuẩn hóa API Response (Best Practice)
Thay vì trả về object lộn xộn, mọi API (thành công hay thất bại) đều phải được bọc trong `ApiResponse<T>`.

**Lý do:** Giúp Frontend/Mobile dễ dàng xử lý chung một cấu trúc:
```json
{
  "success": true/false,
  "message": "Thông báo cho user",
  "data": { ... } // Payload thực sự (có thể null)
}
```

---

## 2. 🛡️ Xử lý lỗi tập trung (Global Exception Handler)
Không cần viết `try-catch` lặp đi lặp lại trong các Controller nữa. 

- Tạo thư mục `exception` chứa các lỗi tùy chỉnh: `NotFoundException`, `AlreadyExistsException` (kế thừa `RuntimeException`).
- Dùng `@RestControllerAdvice` để lập một "Trạm thu phí bắt lỗi" toàn cục.
- Trong class trạm thu phí, dùng `@ExceptionHandler(TênLỗi.class)` để định nghĩa status code (`404 NOT FOUND`, `400 BAD REQUEST`, `409 CONFLICT`, `500 INTERNAL SERVER ERROR`) và trả về `ApiResponse` lỗi.

---

## 3. 🐛 Khắc phục lỗi: `map()` cannot be applied to method reference
**Triệu chứng:** Khi dùng Java Stream `stream().map(this::mapBookToDto)`.
**Thông báo lỗi IDE:** IDE nhầm Entity `Book` với class `java.awt.print.Book`.

**Cách giải quyết 2 bước:**
1. **Tránh ambiguity (sự mơ hồ) trong method reference:** Chuyển sang dùng lambda expression tường minh để Java tự hiểu kiểu dữ liệu:
   ```java
   // ❌ Dễ lỗi nhận diện class:
   .map(this::mapBookToDto)
   
   // ✅ Rõ ràng, an toàn:
   .map(book -> mapBookToDto(book))
   ```
2. **Khai báo kiểu đầy đủ (Fully Qualified Name):** Trong hàm `mapBookToDto`, chỉ rõ package của Entity:
   ```java
   private BookDto mapBookToDto(com.example.bookstore_api.entities.Book book) { ... }
   ```

---

## 4. 🐛 Khắc phục lỗi: Khởi động Spring Boot thất bại (BeanCreationException)
**Thông báo lỗi:** `Collection 'Category.books' is 'mappedBy' a property named 'categories' which does not exist in the target entity 'Book'`

**Nguyên lý `@OneToMany(mappedBy = "...")`:**
Giá trị trong `mappedBy` KHÔNG PHẢI là tên bảng, cũng không phải là tên class. Nó **BẮT BUỘC PHẢI KHỚP CHÍNH XÁC VỚI TÊN BIẾN (field name)** đã khai báo trong Entity ở phía đối diện (`@ManyToOne`).

**Lỗi sai:**
Phía `Book.java` khai báo: `private Category category;` (tên biến là `category`).
Phía `Category.java` lại ghi: `@OneToMany(mappedBy = "categories")` ❌

**Cách fix:** Đổi lại cho khớp tên biến của đối diện.
```java
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL) ✅
```

---

## 5. ♾️ Tránh vòng lặp JSON vô tận (Infinite Recursion)
Khi dùng hai chiều `@OneToMany` và `@ManyToOne` (Ví dụ: Category có list Book, Book lại có Category). Khi fetch data, Jackson sẽ gọi lồng nhau liên tục gây lỗi StackOverflow.

**Cách giải quyết:**
- **Phía Cha (OneToMany) - `Category.java`**: Thêm `@JsonManagedReference` (Phía này sẽ được serialize và hiển thị bình thường).
- **Phía Con (ManyToOne) - `Book.java`**: Thêm `@JsonBackReference` (Phía này sẽ bị ẩn đi để chặn vòng lặp).

*Lưu ý: Vì phía `Book` không gọi ra được `Category` object do bị ẩn, trong `BookDto` ta phải gọi rõ `book.getCategory().getName()` để truyền thông tin cho Client.*
