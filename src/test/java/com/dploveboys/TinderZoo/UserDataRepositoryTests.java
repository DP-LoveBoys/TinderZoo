package com.dploveboys.TinderZoo;

import com.dploveboys.TinderZoo.model.UserCredential;
import com.dploveboys.TinderZoo.repositories.UserCredentialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserDataRepositoryTests {
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private TestEntityManager testManager;

    @Test
    public void testCreateUser()
    {
        UserCredential user = new UserCredential();
        user.setEmail("someRandomMail@mail.com");
        user.setPassword("asdasd");
        user.setName("Andrei");

        UserCredential savedUser = repository.save(user);

        UserCredential existingUserCred = testManager.find(UserCredential.class, savedUser.getId());

        assertThat((existingUserCred.getEmail()).equals(user.getEmail()));
    }
}
