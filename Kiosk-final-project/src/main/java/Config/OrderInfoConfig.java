package Config;

import Repository.OrderInfoRespositoryImpl;
import kioskService.CheckOrderListService;
import kioskService.CheckOrderListServiceImpl;
import kioskService.PrintReceiptService;
import kioskService.PrintReceiptServiceImpl;

import javax.sql.DataSource;

// 주문 정보 관련 설정 클래스
// 주문 정보 Repository와 관련 서비스들을 생성 및 관리
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
