package controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


public class EmployeesController {

    public static void main(String[] args) {
        ManagersController managersController = new ManagersController();
        EngineersControllers engineersControllers = new EngineersControllers();
        DepartmentController departmentController = new DepartmentController();

        Seeds.seedData();



    }
}
