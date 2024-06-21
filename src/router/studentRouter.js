const express = require('express');
const controller = require("../controller/studentController.js")
const router = express.Router();

router.get("/student", controller.getStudentData);
router.get("/attendance", controller.getStudentAttendanceBySubject);

router.post("/login", controller.login);
router.post("/logout", controller.logout);
router.post("/present", controller.present);

module.exports = router;