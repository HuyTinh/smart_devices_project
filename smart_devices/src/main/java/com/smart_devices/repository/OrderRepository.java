package com.smart_devices.repository;

import java.util.Date;
import java.util.List;

import com.smart_devices.dto.OrderDetailManageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart_devices.dto.DailyRevenueDto;
import com.smart_devices.dto.MonthlyRevenueDto;
import com.smart_devices.dto.OrderDetailDto;
import com.smart_devices.enums.OrderStatus;
import com.smart_devices.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT new com.smart_devices.dto.OrderDetailManageDto(" + "o.id, " + "CONCAT(u.firstName, ' ', u.lastName), "
			+ "pd.title, " + "od.quantity, " + "od.price, " + "o.orderDate, " + "o.shippingAddress," + "o.status)"
			+ "FROM Order o " + "JOIN o.user u " + "JOIN o.orderDetails od " + "JOIN od.productDetail pd ")
	Page<OrderDetailManageDto> findAllOrderDetails(Pageable pageable);

	@Query("SELECT new com.smart_devices.dto.OrderDetailManageDto(" +
		       "o.id, " +
		       "CONCAT(u.firstName, ' ', u.lastName), " +
		       "pd.title, " +
		       "od.quantity, " +
		       "od.price, " +
		       "o.orderDate, " +
		       "o.shippingAddress, " +
		       "o.status, " +
		       "(od.quantity * od.price) AS total) " +
		       "FROM Order o " +
		       "JOIN o.user u " +
		       "JOIN o.orderDetails od " +
		       "JOIN od.productDetail pd " +
		       "WHERE (:keyword IS NULL OR " +
		       "CONCAT(u.firstName, ' ', u.lastName) LIKE %:keyword% OR " +
		       "pd.title LIKE %:keyword% OR " +
		       "o.status LIKE %:keyword% OR " +
		       "o.shippingAddress LIKE %:keyword% OR " +
		       "CAST(o.id AS string) LIKE %:keyword%) " +
		       "AND (:fromDate IS NULL OR o.orderDate >= :fromDate) " +
		       "AND (:toDate IS NULL OR o.orderDate <= :toDate) " +
		       "AND (:status IS NULL OR o.status = :status) " +
		       "AND (:minTotal IS NULL OR (od.quantity * od.price) >= :minTotal) " +
		       "AND (:maxTotal IS NULL OR (od.quantity * od.price) <= :maxTotal)")
		Page<OrderDetailManageDto> searchOrders(
		    @Param("keyword") String keyword,
		    @Param("fromDate") Date fromDate,
		    @Param("toDate") Date toDate,
		    @Param("status") OrderStatus status,
		    @Param("minTotal") Double minTotal,
		    @Param("maxTotal") Double maxTotal,
		    Pageable pageable
		);

	@Query("SELECT new com.smart_devices.dto.MonthlyRevenueDto(" + "MONTH(o.orderDate), " + "YEAR(o.orderDate), "
			+ "SUM(od.price * od.quantity)) " + "FROM Order o " + "JOIN o.orderDetails od "
			+ "GROUP BY MONTH(o.orderDate), YEAR(o.orderDate) " + "ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
	List<MonthlyRevenueDto> findMonthlyRevenue();

	@Query("SELECT new com.smart_devices.dto.DailyRevenueDto(" + "DAY(o.orderDate), " + "SUM(od.price * od.quantity)) "
			+ "FROM Order o " + "JOIN o.orderDetails od "
			+ "WHERE MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year "
			+ "GROUP BY DAY(o.orderDate), MONTH(o.orderDate), YEAR(o.orderDate) " + "ORDER BY DAY(o.orderDate)")
	List<DailyRevenueDto> findDailyRevenue(@Param("month") int month, @Param("year") int year);
	
	 
	@Query(value = "Select SUM(orders.total_price) from orders where order_status != 'CANCELED'", nativeQuery = true)
	Double getTotal();
	
	@Query(value = "Select COUNT(*) from orders", nativeQuery = true)
	Double getTotalOrder();
	
	@Query(value = "Select COUNT(*) from orders where order_status = 'COMPLETED'" ,nativeQuery = true)
	Double getTotalOrderCompleted();
	@Query(value = "Select COUNT(*) from orders where order_status = 'CANCELED'" ,nativeQuery = true)
	Double getTotalOrderCanceled();
}
