package com.mankraft;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SpringBootDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int saveAdmin(SpringBootBean springBootBean) {
		String sql = "insert into spring_boot_form(name,url,address)  values('" + springBootBean.getName() + "','"
				+ springBootBean.getUrl() + "','" + springBootBean.getAddress() + "')";

		return jdbcTemplate.update(sql);

	}

	public List<SpringBootBean> getRecords(SpringBootBean springBootBean) {

		return jdbcTemplate.query("select name,url,address from spring_boot_form", new RowMapper<SpringBootBean>() {

			@Override
			public SpringBootBean mapRow(java.sql.ResultSet rs, int row) throws SQLException {
				SpringBootBean s = new SpringBootBean();
				s.setName(rs.getString("name"));
				s.setUrl(rs.getString("url"));
				s.setAddress(rs.getString("address"));
				return s;
			}

		});
	}

}
