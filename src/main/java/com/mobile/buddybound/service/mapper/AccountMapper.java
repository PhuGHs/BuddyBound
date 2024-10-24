package com.mobile.buddybound.service.mapper;

import com.mobile.buddybound.model.dto.AccountDto;
import com.mobile.buddybound.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, UserMapper.class})
public interface AccountMapper {
    Account toEntity(AccountDto accountDto);
    AccountDto toDto(Account account);
}
