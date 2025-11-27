package Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

// Spring 없이도 사용할 수 있는 간단한 JDBC 템플릿 구현체
// 반복적인 커넥션/PreparedStatement/ResultSet 관리를 캡슐화한다
public class JdbcTemplate {

    @FunctionalInterface
    public interface PreparedStatementSetter {
        void setValues(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int update(String sql, PreparedStatementSetter setter) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (setter != null) {
                setter.setValues(preparedStatement);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("SQL update 실행 중 오류가 발생했습니다.", e);
        }
    }

    public <T> List<T> query(String sql, PreparedStatementSetter setter, RowMapper<T> rowMapper) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (setter != null) {
                setter.setValues(preparedStatement);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(rowMapper.map(resultSet));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQL query 실행 중 오류가 발생했습니다.", e);
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> rowMapper) {
        List<T> results = query(sql, setter, rowMapper);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}

