package it.angelorizzoli.ufs15_l_es02.Controller;

import it.angelorizzoli.ufs15_l_es02.Model.Book;
import it.angelorizzoli.ufs15_l_es02.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/home")
public class MainController {

	@GetMapping(value = "")
	public String showHome(Book book, Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");

		if (user != null) {
			model.addAttribute("user", user);
			return "HomePage";
		}

		return "redirect:/login";
	}
}
