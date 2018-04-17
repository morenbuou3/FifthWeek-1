package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);
        BigDecimal taxTotal = getTax(subTotal);
        BigDecimal grandTotal = getGrandTotal(subTotal, taxTotal);

        return getStdFormat(grandTotal);
    }

    private BigDecimal getGrandTotal(BigDecimal subTotal, BigDecimal taxTotal) {
        return subTotal.add(taxTotal);
    }

    private double getStdFormat(BigDecimal grandTotal) {
        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal getTax(BigDecimal subTotal) {
        return subTotal.multiply(tax);
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) return item;
        }
        return null;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            if (item == null) continue;
            subTotal = subTotal.add(getOrderItemTotal(product, item));
        }
        return subTotal;
    }

    private BigDecimal getOrderItemTotal(Product product, OrderItem orderItem) {
        BigDecimal price = product.getPrice();
        BigDecimal reduce = product.getPrice().multiply(product.getDiscountRate());
        BigDecimal itemTotal = price.subtract(reduce).multiply(new BigDecimal(orderItem.getCount()));

        return itemTotal;
    }
}
