package org.solomon11.repository;

import org.solomon11.models.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoListsRepository extends MongoRepository<TodoList,String> {
}
