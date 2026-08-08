<%-- 
    Document   : reports
    Created on : 31-Oct-2025, 2:15:31 pm
    Author     : Chanchalkumar Puranlal Patwa
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ include file="sessionCheck.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String selectedReportType = request.getParameter("reportType");
    if (selectedReportType == null) {
        Object rtAttr = request.getAttribute("reportType");
        if (rtAttr != null) {
            selectedReportType = rtAttr.toString();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - DairyTrack</title>
    <link rel="stylesheet" href="style.css">

    <style>
        .report-filters {
            display: flex;
            align-items: flex-end;
            gap: 20px;
            margin-bottom: 25px;
        }

        .report-section {
            margin-top: 20px;
            display: none;
            animation: fadeIn 0.3s ease-in-out;
        }

        .report-section h3 {
            margin-bottom: 15px;
            color: #2a4b8d;
        }

        .report-section form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            align-items: flex-end;
            margin-bottom: 20px;
        }

        .input-group {
            flex: 1;
            min-width: 250px;
        }

        select, input[type="date"], input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 15px;
        }

        .generate-report-button {
            background-color: #2a4b8d;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 12px 20px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .generate-report-button:hover {
            background-color: #1f3d7a;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background-color: #ffffff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
            text-align: left;
        }

        th {
            background-color: #2a4b8d;
            color: white;
        }

        tr:hover {
            background-color: #f8f9ff;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
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
                <li><a href="milkEntry.jsp">Milk Collection</a></li>
                <li><a href="soldMilkEntry.jsp">Milk Distribution</a></li>
                <li><a href="reports.jsp" class="active">Reports</a></li>
            </ul>
        </nav>
        <div class="sidebar-footer">
            <a href="LogoutServlet" class="logout-button">Logout</a>
        </div>
    </aside>

    <main class="main-content">
        <header class="main-header">
            <div>
                <h2>Reports</h2>
                <p>Generate and analyze all your reports using mobile number filters</p>
            </div>
        </header>

        <!-- Report Type Selection -->
        <div class="content-card report-filters">
            <div class="input-group">
                <label for="reportType">Select Report Type</label>
                <select id="reportType" name="reportType" onchange="showSection(this.value)">
                    <option value="">-- Select Report Type --</option>
                    <option value="collection" <%= "collection".equals(selectedReportType) ? "selected" : "" %>>Milk Collection Report</option>
                    <option value="distribution" <%= "distribution".equals(selectedReportType) ? "selected" : "" %>>Milk Distribution Report</option>
<!--                    <option value="supplier" <%= "supplier".equals(selectedReportType) ? "selected" : "" %>>Supplier Report</option>
                    <option value="customer" <%= "customer".equals(selectedReportType) ? "selected" : "" %>>Customer Report</option>-->
                </select>
            </div>
        </div>

        <!-- ===== COLLECTION REPORT ===== -->
        <section id="collection" class="report-section content-card">
            <h3>Milk Collection Report</h3>
            <form action="CollectionReportServlet" method="post">
                <input type="hidden" name="reportType" value="collection">
                <div class="input-group">
                    <label for="collectionStartDate">Start Date</label>
                    <input type="date" id="collectionStartDate" name="startDate" required>
                </div>
                <div class="input-group">
                    <label for="collectionEndDate">End Date</label>
                    <input type="date" id="collectionEndDate" name="endDate" required>
                </div>
                <div class="input-group">
                    <label for="collectionShift">Select Shift</label>
                    <select id="collectionShift" name="shift">
                        <option value="">-- Select Shift --</option>
                        <option value="Morning">Morning</option>
                        <option value="Evening">Evening</option>
                    </select>
                </div>
                <div class="input-group">
                    <label for="collectionMobile">Supplier Mobile Number</label>
                    <input type="number" id="collectionMobile" name="mobile" placeholder="Enter supplier mobile number">
                </div>
                <button type="submit" class="generate-report-button">Generate Report</button>
            </form>
            <div id="collectionReportResult">
    <%
        List<Map<String, Object>> reportData = (List<Map<String, Object>>) request.getAttribute("reportData");
        if (reportData != null && !reportData.isEmpty()) {
    %>
        <h4>Milk Collection Report</h4>
        <table border="1">
            <thead>
                <tr>
                    <th>Farmer Name</th>
                    <th>Mobile</th>
                    <th>Entry Date</th>
                    <th>Shift</th>
                    <th>Quantity (L)</th>
                    <th>Fat (%)</th>
                    <th>SNF (%)</th>
                    <th>Rate/Liter</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> row : reportData) {
                %>
                    <tr>
                        <td><%= row.get("farmer_name") %></td>
                        <td><%= row.get("contact_number") %></td>
                        <td><%= row.get("entry_date") %></td>
                        <td><%= row.get("shift") %></td>
                        <td><%= row.get("quantity_liters") %></td>
                        <td><%= row.get("fat_percentage") %></td>
                        <td><%= row.get("snf_percentage") %></td>
                        <td><%= row.get("rate_per_liter") %></td>
                        <td><%= row.get("total_amount") %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <form action="DownloadCollectionExcelServlet" method="post" style="margin-top: 20px;">
            <input type="hidden" name="reportType" value="collection">
            <input type="hidden" name="startDate" value="<%= request.getAttribute("startDate") %>">
            <input type="hidden" name="endDate" value="<%= request.getAttribute("endDate") %>">
            <input type="hidden" name="shift" value="<%= request.getAttribute("shift") %>">
            <input type="hidden" name="mobile" value="<%= request.getAttribute("mobile") %>">
            <button type="submit" class="generate-report-button">Download Excel</button>
        </form>

    <%
        } else if (request.getAttribute("reportData") != null) {
    %>
        <p style="color:red;">No records found for the selected filters.</p>
    <%
        }
    %>
            </div>

        </section>

        <!-- ===== DISTRIBUTION REPORT ===== -->
        <section id="distribution" class="report-section content-card">
            <h3>Milk Distribution Report</h3>
            <form action="DistributionReportServlet" method="POST">
                <input type="hidden" name="reportType" value="distribution">
                <div class="input-group">
                    <label for="distributionStartDate">Start Date</label>
                    <input type="date" id="distributionStartDate" name="startDate" required>
                </div>
                <div class="input-group">
                    <label for="distributionEndDate">End Date</label>
                    <input type="date" id="distributionEndDate" name="endDate" required>
                </div>
<!--                <div class="input-group">
                    <label for="distributionMobile">Customer Mobile Number</label>
                    <input type="number" id="distributionMobile" name="mobile" placeholder="Enter customer mobile number" >
                </div>-->
                <button type="submit" class="generate-report-button">Generate Report</button>
            </form>
            <div id="distributionReportResult">
                
                    <%
        List<Map<String, Object>> reportData2 = (List<Map<String, Object>>) request.getAttribute("reportData2");
        if (reportData2 != null && !reportData2.isEmpty()) {
    %>
        <h4>Milk Collection Report</h4>
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Mobile</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Quantity (L)</th>
                    <th>Rate/Liter</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> row : reportData2) {
                %>
                    <tr>
                        <td><%= row.get("name") %></td>
                        <td><%= row.get("contact_number") %></td>
                        <td><%= row.get("date") %></td>
                        <td><%= row.get("shift") %></td>
                        <td><%= row.get("quantity_liters") %></td>
                        <td><%= row.get("rate_per_liter") %></td>
                        <td><%= row.get("total_amount") %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <form action="DownloadCollectionExcelServlet" method="post" style="margin-top: 20px;">
            <input type="hidden" name="reportType" value="collection">
            <input type="hidden" name="startDate" value="<%= request.getAttribute("startDate") %>">
            <input type="hidden" name="endDate" value="<%= request.getAttribute("endDate") %>">
            <input type="hidden" name="mobile" value="<%= request.getAttribute("mobile") %>">
            <button type="submit" class="generate-report-button">Download Excel</button>
        </form>

    <%
        } else if (request.getAttribute("reportData2") != null) {
    %>
        <p style="color:red;">No records found for the selected filters.</p>
    <%
        }
    %>
                
            </div>
        </section>

        <!-- ===== SUPPLIER REPORT ===== -->
<!--        <section id="supplier" class="report-section content-card">
            <h3>Supplier Report</h3>
            <form action="SupplierReportServlet" method="get">
                <input type="hidden" name="reportType" value="supplier">
                <div class="input-group">
                    <label for="supplierMobile">Supplier Mobile Number</label>
                    <input type="number" id="supplierMobile" name="mobile" placeholder="Enter supplier mobile number" required>
                </div>
                <button type="submit" class="generate-report-button">Generate Report</button>
            </form>
            <div id="supplierReportResult"></div>
        </section>-->

        <!-- ===== CUSTOMER REPORT ===== -->
<!--        <section id="customer" class="report-section content-card">
            <h3>Customer Info Report</h3>
            <form action="CustomerReportServlet" method="get">
                <input type="hidden" name="reportType" value="customer">
                <div class="input-group">
                    <label for="customerMobile">Customer Mobile Number</label>
                    <input type="number" id="customerMobile" name="mobile" placeholder="Enter customer mobile number" required>
                </div>
                <button type="submit" class="generate-report-button">Generate Report</button>
            </form>
            <div id="customerReportResult"></div>
        </section>-->
        
    </main>
</div>

<script>
function showSection(type) {
    var sections = document.getElementsByClassName("report-section");
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = "none";
    }
    if (type) {
        var section = document.getElementById(type);
        if (section) {
            section.style.display = "block";
        }
    }
}

var selectedReportType = '<%= selectedReportType == null ? "" : selectedReportType %>';
if (selectedReportType) {
    document.getElementById("reportType").value = selectedReportType;
    showSection(selectedReportType);
}
</script>

</body>
</html>
