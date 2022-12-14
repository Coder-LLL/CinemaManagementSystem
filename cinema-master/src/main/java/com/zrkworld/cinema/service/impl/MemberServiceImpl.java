package com.zrkworld.cinema.service.impl;

import com.zrkworld.cinema.mapper.MemberMapper;
import com.zrkworld.cinema.pojo.CinemaResult;
import com.zrkworld.cinema.pojo.Member;
import com.zrkworld.cinema.pojo.MemberQuery;
import com.zrkworld.cinema.pojo.PageResult;
import com.zrkworld.cinema.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * @author lzh
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberMapper memberMapper;
    @Override
    public CinemaResult getMemberData(MemberQuery query) {
        PageResult<Member> pageResult = new PageResult<>();

        System.out.println("getMemberData query");
        System.out.println(memberMapper.selectByOrder(query));
        pageResult.setList(memberMapper.selectByOrder(query));

        /**
         * 对于分页取数据，要注意要排序，对于limit和count函数并用，需要用子查询，因为
         * limit是在最后执行的
         */
        pageResult.setPageTotal(memberMapper.selectCountByOrder(query));
        System.out.println("pageResult");
        System.out.println(pageResult);
        return CinemaResult.ok(pageResult);
    }

    @Override
    public CinemaResult memberRegister(Member member) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        member.setmemberId(uuid);
        memberMapper.insertMember(member);
        return CinemaResult.ok(member);
    }

    @Override
    public CinemaResult delMember(String memberId) {
        memberMapper.deleteMember(memberId);
        return CinemaResult.ok();
    }

    @Override
    public CinemaResult modifyMember(Member member) {
        memberMapper.updateMember(member);
        return CinemaResult.ok();
    }

    @Override
    public Long getMemberNum() {
         return memberMapper.selectCountByOrder(null);
    }

    @Override
    public Member loginMember(Member member) {
        return memberMapper.selectMemberByLogin(member);
    }
}
