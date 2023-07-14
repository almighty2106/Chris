package org.example.dao;

import org.example.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.example.domain.User;

public interface UserDao {
    @Insert("insert into tbl_user(name,age)values(#{name},#{age})")
    public void save(User user);
}
