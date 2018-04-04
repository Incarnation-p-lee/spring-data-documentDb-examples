/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.documentdb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserRepositoryConfiguration.class})
public class UserRepositoryIntegrationTest {

    private static final String ID = "0123456789";
    private static final String EMAIL = "xxx-xx@xxx.com";
    private static final String NAME = "myName";

    @Autowired
    private UserRepository repository;

    @Before
    public void setup() {
        this.repository.deleteAll();
    }

    @After
    public void cleanup() {
        this.repository.deleteAll();
    }

    @Test
    public void testUserRepository() {
        final User user = new User(ID, EMAIL, NAME);

        this.repository.save(user);

        final User result = this.repository.findById(ID).get();
        Assert.notNull(result, "should be exist in database");
        Assert.isTrue(result.getId().equals(ID), "should be the same id");

		List<User> resultList = this.repository.findByName(user.getName());
		Assert.isTrue(resultList.size() == 1, "should be only one user here");
		Assert.isTrue(resultList.get(0).getName().equals(user.getName()), "should be same Name");

        resultList = this.repository.findByEmail(user.getEmail());
        Assert.isTrue(resultList.size() == 1, "should be only one user here");
        Assert.isTrue(resultList.get(0).getEmail().equals(user.getEmail()), "should be same Email");
    }
}

