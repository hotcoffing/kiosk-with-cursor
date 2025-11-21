package Repository;

import Domain.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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
