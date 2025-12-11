package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper mapper;

	// ユーザー一覧
	@Override
	public List<User> getUserList() {
		return mapper.selectAll();
	}

	// ユーザーID取得
	@Override
	public User getUserById(Integer id) {
		return mapper.selectById(id);
	}

	// ユーザー追加
	@Override
	public void addUser(User user) {
		mapper.insert(user);
	}

	// ユーザー編集
	@Override
	public void editUser(User user) {
		mapper.update(user);
	}

	// ユーザー論理削除
	@Override
	public void deleteUser(Integer id) {
		mapper.delete(id);
	}

	// 同じログインID(Email)が存在するか判定
	@Override
	public boolean existsByLoginId(String loginId) {
		return mapper.countByLoginId(loginId) > 0;
	}

	// DBのUserを1件取得、パスワードが一致するかチェック
	@Override
	public User findByLoginId(String loginId) {
		return mapper.findByLoginId(loginId);
	}

	// パスワード認証
	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) {
		User user = mapper.findByLoginId(loginId);

		if (user == null)
			return false;
		return BCrypt.checkpw(loginPass, user.getLoginPass());
	}

	// BCrypt.checkpw
	@Override
	public String getPasswordHashByLoginId(String loginId) {
		User user = mapper.findByLoginId(loginId);
		return (user != null) ? user.getLoginPass() : null;
	}

}
