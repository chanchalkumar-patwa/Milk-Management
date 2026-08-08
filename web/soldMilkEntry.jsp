<%-- 
    Document   : soldMilkEntry
    Created on : 28-Oct-2025, 4:36:30 pm
    Author     : chanc
--%>
<%@ include file="sessionCheck.jsp" %>
<%@page import="model.Distribution"%>
<%@page import="dao.DistributionDAO"%>
<%@page import="model.Customer"%>
<%@page import="java.util.List"%>
<%@page import="dao.CustomerDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Distribution - DairyTrack</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .entries-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}
.entries-table th, .entries-table td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: center;
}
.entries-table th {
    background-color: #f4f4f4;
}
.entries-table tr:nth-child(even) {
    background-color: #fafafa;
}

    </style>
</head>
<body>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h1>DairyTrack</h1>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="dash.jsp">Dashboard</a></li>
                    <li><a href="FarmerServlet">Suppliers</a></li>
                    <li><a href="CustomerServlet">Customers</a></li>
                    <li><a href="milkEntry.jsp" >Milk Collection</a></li>
                    <li><a href="#" class="active">Milk Distribution</a></li>
                    <li><a href="reports.jsp">Reports</a></li>
                </ul>
            </nav>
            <div class="sidebar-footer">
                <a href="LogoutServlet"  class="logout-button">Logout</a>
            </div>
        </aside>

       <main class="main-content">
   <header class="main-header">
    <div>
        <h2>Sold Milk Entry</h2>
        <p>Record daily milk sales and revenue</p>
    </div>
</header>
           
           <%
    String msg = (String) request.getAttribute("message");
    String status = (String) request.getAttribute("status");
    if(msg != null){
        String color = "red"; // default error
        if("success".equals(status)){
            color = "green";
        }
%>
    <div style="padding:10px; margin-bottom:10px; border:1px solid #ccc; background-color:#f0f0f0; color:<%= color %>;">
        <%= msg %>
    </div>
<% 
    session.removeAttribute("message");
    session.removeAttribute("status");
    } %>
           
           
    <div class="milk-entry-layout">
        <div class="entry-form-container">
    <h3>New Sale Entry</h3>
    <form class="entry-form" method="post" action="MilkDistributionServlet">
        <div class="form-row">
            <div class="input-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" value="<%= java.time.LocalDate.now() %>">
            </div>
            <div class="input-group">
                <label>Shift</label>
                <div class="shift-toggle">
                    <input type="radio" id="morning" name="shift" value="Morning" checked>
                                    <label for="morning">☀️ Morning</label>
                                    <input type="radio" id="evening" name="shift" value="Evening">
                                    <label for="evening">🌙 Evening</label>
                </div>
            </div>
        </div>
        
        <div class="input-group">
            <label for="customer">Customer</label>
            <select name="customer_id" id="customer">
                <option value="">-- Select Customer --</option>
            <%
                CustomerDAO dao = new CustomerDAO();
                List<Customer> customers = dao.getAllCustomers();
                for(Customer c : customers){
            %>
                <option value="<%= c.getCustomerId() %>"><%= c.getName() %> - <%= c.getContactNumber() %></option>
            <% } %>
            </select>
        </div>
        
        <div class="form-row">
            <div class="input-group">
                <label for="cName">Name(if customer is not registered)</label>
                <input type="text" id="cName" name="cName">
            </div>
            <div class="input-group">
                <label for="avg-price">Number(if customer is not registered)</label>
                <input type="number" id="number" name="cNumber">
            </div>
        </div>
            
        <div class="form-row">
            <div class="input-group">
                <label for="quantity1">Total Sold (Liters)</label>
                <input type="number" id="quantity1" name="quantity" placeholder="0.0" oninput="calculateTotal()">
            </div>
            <div class="input-group">
                <label for="rate1">Average Price (₹/Liter)</label>
                <input type="number" id="rate1" name="rate" placeholder="50.00" oninput="calculateTotal()">
            </div>
            <div class="input-group">
                <label for="total_amount">Total Amount (₹)</label>
                <input type="number" id="total_amount1" name="total_amount" placeholder="0.0" step="0.1" readonly="true">
            </div>
        </div>
        <button type="submit" class="save-entry-button">Save Sale</button>
    </form>
</div>
        
        <div class="todays-entries-container">
            <h3>Today's Entries</h3>
            <%
        DistributionDAO DAO = new DistributionDAO();
        List<Distribution> todaysEntries = DAO.getTodaysEntries();
        if (todaysEntries == null || todaysEntries.isEmpty()) {
    %>
        <div class="no-entries">
            <p>No entries recorded for today</p>
        </div>
    <%
        } else {
    %>
        <table border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Number</th>
                    <th>Shift</th>
                    <th>Quantity (L)</th>
                    <th>Rate (₹/L)</th>
                    <th>Total (₹)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Distribution c : todaysEntries) {
                %>
                    <tr>
                        <td><%= c.getCustomerName()%></td>
                        <td><%= c.getContactNumber() %></td>
                        <td><%= c.getShift()%></td>
                        <td><%= c.getQuantity() %></td>
                        <td><%= c.getRate() %></td>
                        <td><%= c.getTotal()%></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>
        </div>
    </div>
            
<script>
    function calculateTotal() {
        let quantity = parseFloat(document.getElementById('quantity1').value) || 0;
        let rate = parseFloat(document.getElementById('rate1').value) || 0;
        let total = quantity * rate;
        document.getElementById('total_amount1').value = total.toFixed(2);
    }
</script>

</body>
</html>