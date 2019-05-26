package authinterceptor;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpSession session) {
		
		if(session.getAttribute("isLogined") != null && (Boolean) session.getAttribute("isLogined") == true) {
			return "redirect:success";
		}
		
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@RequestParam("user") String userName, @RequestParam("password") String password, HttpSession session) {
		if("123456".equals(password)) {
			if("admin".equals(userName)) {
				session.setAttribute("isLogined", true);
				session.setAttribute("user", userName);
				session.setAttribute("role", Auth.Role.ADMIN);
				return "redirect:success";
			}
			if("emp".equals(userName)) {
				session.setAttribute("isLogined", true);
				session.setAttribute("user", userName);
				session.setAttribute("role", Auth.Role.EMPLOYEE);
				return "redirect:success";
			}
		}
		return "login";
	}
	
	@RequestMapping(value="/deny", method = RequestMethod.GET)
	public String deny() {
		return "deny";
	}
	
	@Auth(role=Auth.Role.LOGIN)
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String needLogin() {
		return "success";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("isLogined");
		session.removeAttribute("role");
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	
	@Auth(role=Auth.Role.EMPLOYEE)
	@RequestMapping(value="/emp", method=RequestMethod.GET)
	public String employee() {
		return "emp";
	}
}
