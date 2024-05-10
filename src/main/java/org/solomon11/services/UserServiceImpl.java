package org.solomon11.services;

import org.solomon11.dto.*;
import org.solomon11.exceptions.*;
import org.solomon11.models.TaskStatus;
import org.solomon11.models.TodoList;
import org.solomon11.models.User;
import org.solomon11.repository.TodoListsRepository;
import org.solomon11.repository.Users;
import org.solomon11.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.solomon11.utilities.Mapper.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Users users;
    @Autowired
    private TodoListService todoListService;
    @Autowired
    private TodoListsRepository todoListsRepository;
    private User authenticatedUser;


    @Override
    public RegisterUserResponse register(RegisterRequest registerRequest) {

        validate(registerRequest.getUsername());
        User newUser = map(registerRequest);
        User savedUser = users.save(newUser);
        return registerResponseMap(savedUser);

    }

    @Override
    public LoginUserResponse login(LoginRequest loginRequest) {
        User foundUser = findUserBy(loginRequest.getUsername());
        if (!isPasswordIncorrect(foundUser, loginRequest.getPassword())) {
            authenticatedUser = foundUser;

            return mapLoginResponse(foundUser);
        } else
            throw new InvalidUsernameOrPassword("Invalid Username or password");


    }

    @Override
    public LogoutUserResponse logout(LogoutRequest logoutRequest) {
        authenticatedUser = null;
        User foundUser = findUserBy(logoutRequest.getUsername());
        User savedUser = users.save(foundUser);
        return mapLogoutResponse(savedUser);

    }

    @Override
    public StartTaskResponse startTask(StartTaskRequest startTaskRequest) {
        validateAuthentication();
        String username = startTaskRequest.getUsername();
        String title = startTaskRequest.getTitle();
        User foundUser = findUserBy(username);
        TodoList newTask = todoListService.startTaskWith(startTaskRequest);
        TodoList taskToStart = findTaskByTitle(foundUser, title);
        taskToStart.setStatus(TaskStatus.IN_PROGRESS);
        users.save(foundUser);

        return startTaskResponseMap(taskToStart);

    }

    private TodoList findTaskByTitle(User foundUser, String title) {
        TodoList taskToStart = null;
        for (TodoList todoList : foundUser.getTodoList()) {
            if (todoList.getTitle().equals(title)) {
                taskToStart = todoList;
                break;
            }
        }
        return taskToStart;
    }

    @Override
    public List<TodoList> viewAllPendingTasks(ViewAllPendingTaskRequest viewAllPendingTaskRequest) {
        validateAuthentication();
        User foundUser = findUserBy(viewAllPendingTaskRequest.getUsername());
        List<TodoList> todoLists = new ArrayList<>();
        for (TodoList todoList : foundUser.getTodoList()) {
            if (todoList.getStatus().equals(TaskStatus.PENDING)) {
                todoLists.add(todoList);
            }
        }
        return todoLists;

    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest) {
        validateAuthentication();
        String assignerUsername = assignTaskRequest.getAuthor();
        String assigneeUsername = assignTaskRequest.getUsername();
        User assigner = findUserBy(assignerUsername);
       User assignee = findUserBy(assigneeUsername);

        TodoList assignedTask = todoListService.assignTask(assignTaskRequest);
        assignee.getTodoList().add(assignedTask);
        users.save(assignee);

        return mapAssignTaskResponseWith(assignedTask);


    }


    private void validateAuthentication() {
        if (authenticatedUser == null)
            throw new InvalidLoginException("user must be login to perform any operations");
    }


    @Override
    public TodoListResponse createTodolist(TodolistRequest todolistRequest) {
        validateAuthentication();
        User foundUser = findUserBy(todolistRequest.getUsername());
        if (taskExistsForUser(foundUser, todolistRequest.getTitle()))
            throw new TaskExistsException("Task already exists");
        TodoList newTodoList = todoListService.createTodoListWith(todolistRequest);
        foundUser.getTodoList().add(newTodoList);
        users.save(foundUser);
        return mapCreateTodoListResponseWith(newTodoList);
    }

    private boolean taskExistsForUser(User foundUser, String title) {
        for (TodoList todoList : foundUser.getTodoList()) {
            if (todoList.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EditTodolistUserResponse editTodoListWith(EditTodolistRequest editTodolistRequest) {
        validateAuthentication();
        User author = findUserBy(editTodolistRequest.getAuthor());
        TodoList userList = findUserTaskBy(editTodolistRequest.getListId(), author);
        userList.setTitle(editTodolistRequest.getTitle());
        userList.setStatus(editTodolistRequest.getStatus());
        users.save(author);
        return todoListService.editTodoListWith(editTodolistRequest, userList);


    }

    @Override
    public DeleteTodoListResponse deleteTodoTaskWith(DeleteTodolistRequest deleteTodolistRequest) {
        validateAuthentication();
        User author = findUserBy(deleteTodolistRequest.getAuthor());
        TodoList task = findUserTaskBy(deleteTodolistRequest.getListId(), author);
        return todoListService.deleteTodoTaskWith(deleteTodolistRequest, task);

    }

    @Override
    public List<TodoListResponse> viewAllTodoList(TodolistRequest todolistRequest) {
        validateAuthentication();
        User foundUser = findUserBy(todolistRequest.getUsername());
        List<TodoListResponse> todoListResponses = new ArrayList<>();
        for (TodoList todoList : foundUser.getTodoList()) {
            TodoListResponse todoListResponse = mapTodoListResponse(todoList);
            todoListResponses.add(todoListResponse);
        }
        return todoListResponses;
        
    }

    @Override
    public MarkTaskResponse markTaskStatus(MarkTaskRequest markTaskRequest) {
        validateAuthentication();
        User foundUser = findUserBy(markTaskRequest.getUsername());
        TodoList newTodoList = todoListService.markTaskStatusWith(markTaskRequest);
        TodoList todoList = findTodoListByTitle(markTaskRequest.getTitle(), foundUser);
        todoList.setStatus(TaskStatus.SUCCESS);
        TodoList updatedTodoList = todoListsRepository.save(todoList);

        MarkTaskResponse markTaskResponse = mapMarkTaskResponse(updatedTodoList);
        users.save(foundUser);
        return markTaskResponse;


    }



    private TodoList findTodoListByTitle(String title, User foundUser) {
        for (TodoList todoList : foundUser.getTodoList()) if (todoList.getTitle().equals(title)) return todoList;
        throw new TaskTitleNotFound("Task title not found");

    }

    private TodoList findUserTaskBy(String listId, User author) {
        for (TodoList todoList : author.getTodoList()) if (todoList.getId().equals(listId)) return todoList;
        throw new TaskNotFound("Task not found");

    }

    private boolean isPasswordIncorrect(User foundUser, String password) {
        return !foundUser.getPassword().equals(password);

    }

    private User findUserBy(String username) {
        if (users == null)
            throw new IllegalStateException("User cannot be null");

        User foundUser = users.findByUsername(username);
        if (foundUser == null)
            throw new UsernameNotFoundException(String.format("%s not found", username));

        return foundUser;

    }

    private void validate(String username) {
        if (username == null || username.trim().isEmpty())
            throw new RegistrationCantBeEmpty("Registration cannot be empty");

        boolean userExists = users.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s username already exists", username));

    }
}

