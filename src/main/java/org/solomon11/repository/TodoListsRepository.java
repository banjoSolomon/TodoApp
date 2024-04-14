package org.solomon11.repository;

import org.solomon11.models.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoLists extends MongoRepository<TodoList,String> {
}
