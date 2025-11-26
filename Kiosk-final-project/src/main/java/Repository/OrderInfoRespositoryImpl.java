package Repository;

import Domain.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;
import java.util.*;

public class OrderInfoRespositoryImpl implements OrderInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderInfoRespositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void removeOrder(Order order) {

    }

    @Override
    public void removeAllOrders() {

    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public Order getOrderByUserInfo(int tableNumber, String name) {
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        return List.of();
    }
}
