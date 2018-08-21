package controllers;

import static spark.Spark.get;

import db.DBHelper;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeesController {

	public EmployeesController() {
		setupEndPoints();
	}

	private void setupEndPoints() {
		//get route for employees
		get("/employees", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("template", "templates/employees/index.vtl");
			List<Employee> employeeList =  DBHelper.getAll(Employee.class);
			model.put("employees", employeeList);
			return new ModelAndView(model, "templates/layout.vtl");

		}, new VelocityTemplateEngine()
		);

	}

	}
