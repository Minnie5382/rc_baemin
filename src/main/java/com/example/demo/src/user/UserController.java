package com.example.demo.src.user;

import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.review.model.PostReviewsReq;
import com.example.demo.src.review.model.PostReviewsRes;
import com.example.demo.src.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.*;
import java.util.List;

@RestController
@RequestMapping("/users") // http://vici-minn.shop:9000/users
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // 회원가입 메서드
    @ResponseBody
    @PostMapping("") // POST http://vici-minn.shop/users
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq.getUserTelNum(), postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 유저 정보 수정 메서드
    @ResponseBody
    @PatchMapping("/{userId}") // POST http://vici-minn.shop/users
    public BaseResponse<String> updateUser(@PathVariable("userId") int userId, @RequestBody PatchUserReq patchUserReq) {
        try{
            userService.updateUser(userId, patchUserReq);
            String result = "정상적으로 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 회원 탈퇴 메서드
    @ResponseBody
    @PatchMapping("/{userId}/status") // PATCH http://vici-minn.shop:9000/users/:userId/status
    public BaseResponse<String> deleteUser(@PathVariable ("userId") int userId) {
        try{
            userService.deleteUser(userId);
            String message = "성공적으로 탈퇴 되었습니다.";

            return new BaseResponse<>(message);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
