module com.jmp.cloud.service.impl {
    requires transitive com.jmp.service.api;
    requires com.jmp.dto;
    requires com.repository;
    requires com.jmp.bank.api;
    requires com.exception;
    uses com.jmp.service.api.Service;
    uses com.repository.SubscriptionRepository;
    uses com.repository.UserRepository;
    provides com.jmp.service.api.Service with com.jmp.cloud.service.impl.CloudService;
}