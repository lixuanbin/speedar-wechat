<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Speedar Stack Trace Page</title>
</head>
<body>
<h3>Speedar Stack Traces</h3>
<pre>
<%
	for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
		Thread thread = (Thread) stackTrace.getKey();
		StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
		if(thread.equals(Thread.currentThread())) {
			continue;
		}
		out.print("\nThread: " + thread.getName() + "\n");
		for(StackTraceElement element : stack) {
			out.print("\t" + element + "\n");
		}
	}
%>
</pre>
</body>
</html>