<%-- 
    Document   : milkEntry
    Created on : 09-Oct-2025, 8:40:54 pm
    Author     : chanc
--%>
<%@ include file="sessionCheck.jsp" %>
<%@ page import="dao.CollectionDAO" %>
<%@ page import="model.Collection" %>
<%@page import="java.util.List"%>
<%@page import="model.Farmer"%>
<%@page import="dao.FarmerDAO"%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Collection - DairyTrack</title>
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
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h1>DairyTrack</h1>
            </div>

            <nav class="sidebar-nav">
                <ul>
                    <li><a href="dash.jsp">Dashboard</a></li>
                    <li><a href="FarmerServlet">Suppliers</a></li>
                    <li><a href="CustomerServlet">Customers</a></li>
                    <li><a href="#" class="active">Milk Collection</a></li>
                    <li><a href="soldMilkEntry.jsp" >Milk Distribution</a></li>
                    <li><a href="reports.jsp">Reports</a></li>
                </ul>
            </nav>

            <div class="sidebar-footer">
                <a href="LogoutServlet" class="logout-button">Logout</a>
            </div>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <header class="main-header">
                <div>
                    <h2>Milk Collection Entry</h2>
                    <p>Record daily milk collection from suppliers</p>
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
                <!-- Entry Form -->
                <div class="entry-form-container">
                    <h3>New Milk Entry</h3>

                    <form action="MilkCollectionServlet" method="post" class="entry-form">
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
                            <label for="supplier">Supplier</label>
                            <select name="farmer_id" id="farmer" required>
            <option value="">-- Select Farmer --</option>
            <%
                FarmerDAO dao = new FarmerDAO();
                List<Farmer> farmers = dao.getAllFarmers();
                for(Farmer f : farmers){
            %>
                <option value="<%= f.getFarmerId() %>"><%= f.getName() %> - <%= f.getContactNumber() %></option>
            <% } %>
        </select>
                        </div>

                        <div class="form-row">
                            <div class="input-group">
                                <label for="quantity">Quantity (Liters)</label>
                                <input type="number" id="quantity" name="quantity" placeholder="0.0" step="0.1" oninput="calculateTotal()">
                            </div>

                            <div class="input-group">
                                <label for="fat">Fat %</label>
                                <input type="number" id="fat" name="fat" placeholder="3.5" step="0.1">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="input-group">
                                <label for="snf">SNF %</label>
                                <input type="number" id="snf" name="snf" placeholder="8.5" step="0.1">
                            </div>
                            
                            <div class="input-group">
                                <label for="rate">Rate (₹/Liter)</label>
                                <input type="number" id="rate" name="rate" placeholder="35" step="0.1" oninput="calculateTotal()">
                            </div>

                            <div class="input-group">
                                <label for="total_amount">Total Amount (₹)</label>
                                <input type="number" id="total_amount" name="total_amount" placeholder="0.0" step="0.1">
                            </div>
                        </div>

                        <button type="submit" class="save-entry-button">Save Entry</button>
                    </form>
                </div>

                <!-- Today's Entries -->
                <div class="todays-entries-container">
                    <h3>Today's Entries</h3>

    <%
        CollectionDAO collectionDAO = new CollectionDAO();
        List<Collection> todaysEntries = collectionDAO.getTodaysEntries();
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
                    <th>ID</th>
                    <th>Farmer</th>
                    <th>Shift</th>
                    <th>Quantity (L)</th>
                    <th>Fat %</th>
                    <th>SNF %</th>
                    <th>Rate (₹/L)</th>
                    <th>Total (₹)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Collection c : todaysEntries) {
                %>
                    <tr>
                        <td><%= c.getEntryId() %></td>
                        <td><%= c.getFarmerName() %></td>
                        <td><%= c.getShift()%></td>
                        <td><%= c.getQuantityLiters() %></td>
                        <td><%= c.getFatPercentage() %></td>
                        <td><%= c.getSnfPercentage() %></td>
                        <td><%= c.getRatePerLiter() %></td>
                        <td><%= c.getTotalAmount() %></td>
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
        </main>
    </div>
<script>
    function calculateTotal() {
        let quantity = parseFloat(document.getElementById('quantity').value) || 0;
        let rate = parseFloat(document.getElementById('rate').value) || 0;
        let total = quantity * rate;
        document.getElementById('total_amount').value = total.toFixed(2);
    }
</script>
</body>
</html>
