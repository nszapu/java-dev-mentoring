module com.repository {
    exports com.repository;
    requires com.jmp.dto;
    provides com.repository.UserRepository with com.repository.UserRepository;
    provides com.repository.SubscriptionRepository with com.repository.SubscriptionRepository;
}
