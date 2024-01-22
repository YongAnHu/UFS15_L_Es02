package it.angelorizzoli.ufs15_l_es02.Controller;

import it.angelorizzoli.ufs15_l_es02.Dao.UserDao;
import it.angelorizzoli.ufs15_l_es02.Model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

	@Autowired
	private UserDao userRepository;

	@GetMapping("/login")
	public ModelAndView showLogin(@RequestParam("username") Optional<String> username, User user, HttpSession session) {
		ModelAndView model = new ModelAndView();

		if (session.getAttribute("loggedUser") != null) {
			model.setViewName("redirect:/home");
			return model;
		}

		model.setViewName("FormLogin");
		if (username != null)
			model.addObject("username", username);

		return model;
	}

	@PostMapping(value = "/login/signin")
	public String validateLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		User userLog = userRepository.login(username, password);

		if(userLog == null)
			return "redirect:/login";
		else {
			session.setAttribute("loggedUser", userLog);

			return "redirect:/home";
		}
	}

	@GetMapping("/register")
	public String showRegister(User user) {
		return "FormRegister";
	}

	@PostMapping(value = "/register")
	public String validateRegister(@Valid User user, BindingResult binding, Model model, HttpSession session) {
		if (binding.hasErrors()) return "FormRegister";

		userRepository.save(user);
		model.addAttribute("username", user.getUsername());

		return "redirect:/login";
	}

	@GetMapping(value = "/profile")
	public String showProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null) return "redirect:/login";

		model.addAttribute("user", user);
		return "Profile";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.setAttribute("loggedUser", null);
		return "redirect:/login";
	}
}
