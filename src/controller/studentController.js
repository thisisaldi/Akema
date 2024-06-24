const { Student, Course, Attendance } = require("../model/models");
const bcrypt = require('bcrypt');
const jwt = require("jsonwebtoken");
const getToken = require('../util/token');
const { convertTimeToday,  checkStatus } = require('../util/customFunctions');
const { isAttendanceExists, getAttendance, createAttendance } = require('../util/attendanceFunctions');

const login = async (req, res, next) => {
  try {
    const { nim, password } = req.body;

    if (!nim || !password) {
      return res.status(400).json({
        status: "Bad Request",
        message: "Please provide nim and password"
      })
    }

    const student = await Student.findOne({ nim }, ["nim", "password"])
    
    if (student !== undefined || student !== null) {
      const verify = await bcrypt.compare(password, student.password);
      
      if(verify) {
        const token = getToken(student.nim)
        res.cookie('token', token, {
          withCredentials: true,
          httpOnly: false,
        });

        return res.status(201).json({
          status: "OK",
          message: "Success",
        });
      } else {
        return res.status(401).json({
          status: "Unauthorized",
          message: "Incorrect username or password!"
        });
      }
    } else {
      return res.status(400).json({
        status: "Bad Request",
        message: "Incorrect username or password"
      });
    }
  } catch (error) {
    return res.status(500).json({
      status: "Internal Server Error",
      message: error.toString()
    });
  }
};

const logout = async(req, res, next) => {
  try {
    const token = req.cookies.token

    if (token) {
      jwt.verify(token, process.env.TOKEN_KEY, async (err, data) => {
        if (err) {
          return res.status(401).json({
            status: "Unauthorized",
            data: err,
            message: "Please login!"
          })
        }

        return res.status(200).clearCookie("token").json({
          status: "OK",
          message: "Success"
        });
      });
    } else {
      return res.status(401).json({
        status: "Unauthorized",
        message: "Please login!"
      })
    }
  } catch (error) {
    return res.status(500).json({
      status: "Internal Server Error",
      message: error.toString()
    });
  }
};

const getStudentData = (req, res) => {
  try {
    const token = req.cookies.token;

    if (token) {
      jwt.verify(token, process.env.TOKEN_KEY, async (err, data) => {
        
        if (err) {
          return res.status(401).json({
            status: "Unauthorized",
            data: err,
            message: "Please login!"
          })
        }

        const student = await Student.findOne({ nim: data.nim }, ["-_id", "-password"]);
        await student.populate({path: 'courses', select: '-_id'});
        await student.populate({path: 'courses.subject', select: "name"});
        const studentData = student?.toObject();

        await studentData.courses.forEach((data) => {
          data.subject = data.subject.name;
        });
        
        return res.status(200).json({
          status: "OK",
          data: studentData
        });
      });
    } else {
      return res.status(401).json({
        status: "Unauthorized",
        message: "Please login!"
      })
    }
  } catch (error) {
    return res.status(500).json({
      status: "Internal Server Error",
      message: error.toString(),
    });
  }
};

const present = (req, res) => {
  try {
    const token = req.cookies.token;

    if (token) {
      jwt.verify(token, process.env.TOKEN_KEY, async (err, data) => {
        if (err) {
          return res.status(401).json({
            status: "Unauthorized",
            data: err,
            message: "Please login!"
          })
        }

        const student = await Student.findOne({nim: data.nim}).select("_id");

        const { courseCode, izinIsTrue } = req.body;
        if (!courseCode || izinIsTrue === undefined) {
          return res.status(400).json({
            status: "Bad Request",
            message: "Please provide courseCode and izinIsTrue!"
          })
        }

        const course = await Course.findOne({ id: courseCode }).select("_id day starttime");
        if (!course) {
          return res.status(404).json({
            status: "Bad Request",
            message: "courseCode is wrong or the course is not found."
          });
        }

        const startTime = convertTimeToday(course.starttime);
        const now = new Date();

        if (await isAttendanceExists(now, student, course)) {
          return res.status(409).json({
            status: "Conflict",
            message: "Attendance has already been submitted."
          })
        }
        
        const weekday = ["Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"];
        const isTheDaySame = course.day === weekday[now.getDay()];
        const status = checkStatus(izinIsTrue, now, startTime);

        if(!isTheDaySame || status === "") {
          return res.status(403).json({
            status: "Forbidden",
            message: "'You cannot submit at this time. Please try again later.'"
          })
        }
        
        const result = await createAttendance(student,course, status, now);

        return res.status(201).json({
          status: "OK",
          result,
          message: "Attendance has been recorded."
        });
      });
    } else {
      return res.status(401).json({
        status: "Unauthorized",
        message: "Please login!"
      })
    }
  } catch (error) {
    return res.status(500).json({
      status: "Internal Server Error",
      message: error.toString(),
    });
  }
};

const getStudentAttendanceBySubject = (req, res) => {
  try {
    const token = req.cookies.token;

    if (token) {
      jwt.verify(token, process.env.TOKEN_KEY, async (err, data) => {
        if (err) {
          return res.status(401).json({
            status: "Unauthorized",
            data: err,
            message: "Please login!"
          })
        }
        
        const { courseCode } = req.query;

        if (!courseCode) {
          return res.status(400).json({
            status: "Bad Request",
            message: "Please provide courseCode!"
          })
        }

        const student = await Student.findOne({ nim: data.nim }).select("_id");
        const course = await Course.findOne({ id: courseCode }).select("_id");
        if (!course) {
          return res.status(404).json({
            status: "Bad Request",
            message: "courseCode is wrong or the course is not found."
          })
        }

        result = await getAttendance(student, course);
        
        return res.status(201).json({
          status: "OK",
          data: result,
        });
      });
    } else {
      return res.status(401).json({
        status: "Unauthorized",
        message: "Please login!"
      })
    }
  } catch (error) {
    return res.status(500).json({
      status: "Internal Server Error",
      message: error.toString(),
    });
  }
};

module.exports = {
  login,
  logout,
  getStudentData,
  present,
  getStudentAttendanceBySubject,
}