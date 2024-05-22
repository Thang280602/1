package com.shop.shoes.controller;


import com.shop.shoes.model.Order;
import com.shop.shoes.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author thang
 */
@RestController
@RequestMapping("/admin")
public class ChartController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/chart")
    public ResponseEntity<ChartData> getChartData() {
        List<Order> orders = orderService.getAll();

        Map<String, Double> monthlyTotalPrice = orders.stream()
                .collect(Collectors.groupingBy(order -> getMonthFromDate(order.getCreateAt()),
                        Collectors.summingDouble(Order::getTotalPrice)));

        Map<String, Long> monthlyTotalOrders = orders.stream()
                .collect(Collectors.groupingBy(order -> getMonthFromDate(order.getCreateAt()),
                        Collectors.counting()));

        // Tạo mảng labels và mảng data tương ứng
        String[] labels = { "January", "February", "March", "April", "May", "June", "July","August","September","October","November","December" };
        double[] totalPriceData = new double[labels.length];
        long[] totalOrdersData = new long[labels.length];
        for (int i = 0; i < labels.length; i++) {
            String month = labels[i];
            totalPriceData[i] = monthlyTotalPrice.getOrDefault(month, 0.0);
            totalOrdersData[i] = monthlyTotalOrders.getOrDefault(month, 0L);
        }

        ChartData chartData = new ChartData(labels, totalPriceData, totalOrdersData);
        System.out.println(chartData);
        return ResponseEntity.ok(chartData);
    }
    private String getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cộng thêm 1 để bắt đầu từ 1
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Invalid month";
        }
    }
    // Định nghĩa một lớp để chứa dữ liệu của biểu đồ
    private static class ChartData {
        private String[] labels;
        private double[] totalPriceData;
        private long[] totalOrdersData;

        public ChartData(String[] labels, double[] totalPriceData, long[] totalOrdersData) {
            this.labels = labels;
            this.totalPriceData = totalPriceData;
            this.totalOrdersData = totalOrdersData;
        }

        public String[] getLabels() {
            return labels;
        }

        public void setLabels(String[] labels) {
            this.labels = labels;
        }

        public double[] getTotalPriceData() {
            return totalPriceData;
        }

        public void setTotalPriceData(double[] totalPriceData) {
            this.totalPriceData = totalPriceData;
        }

        public long[] getTotalOrdersData() {
            return totalOrdersData;
        }

        public void setTotalOrdersData(long[] totalOrdersData) {
            this.totalOrdersData = totalOrdersData;
        }
    }
}
