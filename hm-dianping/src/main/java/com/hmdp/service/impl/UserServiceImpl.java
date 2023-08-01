package com.hmdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import com.hmdp.utils.SystemConstants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.hmdp.utils.SystemConstants.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Result sendCode(String phone, HttpSession session) {
        //1.校验手机号
        if(RegexUtils.isPhoneInvalid(phone)){
            //不符合返回
            return Result.fail("请输入正确的手机号！");
        }

        //符合生成
        String code = RandomUtil.randomNumbers(6);

        //4.保存到session
        session.setAttribute(VERIFY_CODE, code);
        log.debug("发送短信验证码成功, 验证码" + code);
        //返回ok
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //提交手机号验证码，校验
        String phone = loginForm.getPhone();
        if(RegexUtils.isPhoneInvalid(phone)){
            //不符合返回
            return Result.fail("请输入正确的手机号！");
        }

        Object cacheCode = session.getAttribute(VERIFY_CODE);
        String code = loginForm.getCode();

        if (cacheCode == null || !cacheCode.toString().equals(code)){
            return Result.fail("验证码错误");
        }

        //select * from user where phone = ?
        User user = query().eq("phone", phone).one();

        if(user == null){
            //创建一个新的
            user = creatUserWithPhone(phone);
        }

        //有用户保存到session
        session.setAttribute(SAVED_USER, user);

        return Result.ok();
    }

    private User creatUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        save(user);

        return user;
    }
}
