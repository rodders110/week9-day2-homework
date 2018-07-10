package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


public class ManagersController {

    public ManagersController(){
        this.setupEndpoints();
    }

    private void setupEndpoints(){

        get("/managers", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers =DBHelper.getAll(Manager.class);
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/managers/index.vtl");
            model.put("departments", departments);
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/managers/create.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        post("/managers", (req, res) -> {
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int  departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            int budget = Integer.parseInt(req.queryParams("budget"));
            Manager newManager = new Manager(firstName, lastName, salary, department , budget);
            DBHelper.save(newManager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        post("managers/:id/update", (req, res) ->{
            int id = Integer.parseInt(req.params(":id"));
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            double budget = Double.parseDouble(req.queryParams("budget"));
            Manager manager = DBHelper.find(id, Manager.class);
            manager.setFirstName(firstName);
            manager.setLastName(lastName);
            manager.setSalary(salary);
            manager.setDepartment(department);
            manager.setBudget(budget);
            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        post("managers/:id/delete", (req, res) ->{
            int id = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(id, Manager.class);
            DBHelper.delete(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());
    }
}
