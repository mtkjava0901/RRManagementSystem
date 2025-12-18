package com.example.app.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.app.enums.BookSource;

public class BookSourceTypeHandler extends BaseTypeHandler<BookSource> {

	@Override
  public void setNonNullParameter(PreparedStatement ps, int i, BookSource parameter, JdbcType jdbcType)
          throws SQLException {
      ps.setString(i, parameter.name());
  }

  @Override
  public BookSource getNullableResult(ResultSet rs, String columnName) throws SQLException {
      String value = rs.getString(columnName);
      return value == null ? null : BookSource.valueOf(value);
  }

  @Override
  public BookSource getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
      String value = rs.getString(columnIndex);
      return value == null ? null : BookSource.valueOf(value);
  }

  @Override
  public BookSource getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
      String value = cs.getString(columnIndex);
      return value == null ? null : BookSource.valueOf(value);
  }
}
