package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Admin;

@Mapper
public interface AdminMapper {

	// 管理者マッピング

	// 管理者ID選択
	Admin selectByLoginId(String loginId);
	
	// LoginIdでAdmin取得
	Admin findByLoginId(@Param("loginId") String loginId);

}
