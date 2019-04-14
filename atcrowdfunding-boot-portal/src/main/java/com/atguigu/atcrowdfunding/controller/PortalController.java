package com.atguigu.atcrowdfunding.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.exception.LoginFailureException;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.util.Const;

@Controller
public class PortalController extends BaseController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/member")
    public String member() {
        return "member/member";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/toLogin";
    }


    @ResponseBody
    @RequestMapping("/doMemberLogin")
    public Object doMemberLogin(Member member, HttpSession session) {
        start();
        try {

            Member loginMember = memberService.queryMemberByLoginacct(member.getLoginacct());

            if (loginMember == null) {
                throw new LoginFailureException(Const.LOGIN_LOGINACCT_ERROR);
            }

            if (!loginMember.getUserpswd().equals(member.getUserpswd())) {
                throw new LoginFailureException(Const.LOGIN_USERPSWD_ERROR);
            }

//			session.setAttribute(Const.LOGIN_MEMBER, loginMember);

            success(true);

        } catch (Exception e) {
            success(false);
            message(e.getMessage());
            e.printStackTrace();
        }

        return end();
    }


}
