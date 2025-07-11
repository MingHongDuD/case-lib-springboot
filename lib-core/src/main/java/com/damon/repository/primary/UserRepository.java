package com.damon.repository.primary;

import com.damon.entity.primary.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User 的 JPA 的数据库访问层
 *
 * @author damon du/minghongdud
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
