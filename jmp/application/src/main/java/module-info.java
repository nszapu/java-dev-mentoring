module com.appliaction {
    requires com.jmp.bank.api;
    requires com.jmp.service.api;
    requires com.jmp.dto;
    requires com.exception;
    uses com.jmp.bank.api.Bank;
    uses com.jmp.service.api.Service;
}