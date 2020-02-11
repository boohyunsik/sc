package com.delabtory.sc.userupgradepolicy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserRepository userRepository;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy) {
        userLevelUpgradePolicy = policy;
    }

    public void upgradeLevels() {
        List<User> users = this.userRepository.findAll();
        for (User user: users) {
            userLevelUpgradePolicy.upgradeLevel(user);
            userRepository.save(user);
        }
    }
}
