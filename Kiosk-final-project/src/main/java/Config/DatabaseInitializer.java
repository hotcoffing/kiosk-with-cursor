package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// 데이터베이스 자동 초기화 클래스
// 애플리케이션 실행 시 데이터베이스와 테이블 자동 생성 클래스
public class DatabaseInitializer {
    
    private static final String DB_NAME = "kiosk_order_db";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rlatjdqls@2";
    
    // 데이터베이스 및 테이블 초기화 메서드
    public static void initializeDatabase() {
        try {
            createDatabaseIfNotExists();
            createTablesIfNotExists();
            System.out.println("데이터베이스 초기화 완료: " + DB_NAME);
        } catch (SQLException e) {
            System.err.println("데이터베이스 초기화 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 데이터베이스 생성 메서드
    private static void createDatabaseIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL + "?serverTimezone=UTC", USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String createDbSql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            stmt.executeUpdate(createDbSql);
            System.out.println("데이터베이스 확인/생성 완료: " + DB_NAME);
        }
    }
    
    // 주문 및 주문 항목 테이블 생성 메서드
    private static void createTablesIfNotExists() throws SQLException {
        String dbUrl = DB_URL + "/" + DB_NAME + "?serverTimezone=UTC";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String createOrdersTable = 
                "CREATE TABLE IF NOT EXISTS orders (" +
                "  id BIGINT PRIMARY KEY," +
                "  table_number INT NOT NULL," +
                "  order_type VARCHAR(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                "  order_state VARCHAR(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                "  payment_type VARCHAR(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                "  customer_name VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                "  order_time TIMESTAMP NULL DEFAULT NULL," +
                "  total_price INT NOT NULL DEFAULT 0," +
                "  INDEX idx_table_customer (table_number, customer_name)," +
                "  INDEX idx_order_time (order_time)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
            
            String createOrderItemsTable = 
                "CREATE TABLE IF NOT EXISTS order_items (" +
                "  order_id BIGINT NOT NULL," +
                "  order_item_id BIGINT NOT NULL," +
                "  menu_name VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL," +
                "  quantity INT NOT NULL DEFAULT 1," +
                "  unit_price INT NOT NULL DEFAULT 0," +
                "  option_summary TEXT COLLATE utf8mb4_unicode_ci," +
                "  order_state VARCHAR(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                "  PRIMARY KEY (order_id, order_item_id)," +
                "  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE," +
                "  INDEX idx_order_id (order_id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
            
            stmt.executeUpdate(createOrdersTable);
            System.out.println("테이블 확인/생성 완료: orders");
            
            stmt.executeUpdate(createOrderItemsTable);
            System.out.println("테이블 확인/생성 완료: order_items");
        }
    }
}

