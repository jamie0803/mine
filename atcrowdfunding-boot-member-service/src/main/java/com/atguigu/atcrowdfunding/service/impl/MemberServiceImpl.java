package com.atguigu.atcrowdfunding.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.dao.MemberDao;
import com.atguigu.atcrowdfunding.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member queryMemberByLoginacct(String loginacct) {
        return memberDao.queryMemberByLoginacct(loginacct);
    }

}
