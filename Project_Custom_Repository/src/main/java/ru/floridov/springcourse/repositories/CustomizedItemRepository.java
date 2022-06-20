package ru.floridov.springcourse.repositories;

import java.util.List;

public interface CustomizedItemRepository <T>{

    List<T> getItemsStartsWithB();
}
