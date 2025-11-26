package Config;

import Repository.OrderInfoRespositoryImpl;
import kioskService.CheckOrderListService;
import kioskService.CheckOrderListServiceImpl;
import kioskService.PrintReceiptService;
import kioskService.PrintReceiptServiceImpl;

import javax.sql.DataSource;

public class OrderInfoConfig {

    private final DataSource dataSource;

    public OrderInfoConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PrintReceiptService printReceiptService() {
        return new PrintReceiptServiceImpl(
                new OrderInfoRespositoryImpl(dataSource)
        );
    }

    public CheckOrderListService checkOrderListService() {
        return new CheckOrderListServiceImpl(
                new OrderInfoRespositoryImpl(dataSource)
        );
    }
}
