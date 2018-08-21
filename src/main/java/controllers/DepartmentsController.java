package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentsController {

	public DepartmentsController() {
		setupEndPoints();
	}

	public static void setupEndPoints(){
		//get route for employees
		get("/departments", (req, res) -> {
				Map<String, Object> model = new HashMap<>();
				model.put("template", "templates/departments/index.vtl");
				List<Department> departments =  DBHelper.getAll(Department.class);
				model.put("departments", departments);
				return new ModelAndView(model, "templates/layout.vtl");

			}, new VelocityTemplateEngine()
		);
	}
}
