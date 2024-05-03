package org.solomon11.services;

import org.solomon11.dto.*;
import org.solomon11.models.TaskStatus;
import org.solomon11.models.TodoList;
import org.solomon11.repository.TodoListsRepository;
import org.solomon11.response.DeleteTodoListResponse;
import org.solomon11.response.EditTodolistUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.solomon11.utilities.Mapper.*;

@Service
public class TodoListServiceImpl implements TodoListService{
    @Autowired
    private TodoListsRepository todoListsRepository;
    private TodoList todoList;

    @Override
    public TodoList createTodoListWith(TodolistRequest todolistRequest) {
       TodoList todoList = checkMap(todolistRequest);
        return todoListsRepository.save(todoList);

    }

    @Override
    public EditTodolistUserResponse editTodoListWith(EditTodolistRequest editTodolistRequest, TodoList userList) {
        TodoList editedTodolist = map(editTodolistRequest, userList);
        todoListsRepository.save(editedTodolist);
        return mapEditTodoResponseWith(editedTodolist);


    }

    @Override
    public DeleteTodoListResponse deleteTodoTaskWith(DeleteTodolistRequest deleteTodolistRequest, TodoList task) {
        todoListsRepository.delete(task);
        return mapDeleteTodolistResponseWith(task);

    }

    @Override
    public TodoList markTaskStatusWith(MarkTaskRequest markTaskRequest) {
        TodoList todoList = checkMapTask(markTaskRequest);
        return todoListsRepository.save(todoList);

    }


    @Override
    public TodoList startTaskWith(StartTaskRequest startTaskRequest) {
        TodoList newTask = new TodoList();
        newTask.setTitle(startTaskRequest.getTitle());
        newTask.setStatus(TaskStatus.PENDING);
        newTask.setAuthor(startTaskRequest.getUsername());
        return todoListsRepository.save(newTask);

    }

    @Override
    public TodoList assignTask(AssignTaskRequest assignTaskRequest) {
        TodoList newTask = new TodoList();
        newTask.setTitle(assignTaskRequest.getTitle());
        newTask.setStatus(TaskStatus.PENDING);
        newTask.setAuthor(assignTaskRequest.getAuthor());
        return todoListsRepository.save(newTask);


    }


}
