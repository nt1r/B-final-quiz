package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeDto {
    @NotBlank(message = "姓名字段不能为空")
    String name;

    @NotBlank(message = "办公室字段不能为空")
    String office;

    @NotBlank(message = "邮箱字段不能为空")
    @Email(message = "邮箱格式不正确")
    String email;

    @NotBlank(message = "github字段不能为空")
    String github;

    @NotBlank(message = "zoom字段不能为空")
    String zoomId;
}
