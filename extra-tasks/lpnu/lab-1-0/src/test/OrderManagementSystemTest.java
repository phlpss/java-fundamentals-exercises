package test;

import main.Order;
import main.Product;
import main.OrderManagementSystem;
import org.testng.annotations.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderManagementSystemTest {

    List<Order> orders = Arrays.asList(
            new Order("001", "John Doe", Arrays.asList(
                    new Product("Laptop", 1, 1000.0),
                    new Product("Mouse", 2, 25.0)), "card"),

            new Order("002", "John Doe", List.of(
                    new Product("Laptop", 2, 950.0)), "cash"),

            new Order("003", "Alice Boehm", Arrays.asList(
                    new Product("Keyboard", 1, 45.0),
                    new Product("Mouse", 2, 20.0),
                    new Product("Keyboard", 2, 45.0)), "cash"),

            new Order("004", "Victor Boehm", List.of(
                    new Product("SSD", 3, 70.0),
                    new Product("Keyboard", 1, 45.0)), "cash"),

            new Order("005", "Alice Boehm", List.of(
                    new Product("SSD", 3, 70.0),
                    new Product("Monitor", 2, 200.0),
                    new Product("Keyboard", 1, 45.0)), "cash"),

            new Order("006", "John Doe", Arrays.asList(
                    new Product("Laptop", 1, 1000.0),
                    new Product("Headphones", 1, 150.0)), "card")
    );

    @Test
    public void testOrdersByPaymentMethod() {
        List<Order> cardOrders = OrderManagementSystem.ordersByPaymentMethod(orders, "card");
        List<Order> cashOrders = OrderManagementSystem.ordersByPaymentMethod(orders, "cash");

        assertEquals(2, cardOrders.size());
        assertEquals(4, cashOrders.size());

        assertTrue(cardOrders.stream().allMatch(order -> order.getPaymentMethod().equals("card")));
        assertTrue(cashOrders.stream().allMatch(order -> order.getPaymentMethod().equals("cash")));
    }

    @Test
    public void testOrdersGroupedByCustomer() {
        Map<String, List<Order>> groupedByCustomer = OrderManagementSystem.ordersGroupedByCustomer(orders);

        assertEquals(3, groupedByCustomer.size());
        assertTrue(groupedByCustomer.containsKey("John Doe"));
        assertTrue(groupedByCustomer.containsKey("Alice Boehm"));
    }

    @Test
    public void testOrdersByCustomerName() {
        List<Order> aliceOrders = OrderManagementSystem.ordersByCustomerName(orders, "Alice Boehm");

        assertEquals(2, aliceOrders.size());
        assertTrue(aliceOrders.stream().allMatch(order -> order.getCustomerName().equals("Alice Boehm")));
    }

    @Test
    public void testTotalAmountOfEachProduct() {
        Map<String, Integer> totalProducts = OrderManagementSystem.totalAmountOfEachProduct(orders);

        assertEquals(4, totalProducts.get("Laptop"));
        assertEquals(5, totalProducts.get("Keyboard"));
        assertEquals(6, totalProducts.get("SSD"));
    }

    @Test
    public void testSortOrdersByTotalProductsValue() {
        List<Order> sortedOrders = OrderManagementSystem.sortOrdersByTotalProductsValue(orders);

        assertEquals("003", sortedOrders.getFirst().getOrderNumber());
        assertEquals("006", sortedOrders.getLast().getOrderNumber());
    }

    @Test
    public void testGetUniqueProducts() {
        Set<Product> uniqueProducts = OrderManagementSystem.getUniqueProducts(orders);

        assertEquals(6, uniqueProducts.size());
        assertTrue(uniqueProducts.stream().anyMatch(product -> product.getName().equals("SSD")));
        assertTrue(uniqueProducts.stream().anyMatch(product -> product.getName().equals("Headphones")));
    }

    @Test
    public void testGetMaxValueOrder() {
        Optional<Order> maxOrder = OrderManagementSystem.getMaxValueOrder(orders);

        assertTrue(maxOrder.isPresent());
        assertEquals("006", maxOrder.get().getOrderNumber());
    }
}