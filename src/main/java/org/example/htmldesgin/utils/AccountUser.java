package org.example.htmldesgin.utils;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {
    private Integer id;
    private String username;
    private String role;
    private String telephone;
    private String email;
    private String remark;
}
