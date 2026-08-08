<%-- 
    Document   : dash
    Created on : 20-Sept-2025, 9:41:59 am
    Author     : chanc
--%>
<%@ include file="sessionCheck.jsp" %>
<%@page import="dao.CollectionDAO"%>
<%@page import="dao.FarmerDAO"%>
<%@page import="dao.CustomerDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - DairyTrack</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h1>DairyTrack</h1>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="#" class="active">Dashboard</a></li>
                    <li><a href="FarmerServlet">Suppliers</a></li>
                    <li><a href="CustomerServlet">Customers</a></li>
                    <li><a href="milkEntry.jsp">Milk Collection</a></li>
                    <li><a href="soldMilkEntry.jsp" >Milk Distribution</a></li>
                    <li><a href="reports.jsp">Reports</a></li>
                </ul>
            </nav>
            <div class="sidebar-footer">
                <a href="LogoutServlet" class="logout-button">Logout</a>
            </div>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <div>
                    <h2>Dashboard</h2>
                    <p>Welcome <%= user %>! Here's your dairy overview</p>
                </div>
                <div class="current-date">
                    <%= java.time.LocalDate.now() %>
                </div>
            </header>

            <div class="stats-grid">
                <div class="stat-card">
                    <h4>Total Customers</h4>
                    <span class="stat-value"><%
                                                 CustomerDAO cd = new CustomerDAO();
                                                 out.println(cd.getCustomerCount());
                                             %>
                    </span>
                </div>
                <div class="stat-card">
                    <h4>Total Suppliers</h4>
                    <span class="stat-value">
                        <%
                            FarmerDAO fd = new FarmerDAO();
                            out.println(fd.getFarmerCount());
                        %>
                    </span>
                </div>
                <div class="stat-card">
                    <h4>Today's Collection(liters)</h4>
                    <span class="stat-value">
                        <% 
                            CollectionDAO collection = new CollectionDAO();
                            out.println(collection.getTodaysCollection());
                            double fat = collection.getAVGFat();
                        %>
                    </span>
                </div>
                <div class="stat-card">
                    <h4>Average Fat %</h4>
                    <span class="stat-value"><%=fat%></span>
                    <!--<p class="stat-growth">Quality maintained</p>-->
                </div>
            </div>

            <div class="charts-grid">
                <div class="chart-container" style="width: 600px; margin: 40px auto;">
                    <h3>Daily Milk Collection</h3>
                    <canvas id="dailyChart"></canvas>
                </div>
                <div class="chart-container" style="width: 400px; margin: auto;">
                    <h3>Collection by Shift</h3>
                    <canvas id="shiftChart"></canvas>
                </div>
            </div>
        </main>
    </div>
</body>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
async function loadShiftChart() {
    const response = await fetch('api/shiftdata?nocache=' + new Date().getTime());
    const data = await response.json();

    const ctx = document.getElementById('shiftChart').getContext('2d');

    if (window.shiftChartInstance) {
        // Update existing chart
        window.shiftChartInstance.data.datasets[0].data = [data.morning, data.evening];
        window.shiftChartInstance.update();
    } else {
        // Create new chart
        window.shiftChartInstance = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Morning', 'Evening'],
                datasets: [{
                    data: [data.morning, data.evening],
                    backgroundColor: ['#36A2EB', '#FF6384']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' }
                }
            }
        });
    }
}

// Load chart initially
loadShiftChart();

// Auto-refresh every 5 seconds
setInterval(loadShiftChart, 5000);


async function loadDailyChart() {
    const response = await fetch('api/dailycollection?nocache=' + new Date().getTime());
    const data = await response.json();

    const ctx = document.getElementById('dailyChart').getContext('2d');

    if (window.dailyChartInstance) {
        window.dailyChartInstance.data.labels = data.dates;
        window.dailyChartInstance.data.datasets[0].data = data.quantities;
        window.dailyChartInstance.update();
    } else {
        window.dailyChartInstance = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.dates,
                datasets: [{
                    label: 'Total Quantity (Liters)',
                    data: data.quantities,
                    backgroundColor: '#36A2EB',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { display: true, position: 'bottom' },
                    title: {
                        display: true,
                        text: 'Milk Collection by Date'
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Liters Collected' }
                    },
                    x: {
                        title: { display: true, text: 'Date' }
                    }
                }
            }
        });
    }
}

// Load initially
loadDailyChart();

// Refresh every 5 seconds (optional)
setInterval(loadDailyChart, 5000);
</script>