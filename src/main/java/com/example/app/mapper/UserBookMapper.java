package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.UserBook;

@Mapper
public interface UserBookMapper {
	
	UserBook selectById(@Param("id") Integer id);

  List<UserBook> selectByUserId(@Param("userId") Integer userId);

  int insert(UserBook userBook);

  int update(UserBook userBook);

  int delete(@Param("id") Integer id);

}
