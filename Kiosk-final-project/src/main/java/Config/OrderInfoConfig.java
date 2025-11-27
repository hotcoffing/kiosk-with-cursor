package Config;

import Repository.*;
import kioskService.*;

import javax.sql.DataSource;

public class OrderInfoConfig {

    private final DataSource dataSource;
    private OrderInfoRespositoryImpl orderInfoRepository;

    public OrderInfoConfig() {
        this(new AppConfig().dataSource());
    }

    public OrderInfoConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public OrderInfoRespositoryImpl orderInfoRepository() {
        if (orderInfoRepository == null) {
            orderInfoRepository = new OrderInfoRespositoryImpl(dataSource);
        }
        return orderInfoRepository;
    }

    public PrintReceiptService printReceiptService() {
        return new PrintReceiptServiceImpl(
                orderInfoRepository()
        );
    }

    public CheckOrderListService checkOrderListService() {
        return new CheckOrderListServiceImpl(
                orderInfoRepository()
        );
    }
}
