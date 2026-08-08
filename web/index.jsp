<%-- 
    Document   : login
    Created on : 22-Sept-2025, 10:19:05 am
    Author     : chanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - DairyTrack</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <div class="login-container">
        <div class="login-header">
            <div class="logo">🥛</div> 
            <h1>DairyTrack</h1>
            <p>Dairy Management System</p>
        </div>

        <div class="login-box">
            <h2>Admin Login</h2>
            <p>Enter your credentials to access the system</p>

            <form method="post" action="LoginServlet">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="uName" placeholder="Enter username">
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="pass" placeholder="Enter password">
                </div>
                <input class="login-button" type="submit" value="Login">
            </form>
            
            <div class="demo-creds">
                <p>Demo Credentials:</p>
                <p>Username: <strong>admin</strong> | Password: <strong>dairy2024</strong></p>
            </div>
            <p style="color:red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>
        </div>
        
    </div>

</body>
</html>
