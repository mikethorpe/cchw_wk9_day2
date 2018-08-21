package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

	public ManagersController() {
		setupEndPoints();
	}

	private void setupEndPoints(){

		get("/managers", (req, res) ->{
			Map<String, Object> model = new HashMap<>();
			model.put("template", "templates/managers/index.vtl");
			List<Manager> managers = DBHelper.getAll(Manager.class);
			model.put("managers", managers);
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine()
		);

		get("/managers/new", (req, res) ->{
			List<Department> departments = DBHelper.getAll(Department.class);
			Map<String, Object> model = new HashMap<>();
			model.put("template", "templates/managers/new.vtl");
			model.put("departments", departments);
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine()
		);

		post("/managers/new", (req, res) -> {
			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));
			int budget = Integer.parseInt(req.queryParams("budget"));
			int departmentId = Integer.parseInt(req.queryParams("departmentId"));
			Department department =  DBHelper.find(departmentId, Department.class);
			Manager manager = new Manager(firstName,lastName, salary, department, budget);
			DBHelper.save(manager);
			res.redirect("/managers");
			return null;
		}
		);

		get("/managers/:id/edit", (req, res) ->{

			//create the model
			Map<String, Object> model = new HashMap<>();
			model.put("template", "templates/managers/edit.vtl");

			//get the manager to update from the db and pass into the model
			//so we can display property values in the form
			int managerId = Integer.parseInt(req.params(":id"));
			Manager manager = DBHelper.find(managerId, Manager.class);
			model.put("manager", manager);

			//get the list of departments from the db and add to the model
			//so we can access them in the form
			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);

			return new ModelAndView(model, "templates/layout.vtl");

		}, new VelocityTemplateEngine()
		);


		post("/managers/:id", (req, res) -> {

			//get the manager to update from the db
			int managerId = Integer.parseInt(req.params(":id"));
			Manager manager = DBHelper.find(managerId, Manager.class);

			//get the new values of the manager properties from req.params()
			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));
			int budget = Integer.parseInt(req.queryParams("budget"));
			int departmentId = Integer.parseInt(req.queryParams("departmentId"));
			Department department =  DBHelper.find(departmentId, Department.class);

			//update manager model with new values of properties
			manager.setFirstName(firstName);
			manager.setLastName(lastName);
			manager.setSalary(salary);
			manager.setBudget(budget);
			manager.setDepartment(department);

			//update the manager in the db and redirect to the managers page
			DBHelper.save(manager);
			res.redirect("/managers");
			return null;
			}
		);

		post("/managers/:id/delete", (req, res) ->{
			int managerId = Integer.parseInt(req.params(":id"));
			Manager manager = DBHelper.find(managerId, Manager.class);
			DBHelper.delete(manager);
			res.redirect("/managers");
			return null;
		});
	}





}
