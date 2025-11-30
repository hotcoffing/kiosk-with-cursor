package Config;

import Repository.OrderInfoRespositoryImpl;
import kioskService.CheckOrderListService;
import kioskService.CheckOrderListServiceImpl;
import kioskService.PrintReceiptService;
import kioskService.PrintReceiptServiceImpl;

import javax.sql.DataSource;

// 주문 정보 관련 설정 클래스
// 주문 정보 Repository와 관련 서비스 생성 및 관리 클래스
public class OrderInfoConfig {

    private final DataSource dataSource;
    private OrderInfoRespositoryImpl orderInfoRepository;

    // 기본 생성자
    public OrderInfoConfig() {
        this(new AppConfig().dataSource());
    }

    // 데이터소스 지정 생성자
    public OrderInfoConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 주문 정보 저장소 반환 메서드
    public OrderInfoRespositoryImpl orderInfoRepository() {
        if (orderInfoRepository == null) {
            orderInfoRepository = new OrderInfoRespositoryImpl(dataSource);
        }
        return orderInfoRepository;
    }

    // 영수증 출력 서비스 반환 메서드
    public PrintReceiptService printReceiptService() {
        return new PrintReceiptServiceImpl(
                orderInfoRepository()
        );
    }

    // 주문 내역 조회 서비스 반환 메서드
    public CheckOrderListService checkOrderListService() {
        return new CheckOrderListServiceImpl(
                orderInfoRepository()
        );
    }
}
