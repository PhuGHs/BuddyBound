package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MemberDto;
import com.mobile.buddybound.model.dto.MemberPostDto;
import com.mobile.buddybound.model.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, GroupMapper.class })
public interface MemberMapper extends EntityMapper<Member, MemberDto> {
    MemberPostDto toMemberDto(Member member);
}
