package cart.service.order;

import cart.domain.member.Member;
import cart.dto.history.OrderHistory;
import cart.dto.history.OrderedCouponHistory;
import cart.dto.history.OrderedProductHistory;
import cart.dto.order.OrderResponse;
import cart.dto.order.OrdersResponse;
import cart.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static cart.fixture.MemberFixture.createMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @DisplayName("주문 내역들을 보내준다.")
    @Test
    void find_orders() {
        // given
        Member member = createMember();
        OrderHistory orderHistory = new OrderHistory(
                1L,
                List.of(new OrderedProductHistory(1L, "치킨", "img", 10, 10000)),
                3000,
                List.of(new OrderedCouponHistory(1L, "쿠폰")),
                Date.from(Instant.now())
        );
        List<OrderHistory> expected = List.of(orderHistory);

        given(orderRepository.findAllByMemberId(member.getId())).willReturn(expected);

        // when
        OrdersResponse result = orderService.findOrders(member);

        // then
        assertAll(
                () -> assertThat(result.getOrders().size()).isEqualTo(1),
                () -> assertThat(result.getOrders().get(0).getOrderId()).isEqualTo(1)
        );
    }

    @DisplayName("주문 내역을 보여준다.")
    @Test
    void find_order() {
        // given
        Member member = createMember();
        OrderHistory orderHistory = new OrderHistory(
                1L,
                List.of(new OrderedProductHistory(1L, "치킨", "img", 10, 10000)),
                3000,
                List.of(new OrderedCouponHistory(1L, "쿠폰")),
                Date.from(Instant.now())
        );
        Long orderId = 1L;

        given(orderRepository.isMemberOrder(member, orderId)).willReturn(true);
        given(orderRepository.findOrderHistory(orderId)).willReturn(orderHistory);

        // when
        OrderResponse order = orderService.findOrder(member, orderId);

        // then
        assertAll(
                () -> assertThat(order.getOrderId()).isEqualTo(1),
                () -> assertThat(order.getProducts().get(0).getProductName()).isEqualTo("치킨")
        );
    }
}
