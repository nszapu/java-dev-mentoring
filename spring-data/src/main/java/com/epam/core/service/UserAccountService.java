package com.epam.core.service;

import com.epam.core.dto.UserAccountDto;
import com.epam.core.entity.UserAccountEntity;
import com.epam.core.model.User;
import com.epam.core.model.UserAccount;
import com.epam.core.repository.UserAccountRepository;
import com.epam.core.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserAccountService {

    @Value("${user.accounts.path}")
    private String userAccountsPath;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;

    public void loadUserAccountsFromFile() throws IOException {
        List<UserAccountDto> userAccounts = Arrays.asList(mapper.readValue(new File(classLoader.getResource(userAccountsPath).getFile()), UserAccountDto[].class));
        userAccounts.forEach(userAccount -> userAccountRepository.save(convertUserAccountDtoToEntity(userAccount)));
    }

    @Transactional
    public UserAccount refillUserAccountBalance(User user, int amount) {
        UserAccountEntity account = userAccountRepository.findByUserId(user.getId()).orElseThrow();
        account.setBalance(amount);
        UserAccount result = convertUserAccountEntityToDto(userAccountRepository.save(account));
        log.info("Updating balance for user: " + user + ", user account: " + result);
        return result;
    }

    public UserAccount createUserAccount(UserAccount userAccount) {
        log.info("This userAccount was created: " + userAccount);
        UserAccountEntity userAccountEntity = convertUserAccountDtoToEntity(userAccount);
        return convertUserAccountEntityToDto(userAccountRepository.save(userAccountEntity));
    }

    public UserAccount updateUserAccount(UserAccount userAccount) {
        log.info("This userAccount was updated: " + userAccount);
        UserAccountEntity userAccountEntity = convertUserAccountDtoToEntity(userAccount);
        return convertUserAccountEntityToDto(userAccountRepository.save(userAccountEntity));
    }

    public boolean deleteUserAccount(long userAccountId) {
        log.info("The userAccount with this id was deleted: " + userAccountId);
        userAccountRepository.deleteById(userAccountId);
        return !userAccountRepository.existsById(userAccountId);
    }

    private UserAccount convertUserAccountEntityToDto(UserAccountEntity user) {
        UserAccount userAccount = new UserAccountDto();
        userAccount.setId(user.getId());
        userAccount.setUserId(user.getUser().getId());
        userAccount.setBalance(user.getBalance());
        return userAccount;
    }

    private UserAccountEntity convertUserAccountDtoToEntity(UserAccount userAccount) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(userAccount.getId());
        userAccountEntity.setUser(userRepository.findById(userAccount.getUserId()).orElseThrow());
        userAccountEntity.setBalance(userAccount.getBalance());
        return userAccountEntity;
    }
}
