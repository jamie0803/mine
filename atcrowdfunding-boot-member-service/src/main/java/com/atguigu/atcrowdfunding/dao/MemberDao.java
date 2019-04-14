package com.atguigu.atcrowdfunding.dao;

import org.apache.ibatis.annotations.Select;

import com.atguigu.atcrowdfunding.bean.Member;

public interface MemberDao {

    @Select("select * from t_member where loginacct=#{loginacct}")
    Member queryMemberByLoginacct(String loginacct);

}
