module com.appliaction {
    requires com.jmp.bank.api;
    requires com.jmp.service.api;
    requires com.jmp.dto;
    requires com.jmp.cloud.bank.impl;
    requires com.jmp.cloud.service.impl;
    uses com.jmp.bank.api.Bank;
    uses com.jmp.cloud.bank.impl.CloudBank;
    uses com.jmp.cloud.service.impl.CloudService;
    uses com.jmp.service.api.Service;
}