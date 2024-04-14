package org.solomon11.repository;

import org.junit.jupiter.api.Test;
import org.solomon11.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
public class UsersTest {
    @Autowired
    private Users users;

    @Test
    public void saveTest(){
       users.deleteAll();
        User user1 =new User();
        users.save(user1);
        assertThat(users.count(), is(1L));
    }



}