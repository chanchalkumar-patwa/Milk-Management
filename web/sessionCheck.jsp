<%-- 
    Document   : sessionCheck
    Created on : 31-Oct-2025, 1:52:01 pm
    Author     : chanc
--%>

<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

HttpSession session1 = request.getSession(false);
if (session1 == null || session1.getAttribute("user") == null) {
    response.sendRedirect("index.jsp");
    return;
}
String user = (String) session.getAttribute("user");
%>
