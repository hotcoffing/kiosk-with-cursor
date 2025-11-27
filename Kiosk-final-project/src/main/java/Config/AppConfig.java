package Config;

// MainApp.java 등 설정 파일
import com.zaxxer.hikari.*;
import javax.sql.*;

public class AppConfig {

    public DataSource dataSource() {
        // HikariCP를 사용하여 DataSource 설정
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/kiosk_order_db?serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("rlatjdqls@2");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return new HikariDataSource(config);
    }
}