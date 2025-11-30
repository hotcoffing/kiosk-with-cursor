package Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

// 데이터베이스 연결 풀 설정 클래스
// HikariCP를 사용한 MySQL 연결 관리
public class AppConfig {

    // 데이터베이스 연결 풀 생성 메서드
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