package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

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

		get("/departments/new", (req, res) ->{
				Map<String, Object> model = new HashMap<>();
				model.put("template", "templates/departments/new.vtl");
				return new ModelAndView(model, "templates/layout.vtl");
			}, new VelocityTemplateEngine()
		);

		post("/departments/new", (req, res) -> {
				String title = req.queryParams("title");
				Department department = new Department(title);
				DBHelper.save(department);
				res.redirect("/departments");
				return null;
			}
		);

		get("/departments/:id/edit", (req, res) ->{

			//create the model
			Map<String, Object> model = new HashMap<>();
			model.put("template", "templates/departments/edit.vtl");

			//get the manager to update from the db and pass into the model
			//so we can display property values in the form
			int departmentId = Integer.parseInt(req.params(":id"));
			Department department = DBHelper.find(departmentId, Department.class);
			model.put("department", department);

			return new ModelAndView(model, "templates/layout.vtl");

		}, new VelocityTemplateEngine()
		);

		post("/departments/:id", (req, res) -> {

			//get the department to update from the db
			int departmentId = Integer.parseInt(req.params(":id"));
			Department department = DBHelper.find(departmentId, Department.class);

			//get the new values of the department properties from req.params()
			String title = req.queryParams("title");

			//update department model with new values of properties
			department.setTitle(title);

			//update the department in the db and redirect to the departments index
			DBHelper.save(department);
			res.redirect("/departments");
			return null;
		}
		);


		post("/departments/:id/delete", (req, res) ->{
			int departmentId = Integer.parseInt(req.params(":id"));
			Department department = DBHelper.find(departmentId, Department.class);
			DBHelper.delete(department);
			res.redirect("/departments");
			return null;
		});
	}
}
