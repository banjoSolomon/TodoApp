package org.solomon11.utilities;

import org.solomon11.dto.*;
import org.solomon11.models.TaskStatus;
import org.solomon11.models.TodoList;
import org.solomon11.models.User;
import org.solomon11.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

    public static RegisterUserResponse registerResponseMap(User user) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getId());
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setDateRegistered(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(user.getDateRegistered()));
        return registerUserResponse;

    }

    public static LoginUserResponse mapLoginResponse(User user) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setId(user.getId());
        loginUserResponse.setUsername(user.getUsername());
        return loginUserResponse;

    }
    public static LogoutUserResponse mapLogoutResponse(User user) {
       LogoutUserResponse loginUserResponse = new LogoutUserResponse();
        loginUserResponse.setUsername(user.getUsername());
        loginUserResponse.setId(user.getId());
        loginUserResponse.setMessage("Logout successful");
        return loginUserResponse;

    }

    public static TodoListResponse mapCreateTodoListResponseWith(TodoList todoList) {
        TodoListResponse todoListResponse = new TodoListResponse();
        todoListResponse.setListId(todoList.getId());
        todoListResponse.setTitle((todoList.getTitle()));
        todoListResponse.setStatus(TaskStatus.PENDING);
        todoListResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateCreated()));
        return todoListResponse;
    }

    public static TodoList checkMap(TodolistRequest todoList) {
        TodoList todoList1 = new TodoList();
        todoList1.setTitle(todoList.getTitle());
        todoList1.setStatus(TaskStatus.PENDING);
        return todoList1;
    }

    public static TodoList checkMapTask(MarkTaskRequest markTaskRequest) {
        TodoList todoList1 = new TodoList();
        todoList1.setTitle(markTaskRequest.getTitle());
        todoList1.setStatus(markTaskRequest.getStatus());
        return todoList1;
    }

    public static TodoList checkMapTaskPriority(MarkTaskPriorityRequest markTaskPriorityRequest) {
        TodoList todoList1 = new TodoList();
        todoList1.setTitle(markTaskPriorityRequest.getTitle());
        todoList1.setStatus(markTaskPriorityRequest.getStatus());
        return todoList1;
    }

    public static TodoList map(EditTodolistRequest editPostRequest, TodoList todoList) {
        todoList.setTitle(editPostRequest.getTitle());
        todoList.setStatus(editPostRequest.getStatus());
        return todoList;
    }

    public static EditTodolistUserResponse mapEditTodoResponseWith(TodoList todoList) {
        EditTodolistUserResponse todolistUserResponse = new EditTodolistUserResponse();
        todolistUserResponse.setListId(todoList.getId());
        todolistUserResponse.setTitle(todoList.getTitle());
        todolistUserResponse.setStatus(todoList.getStatus());
        todolistUserResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateUpdated()));
        return todolistUserResponse;
    }

    public static DeleteTodoListResponse mapDeleteTodolistResponseWith(TodoList todoList) {
        DeleteTodoListResponse deletePostResponse = new DeleteTodoListResponse();
        deletePostResponse.setListId(todoList.getId());
        return deletePostResponse;
    }

    public static TodoListResponse mapTodoListResponse(TodoList todoList) {
        TodoListResponse todoListResponse = new TodoListResponse();
        todoListResponse.setListId(todoList.getId());
        todoListResponse.setTitle(todoList.getTitle());
        todoListResponse.setStatus(todoList.getStatus());
        todoListResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateCreated()));
        return todoListResponse;


    }

    public static MarkTaskResponse mapMarkTaskResponse(TodoList todoList) {
        MarkTaskResponse markTaskResponse = new MarkTaskResponse();
        markTaskResponse.setListId(todoList.getId());
        markTaskResponse.setTitle(todoList.getTitle());
        markTaskResponse.setStatus(todoList.getStatus());
        markTaskResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateUpdated()));
        return markTaskResponse;
    }

    public static StartTaskResponse startTaskResponseMap(TodoList task){
        StartTaskResponse startTaskResponse = new StartTaskResponse();
        startTaskResponse.setListId(task.getId());
        startTaskResponse.setTitle(task.getTitle());
        startTaskResponse.setStatus(TaskStatus.IN_PROGRESS);
        startTaskResponse.setStartTime(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(task.getDateCreated()));
        startTaskResponse.setStartTime(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(task.getStartTime()));
        return startTaskResponse;
    }
}