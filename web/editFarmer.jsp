<%-- 
    Document   : editFarmer
    Created on : 10-Oct-2025, 9:09:01 am
    Author     : chanc
--%>
<%@page import="model.Farmer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Farmer farmer = (Farmer) request.getAttribute("farmer");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Farmer</title>
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
        <h2>Edit Farmer</h2>
        <form action="UpdateFarmerServlet" method="post">
            <input type="hidden" name="farmerId" value="<%= farmer.getFarmerId() %>">

            <label for="name">Name</label>
            <input type="text" name="name" id="name" value="<%= farmer.getName() %>" required>

            <label for="contactNumber">Contact</label>
            <input type="text" name="contactNumber" id="contactNumber" value="<%= farmer.getContactNumber() %>" required>

            <label for="email">Email</label>
            <input type="text" name="email" id="email" value="<%= farmer.getEmail() %>" required>

            <label for="address">Address</label>
            <input type="text" name="address" id="address" value="<%= farmer.getAddress() %>" required>

            <button type="submit">Update Farmer</button>
        </form>

        <a href="FarmerServlet" class="back-link">← Back to Farmer List</a>
    </div>
</body>
</html>
