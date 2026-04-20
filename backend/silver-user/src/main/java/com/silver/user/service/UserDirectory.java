package com.silver.user.service;

import com.silver.user.model.AdminAccount;
import com.silver.user.model.UserAccount;
import java.util.List;
import java.util.Optional;

public interface UserDirectory {

    Optional<AdminAccount> findAdminByUsername(String username);

    Optional<AdminAccount> getAdminById(Long adminId);

    Optional<UserAccount> findUserByOpenId(String openId);

    Optional<UserAccount> getUserById(Long userId);

    UserAccount createMiniappUser(String openId);

    void saveUser(UserAccount userAccount);

    void saveAdmin(AdminAccount adminAccount);

    List<UserAccount> listUsers();
}
