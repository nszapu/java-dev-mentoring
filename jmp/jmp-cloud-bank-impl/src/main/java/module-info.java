module com.jmp.cloud.bank.impl {
    exports com.jmp.cloud.bank.impl;
    requires transitive com.jmp.bank.api;
    requires com.jmp.dto;
    provides com.jmp.bank.api.Bank with com.jmp.cloud.bank.impl.CloudBank;
}