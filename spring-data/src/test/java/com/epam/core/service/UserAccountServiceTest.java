package com.epam.core.service;

import com.epam.core.dto.UserAccountDto;
import com.epam.core.dto.UserDto;
import com.epam.core.entity.UserAccountEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import com.epam.core.model.UserAccount;
import com.epam.core.repository.UserAccountRepository;
import com.epam.core.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserAccountService service;
    private UserAccountDto userAccountDto;
    private UserAccountEntity userAccountEntity;
    private UserEntity userEntity;

    @Before
    public void setup() {
        userAccountDto = new UserAccountDto();
        userAccountDto.setId(1);
        userAccountDto.setUserId(1);
        userAccountDto.setBalance(1000);
        userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(1);
        userEntity = new UserEntity();
        userEntity.setId(1);
        userAccountEntity.setUser(userEntity);
        userAccountEntity.setBalance(1000);
    }

    @Test
    public void testLoadUserAccountsFromFile() {

    }

    @Test
    public void testRefillUserAccountBalance() {
        when(userAccountRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(userAccountEntity));
        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccountEntity);
        User user = new UserDto();
        user.setId(1);
        assertEquals(userAccountDto, service.refillUserAccountBalance(user, 1000));
    }

    @Test
    public void testCreateUserAccount() {
        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccountEntity);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userEntity));
        UserAccount result = service.createUserAccount(userAccountDto);
        assertEquals(userAccountDto, result);
    }

    @Test
    public void testUpdateUserAccount() {
        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(userAccountEntity);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userEntity));
        UserAccount result = service.updateUserAccount(userAccountDto);
        assertEquals(userAccountDto, result);
    }

    @Test
    public void testDeleteEvent() {

    }
}
