package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.MemberDto;
import com.mobile.buddybound.model.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface MemberMapper extends EntityMapper<Member, MemberDto> {
}
