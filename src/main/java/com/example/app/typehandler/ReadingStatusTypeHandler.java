package com.example.app.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.app.enums.ReadingStatus;

public class ReadingStatusTypeHandler extends BaseTypeHandler<ReadingStatus> {
	
	// データベース上の値とReadingStatus(enum型)を相互変換するクラス
	
	// ReadingStatus enumをSQLにセット
	@Override
  public void setNonNullParameter(PreparedStatement ps, int i, ReadingStatus parameter, JdbcType jdbcType) throws SQLException {
      ps.setString(i, parameter.getLabel());
  }

	// DBから値を取得、JavaのReadingStatusに変換
  @Override
  public ReadingStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
      String value = rs.getString(columnName);
      return value == null ? ReadingStatus.UNSPECIFIED : ReadingStatus.from(value);
  }

  // DBから値を取得、JavaのReadingStatusに変換(カラムインデックスを使う場合)
  @Override
  public ReadingStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
      String value = rs.getString(columnIndex);
      return value == null ? ReadingStatus.UNSPECIFIED : ReadingStatus.from(value);
  }

  // ストアドプロシージャや関数の呼び出し結果から値を取得するに使う
  // StoredProcedure → データベースの中に保存されている一連のSQL処理
  @Override
  public ReadingStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
      String value = cs.getString(columnIndex);
      return value == null ? ReadingStatus.UNSPECIFIED : ReadingStatus.from(value);
  }

}
