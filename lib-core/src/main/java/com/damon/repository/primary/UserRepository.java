package com.damon.repository.primary;

import com.damon.entity.primary.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户数据库访问层
 *
 * @author damon du/minghongdud
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
