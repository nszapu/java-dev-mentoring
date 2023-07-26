module com.jmp.cloud.service.impl {
    exports com.jmp.cloud.service.impl;
    requires transitive com.jmp.service.api;
    requires com.jmp.dto;
    uses com.jmp.service.api.Service;
    provides com.jmp.service.api.Service with com.jmp.cloud.service.impl.CloudService;
}