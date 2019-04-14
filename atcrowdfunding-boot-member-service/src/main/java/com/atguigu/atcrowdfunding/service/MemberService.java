package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.Member;

public interface MemberService {

    Member queryMemberByLoginacct(String loginacct);

}
