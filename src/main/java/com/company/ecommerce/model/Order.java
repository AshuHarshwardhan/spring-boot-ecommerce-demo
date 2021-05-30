package com.company.ecommerce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.company.ecommerce.dto.order.PlaceOrderDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "total_price")
	private Double totalPrice;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> orderItems;

	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Order() {
	}

	public Order(PlaceOrderDto orderDto, User user) {
		this.user = user;
		this.createdDate = new Date();
		this.totalPrice = orderDto.getTotalPrice();
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
