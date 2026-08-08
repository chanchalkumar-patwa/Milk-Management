<%-- 
    Document   : EditCustomer
    Created on : 20-Oct-2025, 4:39:04 pm
    Author     : chanc
--%>

<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Customer customer = (Customer) request.getAttribute("customer");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Customer</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #e0f7fa, #f1f8e9);
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
            align-items: center;
            justify-content: center;
        }

        .form-container {
            background: #ffffff;
            width: 400px;
            padding: 25px 30px;
            border-radius: 15px;
            box-shadow: 0px 5px 15px rgba(0,0,0,0.1);
            transition: 0.3s ease;
        }

        .form-container:hover {
            box-shadow: 0px 8px 20px rgba(0,0,0,0.2);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 20px;
        }

        label {
            font-weight: 500;
            color: #333;
            display: block;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s ease;
        }

        input[type="text"]:focus {
            outline: none;
            border-color: #2e7d32;
        }

        button {
            width: 100%;
            padding: 10px;
            background: #2e7d32;
            color: #fff;
            font-weight: bold;
            font-size: 15px;
            border: none;
            border-radius: 8px;
            margin-top: 20px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        button:hover {
            background: #43a047;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #388e3c;
            text-decoration: none;
            font-size: 14px;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Edit Customer</h2>
        <form action="CustomerServlet?action=update" method="post">
            <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">

            <label for="name">Name</label>
            <input type="text" name="name" id="name" value="<%= customer.getName() %>" required>

            <label for="contactNumber">Contact</label>
            <input type="text" name="contactNumber" id="contactNumber" value="<%= customer.getContactNumber() %>" required>

            <label for="email">Email</label>
            <input type="text" name="email" id="email" value="<%= customer.getEmail() %>" required>

            <label for="address">Address</label>
            <input type="text" name="address" id="address" value="<%= customer.getAddress() %>" required>

            <button type="submit">Update Customer</button>
        </form>

        <a href="CustomerServlet" class="back-link">← Back to Customer List</a>
    </div>
</body>
</html>
