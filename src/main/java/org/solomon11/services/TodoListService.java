package org.solomon11.services;

import org.solomon11.dto.*;
import org.solomon11.models.TodoList;
import org.solomon11.response.DeleteTodoListResponse;
import org.solomon11.response.EditTodolistUserResponse;

public interface TodoListService {
    TodoList createTodoListWith(TodolistRequest todolistRequest);

    EditTodolistUserResponse editTodoListWith(EditTodolistRequest editTodolistRequest, TodoList userList);

    DeleteTodoListResponse deleteTodoTaskWith(DeleteTodolistRequest deleteTodolistRequest, TodoList task);

    TodoList markTaskStatusWith(MarkTaskRequest markTaskRequest);

    TodoList startTaskWith(StartTaskRequest startTaskRequest);


}

