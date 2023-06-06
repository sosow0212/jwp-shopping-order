package cart.dto.history;

import cart.domain.cart.DeliveryFee;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OrderHistory {

    private final Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date orderTime;
    private final List<OrderedProductHistory> products;
    private final DeliveryFee deliveryFee;
    private final List<OrderedCouponHistory> coupons;

    public OrderHistory(final Long id, final List<OrderedProductHistory> products, final int deliveryFee, final List<OrderedCouponHistory> coupons, final Date orderTime) {
        this.id = id;
        this.products = products;
        this.deliveryFee = DeliveryFee.from(deliveryFee);
        this.coupons = coupons;
        this.orderTime = orderTime;
    }

    public Long getId() {
        return id;
    }

    public List<OrderedProductHistory> getProducts() {
        return products;
    }

    public DeliveryFee getDeliveryFee() {
        return deliveryFee;
    }

    public List<OrderedCouponHistory> getCoupons() {
        return coupons;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHistory)) return false;
        OrderHistory that = (OrderHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
