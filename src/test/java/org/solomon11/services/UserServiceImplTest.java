package org.solomon11.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.solomon11.dto.*;
import org.solomon11.exceptions.InvalidUsernameOrPassword;
import org.solomon11.exceptions.UserExistsException;
import org.solomon11.exceptions.UsernameNotFoundException;
import org.solomon11.models.TaskStatus;
import org.solomon11.models.TodoList;
import org.solomon11.models.User;
import org.solomon11.repository.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private Users users;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private TodolistRequest todolistRequest;
    private EditTodolistRequest editTodolistRequest;
    private DeleteTodolistRequest deleteTodolistRequest;
    private MarkTaskRequest markTaskRequest;
    private  LogoutRequest logoutRequest;
    private StartTaskRequest startTaskRequest;
    private ViewAllPendingTaskRequest viewAllPendingTaskRequest;

    @BeforeEach
    public void setUp(){
        users.deleteAll();

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");


        editTodolistRequest = new EditTodolistRequest();
        editTodolistRequest.setAuthor("username");
        editTodolistRequest.setStatus(TaskStatus.PENDING);
        editTodolistRequest.setTitle("title");

        deleteTodolistRequest = new DeleteTodolistRequest();
        deleteTodolistRequest.setAuthor("username");

        markTaskRequest = new MarkTaskRequest();
        markTaskRequest.setUsername("username");
        markTaskRequest.setTitle("title");


        viewAllPendingTaskRequest = new ViewAllPendingTaskRequest();
        viewAllPendingTaskRequest.setUsername("username");


        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");

        startTaskRequest = new StartTaskRequest();
        startTaskRequest.setUsername("username");
        startTaskRequest.setTitle("title");



    }

    @Test
    public void testUserCanRegister(){
        users.deleteAll();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));


    }


    @Test
    public void testUserCannotRegisterTwice(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        try {
            userService.register(registerRequest);
        } catch (UserExistsException e) {
            assertThat(e.getMessage(), containsString("username already exists"));
        }
        assertThat(users.count(), is(1L));

    }

    @Test
    public void testUserCanLoginWithCorrect_Password(){
        users.deleteAll();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        var loginResponse = userService.login(loginRequest);
        assertThat(loginResponse.getId(), notNullValue());


    }
    @Test
    public void loginWrongUser_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existing_username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Non existent username");
        try {
            userService.login(loginRequest);
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), containsString("username not found"));
        }

    }

    @Test
    public void loginWithIncorrectPassword_throwsExceptionTest(){
        userService.register(registerRequest);
        loginRequest.setPassword("incorrectPassword");
        try {
            userService.login(loginRequest);
        } catch (InvalidUsernameOrPassword e) {
            assertThat(e.getMessage(), containsString("Invalid Username or password"));
        }
    }

    @Test
    public void testUserCanAdd_firstTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
       // todolistRequest.setStatus(TaskStatus.PENDING);
        var todolistResponse = userService.createTodolist(todolistRequest);
        checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(1));
        assertThat(todolistResponse.getListId(), notNullValue());

    }

    @Test
    public void testUserCanUpdateTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");
       // todolistRequest.setStatus(TaskStatus.PENDING);
        userService.createTodolist(todolistRequest);

        var foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        var savedTodolist = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        editTodolistRequest.setStatus(TaskStatus.SUCCESS);
        editTodolistRequest.setListId(savedTodolist.getId());
        var editTodolistResponse = userService.editTodoListWith(editTodolistRequest);
        foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        savedTodolist = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        assertThat(editTodolistResponse.getListId(), notNullValue());

    }

    @Test
    public void testUserCanDeleteTodoList_ListIsZero(){
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        userService.createTodolist(todolistRequest);
        var foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        var savedPost = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        deleteTodolistRequest.setListId(savedPost.getId());

        var deleteTodoListResponse = userService.deleteTodoTaskWith(deleteTodolistRequest);
        assertThat(deleteTodoListResponse.getListId(), notNullValue());


    }


    @Test
    public void testToViewAllPendingTask(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title1");
        userService.createTodolist(todolistRequest);
        viewAllPendingTaskRequest = new ViewAllPendingTaskRequest();
        viewAllPendingTaskRequest.setUsername(registerRequest.getUsername());
        List<TodoList> pendingTasks = userService.viewAllPendingTasks(viewAllPendingTaskRequest);
        assertEquals(2, pendingTasks.size());
        for (TodoList task : pendingTasks) {
            assertEquals(TaskStatus.PENDING, task.getStatus());
            assertTrue(task.getTitle().startsWith("title"));
        }
    }

    @Test
    public void testUserCan_ViewAllTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);
        todolistRequest.setTitle("title1");
        userService.createTodolist(todolistRequest);

        var checkName = users.findByUsername(registerRequest.getUsername());
        assertThat(checkName.getTodoList().size(), is(2));
        var todolistResponse = userService.viewAllTodoList(todolistRequest);
        assertThat(todolistResponse.size(), is(2));


    }

    @Test
    public void testUserCan_StartTask(){
        userService.register(registerRequest);
        userService.login(loginRequest);

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);

        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(1));

        var savedTodoList = checkUser.getTodoList().getFirst();

        startTaskRequest = new StartTaskRequest();
        startTaskRequest.setUsername(registerRequest.getUsername());
        startTaskRequest.setTitle("title");
        userService.startTask(startTaskRequest);
        var updateUser = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUser.getTodoList().size(), is(1));

        var updatedTask = updateUser.getTodoList().getFirst();
        assertThat(updatedTask.getStatus(), is(TaskStatus.IN_PROGRESS));

    }

    @Test
    public void testMarkTaskStatus(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        //todolistRequest.setStatus(TaskStatus.PENDING);
        userService.createTodolist(todolistRequest);
        var updateUserBeforeMarking = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUserBeforeMarking.getTodoList().size(), is(1));

        markTaskRequest = new MarkTaskRequest();
        markTaskRequest.setUsername(registerRequest.getUsername());
        markTaskRequest.setTitle("title");
        userService.markTaskStatus(markTaskRequest);
        var updateUserAfterMarking = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUserAfterMarking.getTodoList().size(), is(1));

        TodoList updatedTodoList = updateUserAfterMarking.getTodoList().getFirst();
        assertThat(updatedTodoList.getTitle(), is("title"));
        assertThat(updatedTodoList.getStatus(), is(TaskStatus.SUCCESS));

    }

    @Test
    public void testUserCan_AssignTask_to_A_User(){


    }



    @Test
    public void testUserCanLogout() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        var loginResponse = userService.logout(logoutRequest);
        assertThat(loginResponse.getUsername(), notNullValue());
    }

    }
