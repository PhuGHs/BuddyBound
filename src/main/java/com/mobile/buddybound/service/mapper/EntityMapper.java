package com.mobile.buddybound.service.mapper;

public interface EntityMapper <E, D>{
    E toEntity(D d);
    D toDto(E e);
}
