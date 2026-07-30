# 🚀 HƯỚNG DẪN SỬ DỤNG TEMPLATE THI THỰC HÀNH HSF302 (SPRING BOOT + THYMELEAF)

- Thư mục "SE20A11_DE190293_SP26_PE1" là đề bài "Tour Management"

- Thư mục "SE20A11_DE190293_CMS_PE" là đề bài "Car Management"

- Thư mục "trial" là đề bài "Shoes Management"


Thư mục `template_copy_paste` chứa sẵn toàn bộ bộ khung (Boilerplate) cho bài thi Practical Exam môn HSF302 Spring Boot + Thymeleaf. Khi bắt đầu bài thi, bạn chỉ cần copy folder này, đổi tên dự án và refactor tên thuộc tính theo yêu cầu của đề bài.

---

## 📁 CẤU TRÚC DỰ ÁN MẪU

```text
template_copy_paste/
├── src/main/java/com/template_copy_paste/
│   ├── TemplateCopyPasteApplication.java  # Main class Spring Boot (đã đặt đúng root package)
│   ├── ServletInitializer.java             # Cấu hình WAR
│   ├── annotation/                         # Bộ Custom Annotations & Engine Validate tự dựng
│   │   ├── CustomValidationEngine.java
│   │   ├── NotBlank.java
│   │   ├── StringLength.java
│   │   ├── IntRange.java
│   │   ├── PriceRange.java
│   │   ├── ValidDate.java
│   │   ├── ValidStatus.java
│   │   ├── AlphaNumeric.java
│   │   ├── VNPhone.java
│   │   ├── ValidEmail.java
│   │   └── RegexPattern.java
│   ├── controller/
│   │   └── TemplateController.java        # Controller mẫu (Search, Delete, Add, View)
│   ├── dto/
│   │   └── TemplateDto.java               # DTO mẫu chứa annotation validation
│   ├── entity/
│   │   └── TemplateEntity.java            # Entity JPA mẫu
│   ├── repository/
│   │   └── TemplateRepository.java        # JpaRepository mẫu (Search, Exists)
│   └── service/
│       ├── TemplateService.java
│       └── impl/
│           └── TemplateServiceImpl.java   # Service CRUD chuẩn
└── src/main/resources/
    ├── application.properties             # Cấu hình Port, DB SQL Server
    └── templates/
        ├── template-list.html             # Màn hình danh sách & tìm kiếm & nút Delete/View
        ├── add-template.html              # Màn hình Form tạo mới (Có 2 loại Dropdown mẫu)
        └── view-template.html             # Màn hình Xem chi tiết (Read-only)
```

---

## 🛠️ QUY TRÌNH 5 BƯỚC COPY-PASTE KHI VÀO PHÒNG THI

1. **Copy folder `template_copy_paste`**: Copy ra thư mục mới và đổi tên thành Mã Số Sinh Viên / Tên Đề Bài (Ví dụ: `SE180000_DE01_PE`).
2. **Cấu hình DB trong `application.properties`**:
   - Thay đổi tên Database: `spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName={TÊN_DB_ĐỀ_BÀI};...`
   - Đổi Username/Password SQL Server nếu cần.
3. **Đổi tên Entity & DTO**:
   - Đổi `TemplateEntity` -> `Tour` / `Shoes` / `Product` theo đề.
   - Đổi `TemplateDto` -> `TourDto` / `ShoesDTO` theo đề.
   - Thay các trường (`id`, `name`, `price`, `startDate`,...) tương ứng.
4. **Cập nhật Repository, Service, Controller**:
   - Sửa các tên phương thức tìm kiếm (VD: `findByTourNameContainingIgnoreCase`).
   - Kiểm tra trùng lặp (VD: `existsByTourNameIgnoreCase`).
5. **Cập nhật 3 File HTML Thymeleaf**:
   - Sửa URL endpoint (`th:action="@{/tours}"`, `th:href="@{/tours/add}"`,...).
   - Đổi tên các trường `th:field="*{...}"` và cột hiển thị `th:text="${item....}"`.

---

## 🛡️ HƯỚNG DẪN CHI TIẾT BỘ CUSTOM ANNOTATION VALIDATION TỰ BUILD

Hệ thống sử dụng Reflection Engine (`CustomValidationEngine`) tự dựng để kiểm tra dữ liệu đầu vào mà không bị phụ thuộc vào Jakarta Bean Validation chuẩn.

### 1. Cách Gọi Validation Trong Controller (`@PostMapping("/add")`)

```java
@PostMapping("/add")
public String addEntity(@ModelAttribute("dto") MyDto dto, Model model, RedirectAttributes redirectAttributes) {
    // 1. Gọi Engine validate tự dựng
    Map<String, String> errors = CustomValidationEngine.validate(dto);

    // 2. Kiểm tra trùng lặp (nếu đề bài yêu cầu kiểm tra tên/mã đã tồn tại)
    if (myService.existedName(dto.getName())) {
        errors.put("name", "Name: " + dto.getName() + " already exists in system");
    }

    // 3. Nếu có lỗi -> Gửi errors sang View và giữ lại dữ liệu form
    if (!errors.isEmpty()) {
        model.addAttribute("errors", errors);
        model.addAttribute("dto", dto); // Giữ dữ liệu đã gõ
        return "add-form";
    }

    // 4. Nếu thành công -> Lưu và Redirect kèm Flash Message
    myService.add(dto);
    redirectAttributes.addFlashAttribute("message", "Added successfully");
    return "redirect:/my-endpoint";
}
```

### 2. Danh Sách 10 Custom Annotation & Cách Gắn Vào DTO

| Annotation | Thuộc tính /Tham số | Mục đích & Cách dùng | Ví dụ gắn vào DTO Field |
| :--- | :--- | :--- | :--- |
| `@NotBlank` | `message` | Bắt buộc nhập, không được null hoặc rỗng/khoảng trắng. **(Luôn chạy đầu tiên)** | `@NotBlank(message = "Name is required")` |
| `@StringLength` | `min`, `max`, `message` | Kiểm tra độ dài chuỗi và tự động chặn khoảng trắng 2 đầu. | `@StringLength(min = 1, max = 200, message = "Length 1-200")` |
| `@IntRange` | `min`, `max`, `message` | Kiểm tra giá trị số nguyên trong khoảng `[min, max]`. | `@IntRange(min = 1, max = 1000)` |
| `@PriceRange` | `min`, `max`, `message` | Kiểm tra giá trị số thực (Double/Float) trong khoảng `(min, max)`. | `@PriceRange(min = 1.0, max = 10000.0)` |
| `@ValidDate` | `futureOnly`, `maxDaysFromToday`, `message` | Kiểm tra ngày (LocalDate/String): Bắt buộc ngày tương lai và/hoặc tối đa N ngày tính từ hôm nay. | `@ValidDate(futureOnly = true, maxDaysFromToday = 300)` |
| `@ValidStatus` | `allowed`, `caseSensitive`, `message` | Kiểm tra chuỗi nằm trong danh sách các giá trị cho phép. | `@ValidStatus(allowed = {"AC-Active", "IN-Inactive", "DR-Draft"})` |
| `@AlphaNumeric` | `message` | Chỉ cho phép chữ cái (a-Z) và số (0-9). | `@AlphaNumeric(message = "Only letters and numbers allowed")` |
| `@VNPhone` | `message` | Kiểm tra số điện thoại Việt Nam (10 chữ số, bắt đầu bằng 0). | `@VNPhone(message = "Invalid VN phone number")` |
| `@ValidEmail` | `message` | Kiểm tra định dạng Email chuẩn. | `@ValidEmail(message = "Invalid email format")` |
| `@RegexPattern` | `regexp`, `message` | Kiểm tra theo biểu thức chính quy (Regex) bất kỳ. | `@RegexPattern(regexp = "^[A-Z]{2}-\\d{3}$")` |

---

## 🔍 HƯỚNG DẪN CHI TIẾT VỀ REGEX & CÁC MẪU REGEX THƯỜNG GẶP TRONG ĐỀ THI

Trong bài thi practical, nếu đề bài yêu cầu một định dạng mã hoặc dữ liệu đặc thù chưa có sẵn annotation riêng, bạn chỉ cần dùng `@RegexPattern(regexp = "...")`.

### 1. Ý Nghĩa Các Ký Tự Regex Cơ Bản
- `^` : Bắt đầu chuỗi.
- `$` : Kết thúc chuỗi.
- `[a-z]` : Các chữ cái thường từ `a` đến `z`.
- `[A-Z]` : Các chữ cái in hoa từ `A` đến `Z`.
- `[0-9]` hoặc `\\d` : Các chữ số từ `0` đến `9`.
- `{n}` : Đúng `n` lần xuất hiện (Ví dụ: `\\d{3}` = đúng 3 số).
- `{n,m}` : Xuất hiện từ `n` đến `m` lần (Ví dụ: `\\d{3,5}` = từ 3 đến 5 số).
- `+` : 1 hoặc nhiều lần xuất hiện.
- `*` : 0 hoặc nhiều lần xuất hiện.
- `?` : 0 hoặc 1 lần xuất hiện (có hoặc không).
- `|` : Hoặc (Ví dụ: `(03|05|09)`).

> ⚠️ **LƯU Ý:** Trong Java annotation String, dấu `\` phải được escape thành `\\` (Ví dụ: `\\d` chứ không viết `\d`).

---

### 2. Bảng 10 Mẫu Regex "Kinh Điển" Hay Rơi Vào Đề Thi

#### 1. Mã Code Dạng 2 Chữ Cái In Hoa + 3 Chữ Số (VD: `SH001`, `TR999`, `PE012`)
```java
@NotBlank(message = "Code is required")
@RegexPattern(regexp = "^[A-Z]{2}\\d{3}$", message = "Code must be 2 uppercase letters followed by 3 digits (e.g. SH001)")
private String shoesNo;
```

#### 2. Mã Code Có Dấu Gạch Ngang (VD: `PRD-1234`, `ORD-001`)
```java
// 3 chữ in hoa + dấu '-' + 3 đến 4 chữ số
@RegexPattern(regexp = "^[A-Z]{3}-\\d{3,4}$", message = "Code format must be PRD-1234")
private String productCode;
```

#### 3. Mã Sinh Viên / Mã Học Viên (VD: `SE180000`, `HE171234`)
```java
// 2 chữ cái in hoa + 6 chữ số
@RegexPattern(regexp = "^[A-Z]{2}\\d{6}$", message = "Student Code must be 2 uppercase letters and 6 digits")
private String studentId;
```

#### 4. Số Điện Thoại Việt Nam (10 chữ số bắt đầu bằng 0)
```java
// Bắt đầu bằng 0 và tiếp theo là đúng 9 chữ số
@RegexPattern(regexp = "^0\\d{9}$", message = "Phone must be 10 digits starting with 0")
private String phone;

// Hoặc kiểm tra chặt chẽ đầu số nhà mạng (03, 05, 07, 08, 09)
@RegexPattern(regexp = "^(03|05|07|08|09)\\d{8}$", message = "Invalid VN phone operator code")
private String phone;
```

#### 5. Số CMND / CCCD (9 chữ số hoặc 12 chữ số)
```java
@RegexPattern(regexp = "^\\d{9}(\\d{3})?$", message = "CCCD must be 9 or 12 digits")
private String citizenId;
```

#### 6. Email Chuẩn
```java
@RegexPattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
private String email;
```

#### 7. Chuỗi Chỉ Chứa Chữ & Số (AlphaNumeric không chứa ký tự đặc biệt)
```java
@RegexPattern(regexp = "^[a-zA-Z0-9]+$", message = "Only letters and numbers allowed")
private String username;
```

#### 8. Chuỗi Ngày Tháng (Format `YYYY-MM-DD` hoặc `DD/MM/YYYY`)
```java
// Format YYYY-MM-DD (VD: 2026-07-30)
@RegexPattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must be YYYY-MM-DD")
private String dateString;

// Format DD/MM/YYYY (VD: 30/07/2026)
@RegexPattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date format must be DD/MM/YYYY")
private String dateString;
```

#### 9. Số Thập Phân / Giá Tiền Tối Đa 2 Số Sau Dấu Chấm (VD: `10`, `99.9`, `150.50`)
```java
@RegexPattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Must be positive number with up to 2 decimal places")
private String priceString;
```

#### 10. Biển Số Xe Việt Nam (VD: `29A-123.45` hoặc `43F1-1234`)
```java
@RegexPattern(regexp = "^\\d{2}[A-Z]{1,2}\\d?-\\d{4,5}$", message = "Invalid license plate format")
private String licensePlate;
```

---

### 3. Cách Hiển Thị Lỗi Bằng Thymeleaf Trong `add-template.html`

Mỗi ô input trong Form đều có một thẻ `<span>` bên dưới để hứng và hiển thị thông báo lỗi màu đỏ từ `Map<String, String> errors`:

```html
<!-- Input Text & Hiển thị lỗi tương ứng -->
<tr>
    <td><label for="tourName">Tour Name:</label></td>
    <td>
        <input type="text" id="tourName" th:field="*{tourName}" placeholder="Enter name..."/>
        <!-- Thẻ span hiển thị lỗi màu đỏ -->
        <span th:if="${errors != null and errors.get('tourName') != null}"
              th:text="${errors.get('tourName')}"
              style="color: red;"></span>
    </td>
</tr>
```

---

## 📋 MẪU 2 DẠNG DROPDOWN TRONG FORM THÊM MỚI (`add-template.html`)

Trong bài thi thường có 2 dạng ô chọn Dropdown `<select>`:

### Dạng A: Giá Trị Cố Định (Hardcoded Status / Enum / Constant)
Dùng khi đề bài ghi rõ các trạng thái cố định (Ví dụ: `AC-Active`, `IN-Inactive`, `DR-Draft`):

```html
<!-- FIELD 5A: Dropdown CỐ ĐỊNH (Hardcoded Status) -->
<tr>
    <td><label for="status">Status:</label></td>
    <td>
        <select id="status" th:field="*{status}">
            <option value="">-- Select Status --</option>
            <option value="AC-Active">AC-Active</option>
            <option value="IN-Inactive">IN-Inactive</option>
            <option value="DR-Draft">DR-Draft</option>
        </select>
        <small style="color: gray;">(AC-Active, IN-Inactive, DR-Draft)</small>
        <span th:if="${errors != null and errors.get('status') != null}"
              th:text="${errors.get('status')}"
              style="color: red;"></span>
    </td>
</tr>
```

### Dạng B: Dữ Liệu Động Lấy Từ Database (Foreign Key List)
Dùng khi dropdown hiển thị danh sách entity phụ lấy từ DB (Ví dụ: `ShoesType`, `Category`). Cần truyền danh sách này từ Controller qua `model.addAttribute("types", list)`:

```html
<!-- FIELD 5B: Dropdown DỮ LIỆU ĐỘNG (Lấy từ DB qua th:each) -->
<tr>
    <td><label for="type">Type:</label></td>
    <td>
        <select id="type" th:field="*{type}">
            <option value="">-- Select Type --</option>
            <!-- Duyệt danh sách truyền từ Controller -->
            <option th:each="t : ${types}"
                    th:value="${t.typeName}"
                    th:text="${t.typeCode + '-' + t.typeName}">
            </option>
        </select>
        <span th:if="${errors != null and errors.get('type') != null}"
              th:text="${errors.get('type')}"
              style="color: red;"></span>
    </td>
</tr>
```

---

## ⚡ CHECKLIST NHANH KHI ĐI THI
- [ ] Xóa/Clean target trước khi chạy thử (`./mvnw clean compile`).
- [ ] Kiểm tra kết nối SQL Server trong `application.properties`.
- [ ] Đảm bảo `@SpringBootApplication` nằm ở root package `com.{package_name}`.
- [ ] Thẻ `<form>` trong Thymeleaf có `th:object="${dtoName}"` matching với `@ModelAttribute("dtoName")` ở Controller.
- [ ] Nút Delete có `onclick="return confirm('Are you sure...?');"`.
