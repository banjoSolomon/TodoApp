package org.solomon11.services;

import org.solomon11.dto.*;
import org.solomon11.models.TodoList;
import org.solomon11.response.*;

import java.util.List;

public interface UserService {

    RegisterUserResponse register(RegisterRequest registerRequest);

    LoginUserResponse login(LoginRequest loginRequest);

    TodoListResponse createTodolist(TodolistRequest todolistRequest);

    EditTodolistUserResponse editTodoListWith(EditTodolistRequest editTodolistRequest);

    DeleteTodoListResponse deleteTodoTaskWith(DeleteTodolistRequest deleteTodolistRequest);

    List<TodoListResponse> viewAllTodoList(TodolistRequest todolistRequest);

    MarkTaskResponse markTaskStatus(MarkTaskRequest markTaskRequest);

    LogoutUserResponse logout(LogoutRequest logoutRequest);

    StartTaskResponse startTask(StartTaskRequest startTaskRequest);

    List<TodoList> viewAllPendingTasks(ViewAllPendingTaskRequest viewAllPendingTaskRequest);

    void assignTask(AssignTaskRequest assignTaskRequest);
}


