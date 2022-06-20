package ru.floridov.springcourse.repositories;

import org.springframework.transaction.annotation.Transactional;
import ru.floridov.springcourse.models.Item;
import ru.floridov.springcourse.repositories.CustomizedItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedItemRepositoryImpl implements CustomizedItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public List getItemsStartsWithB() {
        return em.createQuery(" SELECT * from Item WHERE item_name LIKE B%", Item.class)
                .getResultList();
    }
}
