package com.delabtory.sc.userupgradepolicy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.deleteAll();
        userRepository.save(new User(1L, "John", Level.BASIC, 100, 1));
        userRepository.save(new User(2L, "James", Level.SILVER, 5, 100));
        userRepository.save(new User(3L, "Harry", Level.GOLD, 200, 200));
    }

    @Test
    public void testUpgradeLevels() {
        // prepare
        UserLevelUpgradePolicy userLevelUpgradePolicy = new UserLevelUpgradePolicyImpl();
        userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy);

        // execute
        userService.upgradeLevels();

        // verify
        userRepository.findById(1L).ifPresent(user ->
                assertEquals(Level.SILVER, user.getLevel()));

        userRepository.findById(2L).ifPresent(user ->
                assertEquals(Level.GOLD, user.getLevel()));

        userRepository.findById(3L).ifPresent(user ->
                assertEquals(Level.GOLD, user.getLevel()));
    }

    @Test
    public void testUpgradeLevels_usingOtherPolicy() {

    }
}
