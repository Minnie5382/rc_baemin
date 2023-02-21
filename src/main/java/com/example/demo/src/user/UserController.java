// User도메인의 controller 파일 : routing 처리와 request를 넘겨주는 역할. 형식적 validation.
package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users") // 해당 controller의 기본적인 매핑 정보
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

//    /**
//     * 회원 조회 API
//     * [GET] /users
//     * 회원 번호 및 이메일 검색 조회 API
//     * [GET] /users? Email=
//     * @return BaseResponse<List<GetUserRes>>
//     */
//    //Query String
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/users // GET : 메서드 값을 넣어줌
//    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) { // query string을 받아오는 것
//        // GetUserRes : 이 부분에는 응답값을 넣어줌. 모델로.
//        try{
//            if(Email == null){
//                List<GetUserRes> getUsersRes = userProvider.getUsers();
//                return new BaseResponse<>(getUsersRes);
//            }
//            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 회원 1명 조회 API
//     * [GET] /users/:userId
//     * @return BaseResponse<GetUserRes>
//     */
//    // Path-variable
//    @ResponseBody
//    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/app/users/:userId (path variable)
//    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId) {
//        // Get Users
//        try{
//            GetUserRes getUserRes = userProvider.getUser(userId);
//            return new BaseResponse<>(getUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }
//
    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // email validation
        // 이메일 입력했는지?
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL); // 이메일을 입력해주세요.
        }
        // 이메일 형식이 맞는지?
        if(!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL); // 이메일 형식을 확인해주세요.
        }
        // 휴대폰 번호 입력했는지?
        if(postUserReq.getUserTelNum() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_TELNUM); // 휴대폰 번호를 입력해주세요.
        }
        // 휴대폰 번호 형식이 맞는지?
        if(postUserReq.getUserTelNum().length() != 11) {
            return new BaseResponse<>(POST_USERS_INVALID_TELNUM); // 휴대폰 번호 형식을 확인해주세요.
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
//        try{
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userId
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userId}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userId") int userId, @RequestBody User user){
//        try {
//            //jwt에서 email 추출.
//            int userIdByJwt = jwtService.getUserId();
//
//            //userId와 접근한 유저가 같은지 확인
//            if(userId != userIdByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            // 같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(user.getNickname);
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//
}