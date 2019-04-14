package com.atguigu.atcrowdfunding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.service.MemberService;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    //完成登录用户查询
    @RequestMapping("/member/queryMemberByLoginacct/{loginacct}")
    public Member queryMemberByLoginacct(@PathVariable("loginacct") String loginacct) {
        return memberService.queryMemberByLoginacct(loginacct);
    }

}
