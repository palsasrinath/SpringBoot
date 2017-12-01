package com.mankraft;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpringBootJdbcController {

	@Autowired
	SpringBootDAO springBootDAO;

	/*
	 * private TemplateEngine textTemplateEngine;
	 * 
	 * public SpringBootJdbcController(TemplateEngine textTemplateEngine) {
	 * this.textTemplateEngine = textTemplateEngine; }
	 */

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		SpringBootBean springBootBean = new SpringBootBean();
		model.addAttribute("springBootBean", springBootBean);

		return "index";
	}

	@PostMapping("/formSave")
	public String postForm(@ModelAttribute(value = "springBootBean") SpringBootBean springBootBean) {

		try {

			springBootDAO.saveAdmin(springBootBean);
		} catch (DataAccessException e) {
			e.printStackTrace();

			System.out.println("failed due to incorrect data");
		}

		return "redirect:/getDetails";
	}

	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public String getData(@ModelAttribute SpringBootBean springBootBean, HttpServletRequest request,
			HttpServletResponse response, BindingResult result) {

		try {

			List<SpringBootBean> userList = springBootDAO.getRecords(springBootBean);

			HttpSession session = request.getSession(true);
			session.setAttribute("userList", userList);
		} catch (DataAccessException e) {
			e.printStackTrace();

			System.out.println("failed due to incorrect data");
		}

		return "success";
	}

}
