package Repository;

import Domain.*;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

// 주문 정보 저장소 구현 클래스
// MySQL 데이터베이스에 주문 정보를 저장하고 조회하는 기능을 구현
public class OrderInfoRespositoryImpl implements OrderInfoRepository {

    private static final String INSERT_ORDER_SQL = """
            INSERT INTO orders
            (id, table_number, order_type, order_state, payment_type, customer_name, order_time, total_price)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                table_number = VALUES(table_number),
                order_type = VALUES(order_type),
                order_state = VALUES(order_state),
                payment_type = VALUES(payment_type),
                customer_name = VALUES(customer_name),
                order_time = VALUES(order_time),
                total_price = VALUES(total_price)
            """;

    private static final String INSERT_ORDER_ITEM_SQL = """
            INSERT INTO order_items
            (order_id, order_item_id, menu_name, quantity, unit_price, option_summary, order_state)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                menu_name = VALUES(menu_name),
                quantity = VALUES(quantity),
                unit_price = VALUES(unit_price),
                option_summary = VALUES(option_summary),
                order_state = VALUES(order_state)
            """;

    private static final String DELETE_ORDER_SQL = "DELETE FROM orders WHERE id = ?";
    private static final String DELETE_ALL_ORDERS_SQL = "DELETE FROM orders";
    private static final String DELETE_ORDER_ITEMS_SQL = "DELETE FROM order_items WHERE order_id = ?";
    private static final String DELETE_ALL_ORDER_ITEMS_SQL = "DELETE FROM order_items";
    private static final String SELECT_ORDER_BY_ID_SQL = """
            SELECT id, table_number, order_type, order_state, payment_type, customer_name, order_time, total_price
            FROM orders
            WHERE id = ?
            """;
    private static final String SELECT_ORDER_BY_USER_SQL = """
            SELECT id, table_number, order_type, order_state, payment_type, customer_name, order_time, total_price
            FROM orders
            WHERE table_number = ? AND customer_name = ?
            ORDER BY order_time DESC
            LIMIT 1
            """;
    private static final String SELECT_ORDERS_BY_USER_SQL = """
            SELECT id, table_number, order_type, order_state, payment_type, customer_name, order_time, total_price
            FROM orders
            WHERE table_number = ? AND customer_name = ? AND order_state = 'ORDERED'
            ORDER BY order_time DESC
            """;
    private static final String SELECT_ALL_ORDERS_SQL = """
            SELECT id, table_number, order_type, order_state, payment_type, customer_name, order_time, total_price
            FROM orders
            ORDER BY order_time DESC
            """;
    private static final String SELECT_ORDER_ITEMS_SQL = """
            SELECT order_id, order_item_id, menu_name, quantity, unit_price, option_summary, order_state
            FROM order_items
            WHERE order_id = ?
            ORDER BY order_item_id
            """;
    private static final String SELECT_MAX_ORDER_ID_SQL = "SELECT COALESCE(MAX(id), 1999) AS max_id FROM orders";

    private final JdbcTemplate jdbcTemplate;

    public OrderInfoRespositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        syncOrderIdCounter();
    }

    @Override
    public void addOrder(Order order) {
        if (order == null) {
            return;
        }

        // ID 중복 방지: 저장 전에 DB의 최대 ID 확인
        Long maxId = jdbcTemplate.queryForObject(SELECT_MAX_ORDER_ID_SQL, null,
                rs -> rs.getLong("max_id"));
        
        // 현재 Order의 ID가 이미 사용 중이면 새로운 ID 생성
        long orderId = order.getId();
        if (maxId != null && orderId <= maxId) {
            // 새로운 ID 생성 및 카운터 업데이트
            orderId = maxId + 1;
            IdCounter.initOrderIdCounter(orderId + 1);
            System.out.println("Order ID updated from " + order.getId() + " to " + orderId);
        } else {
            // 저장 후 다음 ID로 카운터 증가
            IdCounter.initOrderIdCounter(orderId + 1);
        }

        // 테이블 번호가 0 이하이면 기본값 1로 설정
        int tableNumber = order.getTableNumber() > 0 ? order.getTableNumber() : 1;

        // final 변수로 저장 (lambda에서 사용하기 위해)
        final long savedOrderId = orderId;

        // 새로운 ID로 저장 (기존 ID와 다를 수 있음)
        jdbcTemplate.update(INSERT_ORDER_SQL, ps -> {
            ps.setLong(1, savedOrderId);
            ps.setInt(2, tableNumber);
            ps.setString(3, getEnumName(order.getOrderType()));
            ps.setString(4, getEnumName(order.getOrderState()));
            ps.setString(5, getEnumName(order.getPaymentType()));
            ps.setString(6, order.getCustomerName());
            ps.setTimestamp(7, order.getOrderTime() != null ? Timestamp.valueOf(order.getOrderTime()) : null);
            ps.setInt(8, order.getTotalPrice());
        });
        jdbcTemplate.update(DELETE_ORDER_ITEMS_SQL, ps -> ps.setLong(1, savedOrderId));
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                final int unitPrice = item.getPriceWithOptions();
                jdbcTemplate.update(INSERT_ORDER_ITEM_SQL, ps -> {
                    ps.setLong(1, savedOrderId);
                    ps.setLong(2, item.getId());
                    ps.setString(3, item.getMenuName());
                    ps.setInt(4, item.getQuantity());
                    ps.setInt(5, unitPrice);
                    ps.setString(6, item.getOptionsString());
                    ps.setString(7, getEnumName(item.getOrderState()));
                });
            }
        }
    }

    @Override
    public void removeOrder(Order order) {
        if (order == null) {
            return;
        }
        jdbcTemplate.update(DELETE_ORDER_ITEMS_SQL, ps -> ps.setLong(1, order.getId()));
        jdbcTemplate.update(DELETE_ORDER_SQL, ps -> ps.setLong(1, order.getId()));
    }

    @Override
    public void removeAllOrders() {
        jdbcTemplate.update(DELETE_ALL_ORDER_ITEMS_SQL, null);
        jdbcTemplate.update(DELETE_ALL_ORDERS_SQL, null);
    }

    @Override
    public Order getOrderById(Long id) {
        if (id == null) {
            return null;
        }

        Order order = jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID_SQL,
                ps -> ps.setLong(1, id),
                this::mapOrder);

        if (order != null) {
            attachOrderItems(order);
        }

        return order;
    }

    @Override
    public Order getOrderByUserInfo(int tableNumber, String name) {
        Order order = jdbcTemplate.queryForObject(SELECT_ORDER_BY_USER_SQL,
                ps -> {
                    ps.setInt(1, tableNumber);
                    ps.setString(2, name);
                },
                this::mapOrder);

        if (order != null) {
            attachOrderItems(order);
        }

        return order;
    }

    @Override
    public List<Order> getOrdersByUserInfo(int tableNumber, String name) {
        List<Order> orders = jdbcTemplate.query(SELECT_ORDERS_BY_USER_SQL,
                ps -> {
                    ps.setInt(1, tableNumber);
                    ps.setString(2, name);
                },
                this::mapOrder);

        for (Order order : orders) {
            attachOrderItems(order);
        }

        return orders;
    }

    @Override
    public List<Order> getAllOrder() {
        List<Order> orders = jdbcTemplate.query(SELECT_ALL_ORDERS_SQL, null, this::mapOrder);
        for (Order order : orders) {
            attachOrderItems(order);
        }
        return orders;
    }

    @Override
    public Long getMaxOrderId() {
        try {
            return jdbcTemplate.queryForObject(SELECT_MAX_ORDER_ID_SQL, null,
                    rs -> rs.getLong("max_id"));
        } catch (Exception e) {
            return null;
        }
    }

    private void attachOrderItems(Order order) {
        List<OrderItem> orderItems = jdbcTemplate.query(SELECT_ORDER_ITEMS_SQL,
                ps -> ps.setLong(1, order.getId()),
                this::mapOrderItem);

        order.setOrderItems(orderItems);
    }

    private Order mapOrder(java.sql.ResultSet rs) throws java.sql.SQLException {
        Order order = new Order(rs.getLong("id"));
        order.setTableNumber(rs.getInt("table_number"));
        order.setOrderType(parseOrderType(rs.getString("order_type")));
        order.setOrderState(parseOrderState(rs.getString("order_state")));
        order.setPaymentType(parsePaymentType(rs.getString("payment_type")));
        order.setCustomerName(rs.getString("customer_name"));
        Timestamp orderTime = rs.getTimestamp("order_time");
        if (orderTime != null) {
            order.setOrderTime(orderTime.toLocalDateTime());
        }
        order.setTotalPrice(rs.getInt("total_price"));
        return order;
    }

    private OrderItem mapOrderItem(java.sql.ResultSet rs) throws java.sql.SQLException {
        OrderItem orderItem = new OrderItem(
                rs.getLong("order_item_id"),
                rs.getString("menu_name"),
                rs.getInt("unit_price"),
                rs.getInt("quantity")
        );
        orderItem.setOrderState(parseOrderState(rs.getString("order_state")));
        orderItem.setCachedPriceWithOptions(rs.getInt("unit_price"));
        orderItem.setOptionSummary(rs.getString("option_summary"));
        return orderItem;
    }

    private OrderType parseOrderType(String value) {
        return value != null ? OrderType.valueOf(value) : null;
    }

    private OrderState parseOrderState(String value) {
        return value != null ? OrderState.valueOf(value) : null;
    }

    private PaymentType parsePaymentType(String value) {
        return value != null ? PaymentType.valueOf(value) : null;
    }

    private String getEnumName(Enum<?> value) {
        return value != null ? value.name() : null;
    }

    private void syncOrderIdCounter() {
        try {
            Long maxId = jdbcTemplate.queryForObject(SELECT_MAX_ORDER_ID_SQL, null,
                    rs -> rs.getLong("max_id"));
            if (maxId != null) {
                long nextId = maxId + 1;
                IdCounter.initOrderIdCounter(nextId);
                System.out.println("Order ID counter 동기화 : " + nextId);
            }
        } catch (Exception e) {
            System.err.println("동기화 실패 order ID counter : " + e.getMessage());
            // 테이블이 없거나 오류 발생 시 기본값 유지
        }
    }
}
