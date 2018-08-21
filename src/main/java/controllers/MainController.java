package controllers;

import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.get;

public class MainController {

	public static void main(String[] args) {
		Seeds.SeedData();
		setupEndPoints();
		ManagersController managersController = new ManagersController();
		EmployeesController employeesController = new EmployeesController();


	}

	public static void setupEndPoints(){
		get("/", (req, res) ->{
					Map<String, Object> model = new HashMap<>();
					model.put("template", "templates/index.vtl");
					return new ModelAndView(model, "templates/layout.vtl");
				}, new VelocityTemplateEngine()
		);
	}
}
