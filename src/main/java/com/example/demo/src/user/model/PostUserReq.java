package com.example.demo.src.user.model;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserReq {
    private String nickname;
    private String email;
    private String pwd;
    private String userTelNum;
    private String location;
    private String emailReceive;
    private String smsReceive;

}
