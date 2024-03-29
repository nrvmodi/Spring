package com.nirav;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();

		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("user_login"));
		user.setPassword(rs.getString("user_pass"));
		user.setAge(rs.getInt("age"));
		return user;

	}

}
