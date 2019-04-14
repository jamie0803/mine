package com.atguigu.atcrowdfunding.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.bean.Member;

@FeignClient("atcrowdfunding-member-service")
public interface MemberService {

    @RequestMapping("/member/queryMemberByLoginacct/{loginacct}")
    public Member queryMemberByLoginacct(@PathVariable("loginacct") String loginacct);

}
