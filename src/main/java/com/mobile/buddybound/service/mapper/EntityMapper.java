package com.mobile.buddybound.service.mapper;

import org.mapstruct.Mapper;

public interface EntityMapper <E, D>{
    E toEntity(D d);
    D toDto(E e);
}
