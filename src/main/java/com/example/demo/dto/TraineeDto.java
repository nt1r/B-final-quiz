package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeDto {
    @NotNull(message = "姓名字段不能为空")
    String name;

    @NotNull(message = "办公室字段不能为空")
    String office;

    @NotNull(message = "邮箱字段不能为空")
    @Email(message = "邮箱格式不正确")
    String email;

    @NotNull(message = "github字段不能为空")
    String github;

    @NotNull(message = "zoom字段不能为空")
    String zoomId;
}
