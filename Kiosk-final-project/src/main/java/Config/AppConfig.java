package Config;

import com.zaxxer.hikari.*;
import javax.sql.*;

// 애플리케이션 데이터베이스 연결 설정 클래스
// HikariCP를 사용하여 MySQL 데이터베이스 연결 풀을 생성
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