package tuleuov.space.railway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {
    @Schema(description = "Имя пользователя", example = "Joh")
    @Size(min = 3, max = 50, message = "Username must have from 3 to 50 symbols")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "ИИН", example = "785940321456")
//    @Pattern(regexp = "^[0-9]{12}$", message = "ИИН должен содержать 12 цифр")
//    @Size(min = 12, max = 13, message = "ИИН должен содержать 12 символов")
    @NotBlank(message = "ИИН не может быть пустым")
    private String iin;

    @Schema(description = "Номер телефона", example = "88005553535")
//    @Pattern(regexp = "^[0-9]{11}$",  message = "Номер телефона должен содержать от 1 цифр")
//    @Size(min = 11, max = 12, message = "Номер телефона должен содержать 11 символов")
    @NotBlank(message = "Номер телефона не может быть пустым")
    private String phone;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}
