module com.jmp.cloud.bank.impl {
    uses com.repository.UserRepository;
    exports com.jmp.cloud.bank.impl;
    requires transitive com.jmp.bank.api;
    requires com.jmp.dto;
    requires com.repository;
    provides com.jmp.bank.api.Bank with com.jmp.cloud.bank.impl.CloudBank;
}