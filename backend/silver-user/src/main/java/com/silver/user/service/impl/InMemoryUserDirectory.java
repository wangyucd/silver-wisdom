package com.silver.user.service.impl;

import com.silver.user.model.AdminAccount;
import com.silver.user.model.UserAccount;
import com.silver.user.model.UserInterestTag;
import com.silver.user.service.UserDirectory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserDirectory implements UserDirectory {

    private final Map<Long, AdminAccount> admins = new ConcurrentHashMap<>();
    private final Map<String, Long> adminByUsername = new ConcurrentHashMap<>();
    private final Map<Long, UserAccount> users = new ConcurrentHashMap<>();
    private final Map<String, Long> userByOpenId = new ConcurrentHashMap<>();
    private final AtomicLong userIdSequence = new AtomicLong(10000);

    public InMemoryUserDirectory(BCryptPasswordEncoder passwordEncoder) {
        LocalDateTime now = LocalDateTime.now();

        AdminAccount admin = new AdminAccount();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        admin.setName("系统管理员");
        admin.setStatus("ENABLED");
        admin.setCreatedAt(now.minusDays(7));
        admin.setUpdatedAt(now.minusDays(7));
        admin.setLastLoginTime(now.minusHours(2));
        admins.put(admin.getId(), admin);
        adminByUsername.put(admin.getUsername(), admin.getId());

        UserAccount first = buildSeedUser(10001L, "mock_seed_pet", "银发用户10001", now.minusDays(2), "ENABLED",
                List.of(new UserInterestTag("宠物", 1.0), new UserInterestTag("健康", 0.8)));
        UserAccount second = buildSeedUser(10002L, "mock_seed_novel", "银发用户10002", now.minusDays(1), "ENABLED",
                List.of(new UserInterestTag("小说", 1.0), new UserInterestTag("二次元", 0.6)));
        UserAccount third = buildSeedUser(10003L, "mock_seed_disabled", "银发用户10003", now.minusDays(3), "DISABLED",
                List.of(new UserInterestTag("养生", 1.0)));

        saveSeedUser(first);
        saveSeedUser(second);
        saveSeedUser(third);
        userIdSequence.set(10003L);
    }

    @Override
    public Optional<AdminAccount> findAdminByUsername(String username) {
        Long adminId = adminByUsername.get(username);
        return adminId == null ? Optional.empty() : Optional.ofNullable(admins.get(adminId));
    }

    @Override
    public Optional<AdminAccount> getAdminById(Long adminId) {
        return Optional.ofNullable(admins.get(adminId));
    }

    @Override
    public Optional<UserAccount> findUserByOpenId(String openId) {
        Long userId = userByOpenId.get(openId);
        return userId == null ? Optional.empty() : Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<UserAccount> getUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public UserAccount createMiniappUser(String openId) {
        long userId = userIdSequence.incrementAndGet();
        LocalDateTime now = LocalDateTime.now();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        userAccount.setOpenId(openId);
        userAccount.setNickname("银发用户" + userId);
        userAccount.setAvatarUrl("");
        userAccount.setStatus("ENABLED");
        userAccount.setCreatedAt(now);
        userAccount.setUpdatedAt(now);
        userAccount.setLastLoginTime(now);
        users.put(userId, userAccount);
        userByOpenId.put(openId, userId);
        return userAccount;
    }

    @Override
    public void saveUser(UserAccount userAccount) {
        users.put(userAccount.getId(), userAccount);
        userByOpenId.put(userAccount.getOpenId(), userAccount.getId());
    }

    @Override
    public void saveAdmin(AdminAccount adminAccount) {
        admins.put(adminAccount.getId(), adminAccount);
        adminByUsername.put(adminAccount.getUsername(), adminAccount.getId());
    }

    @Override
    public List<UserAccount> listUsers() {
        return new ArrayList<>(users.values());
    }

    private void saveSeedUser(UserAccount userAccount) {
        users.put(userAccount.getId(), userAccount);
        userByOpenId.put(userAccount.getOpenId(), userAccount.getId());
    }

    private UserAccount buildSeedUser(Long id, String openId, String nickname, LocalDateTime createdAt, String status,
                                      List<UserInterestTag> tags) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setOpenId(openId);
        userAccount.setNickname(nickname);
        userAccount.setAvatarUrl("");
        userAccount.setStatus(status);
        userAccount.setCreatedAt(createdAt);
        userAccount.setUpdatedAt(createdAt);
        userAccount.setLastLoginTime(createdAt.plusHours(6));
        userAccount.getTags().addAll(tags);
        return userAccount;
    }
}
