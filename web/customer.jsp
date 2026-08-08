<%-- 
    Document   : customer
    Created on : 20-Oct-2025, 2:13:57 pm
    Author     : chanc
--%>
<%@ include file="sessionCheck.jsp" %>
<%@page import="java.util.List"%>
<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customers - DairyTrack</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .search-bar { margin-bottom: 20px; }
        .data-table { width: 100%; border-collapse: collapse; }
        .data-table th, .data-table td { border: 1px solid #ccc; padding: 8px; }
        .data-table th { background-color: #f4f4f4; }
        .action-icon { text-decoration: none; margin-right: 10px; }
    
        .message { padding: 10px; margin-bottom: 15px; border-radius: 3px; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
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
                <li><a href="dash.jsp" >Dashboard</a></li>
                <li><a href="FarmerServlet">Suppliers</a></li>
                <li><a href="CustomerServlet" class="active">Customers</a></li>
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
                <h2> Customers</h2>
                <p>Manage your customers</p>
            </div>
            <button class="add-new-button" onclick="document.getElementById('addCustomerModal').style.display='block'">+ Add New</button>
        </header>

        <div class="search-bar">
            <form method="get" action="CustomerServlet">
                <input type="text" name="search" placeholder="Search by name or phone..." value="${param.search}">
                <button type="submit">Search</button>
            </form>
        </div>

    <div class="content-card">
        <h3>All Customers</h3>
        <table class="data-table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Contact</th>
                <th>Address</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                if (customers != null && !customers.isEmpty()) {
                    for (Customer c : customers) {
            %>
                <tr>
                    <td><%= c.getName() %></td>
                    <td><%= c.getContactNumber() %></td>
                    <td><%= c.getAddress() %></td>
                    <td><%= c.getEmail() %></td>
                    <td>
                        <a href="CustomerServlet?action=edit&id=<%=c.getCustomerId()%>" class="action-icon">✎</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="5">No records found.</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    </main>
</div>

<!-- Add Customer Modal -->
<div id="addCustomerModal" class="modal-overlay" style="display:none;">
    <div class="modal-content">
        <div class="modal-header">
            <div>
                <h3>Add New Customer</h3>
                <p>Enter the details to register a new customer</p>
            </div>
            <button id="closeModalButton" class="close-button" onclick="document.getElementById('addCustomerModal').style.display='none'">&times;</button>
        </div>

        <%-- Display overall message --%>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
                String cssClass = message.toLowerCase().contains("error") ? "error" : "success";
        %>
            <div class="message <%= cssClass %>">
                <%= message %>
            </div>
        <% } %>

        <form class="modal-form" method="post" action="AddServlet">
            <div class="input-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" placeholder="Enter full name"
                       value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">
            </div>

            <div class="input-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="contact_number" placeholder="Enter phone number"
                       value="<%= request.getParameter("contact_number") != null ? request.getParameter("contact_number") : "" %>">
            </div>

            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter email address"
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
            </div>

            <div class="input-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" placeholder="Enter full address"
                       value="<%= request.getParameter("address") != null ? request.getParameter("address") : "" %>">
            </div>

            <div class="input-group">
                <label for="type">Type</label>
                <select id="type" name="type">
                    <option value="customer" selected>Customer</option>
                </select>
            </div>

            <button type="submit" class="add-new-button">Add</button>
        </form>
    </div>
</div>

<script>
    <% if (request.getAttribute("message") != null) { %>
        document.getElementById('addCustomerModal').style.display = 'block';
    <% } %>
</script>

</body>
</html>

