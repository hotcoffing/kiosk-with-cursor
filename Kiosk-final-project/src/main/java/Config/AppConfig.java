package Config;

// MainApp.java 등 설정 파일
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class AppConfig {

    public DataSource dataSource() {
        // HikariCP를 사용하여 DataSource 설정
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/db_name?serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("rlatjdqls@2");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return new HikariDataSource(config);
    }

    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}