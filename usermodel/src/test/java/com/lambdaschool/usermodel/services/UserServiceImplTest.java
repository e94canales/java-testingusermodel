package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void A_findUserById() {
        assertEquals("test cinnamon", userService.findUserById(7).getUsername());
    }

    @Test (expected = EntityNotFoundException.class)
    public  void B_findUserByIdFail(){
        assertEquals("test admin", userService.findUserById(318908).getUsername());
    }

    @Test
    public void C_findByNameContaining() {
        assertEquals(1 , userService.findByNameContaining("cinnamon").size());
    }

    @Test
    public void D_findAll() {
        assertEquals(30, userService.findAll().size());
    }

    @Test
    public void E_delete() {
        userService.delete(15);
        assertEquals(29, userService.findAll().size());
    }

    @Test (expected = EntityNotFoundException.class)
    public void Ea_deleteNotFound(){
       userService.delete(4729879);
       assertEquals(29, userService.findAll().size());
    }

    @Test
    public void F_findByName() {
        assertEquals("test cinnamon", userService.findByName("test cinnamon").getUsername());
    }

    @Test
    public void G_save() {
        List<UserRoles> userRoles = new ArrayList<>();
        User newUser = new User("test erick",
                                "password",
                                "erick@erick.com",
                                userRoles);

        User addUser = userService.save(newUser);

        assertNotNull(addUser); // Makes sure initial returned object is not NULL
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals("test erick", foundUser.getUsername());
    }

    @Test
    public void H_update() {
        List<UserRoles> userRoles = new ArrayList<>();
        User newUser = new User("test erick2",
                "password2",
                "erick2@2erick.com",
                userRoles);

        User updateUser = userService.update(newUser, 7);
        assertEquals("test erick2", updateUser.getUsername());
    }

    @Test
    public void I_getCountUserEmails() {
    }

    @Test
    public void J_deleteUserRole() {
        User cin = userService.findUserById(7);
        userService.deleteUserRole(7, 2);
        assertEquals(2, cin.getRoles().size());
    }

    @Test
    public void K_addUserRole() {
    }
}