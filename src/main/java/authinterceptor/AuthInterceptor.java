package authinterceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		Method method = handlerMethod.getMethod();
		
		Auth roleAnnotation = AnnotationUtils.findAnnotation(method, Auth.class);
		
		Auth.Role role = roleAnnotation != null ? roleAnnotation.role() : null;
		
		HttpSession session = request.getSession();
		
		boolean isLogined = session.getAttribute("isLogined") != null ? (Boolean) session.getAttribute("isLogined") : false;
		
		Auth.Role loginRole = session.getAttribute("role") != null ? (Auth.Role)session.getAttribute("role") : null;
		
		if(role!=null) {
			if(!isLogined) {
				response.sendRedirect("login");
				return false;
			}else {
				if(role != Auth.Role.LOGIN && role != loginRole) {
					response.sendRedirect("deny?url=\""+request.getRequestURI().toString()+"?"+request.getQueryString()+"\"&role="+role);
					return false;
				}
			}
			
		}
		
		return true;
	}

}
