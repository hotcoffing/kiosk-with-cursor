package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

// JDBC 템플릿 구현 클래스
// 반복적인 커넥션/PreparedStatement/ResultSet 관리 캡슐화 클래스
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

    // SQL 업데이트 실행 메서드
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

    // SQL 쿼리 실행 및 결과 목록 반환 메서드
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

    // SQL 쿼리 실행 및 단일 객체 반환 메서드
    public <T> T queryForObject(String sql, PreparedStatementSetter setter, RowMapper<T> rowMapper) {
        List<T> results = query(sql, setter, rowMapper);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}

