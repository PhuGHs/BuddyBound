package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.PostVisibilityDto;
import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.entity.PostVisibility;
import com.mobile.buddybound.repository.MemberRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", imports = { MemberMapper.class }, uses = { MemberMapper.class, PostMapper.class })
public interface PostVisibilityMapper extends EntityMapper<PostVisibility, PostVisibilityDto> {
}
