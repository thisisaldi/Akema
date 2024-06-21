const { Student, Course, Attendance } = require("../model/models");
const bcrypt = require('bcrypt');
const jwt = require("jsonwebtoken");
const getToken = require('../util/token');
const { convertTimeToday, isSameDay } = require('../util/customFunctions');

const MS_IN_MINUTES = 60000;

const login = async (req, res, next) => {
  try {
    const { nim, password } = req.body;

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
        const course = await Course.findOne({id: courseCode}).select("_id starttime");
        
        const startTime = convertTimeToday(course.starttime);
        const now = new Date('2024-09-21T02:30:01.000Z');
        // const now = new Date();
        const isItToday = isSameDay(now, startTime)
        
        if(!isItToday) {
          return res.status(403).json({
            status: "Forbidden",
            message: "'You cannot submit at this time. Please try again later.'"
          })
        }

        const isAttendanceDone = await Attendance.findOne({student, course, timestamp: now});

        if (isAttendanceDone && isItToday) {
          return res.status(409).json({
            status: "Conflict",
            message: "Attendance has already been submitted."
          })
        }

        const diff = (now - startTime) / MS_IN_MINUTES;
        let status = "";

        if (izinIsTrue) {
          status = "izin";
        } else if (diff >= -30 && diff <= 30) {
          status = "hadir";
        } else if (diff > 30 && diff <= 120) {
          status = "terlambat";
        } else {
          return res.status(403).json({
            status: "SubmissionNotAllowed",
            message: "'You cannot submit at this time. Please try again later.'"
          })
        }

        const attendance = await new Attendance({
          student: student._id,
          course: course._id,
          status,
          timestamp: now,
        });

        const result = await Attendance.create(attendance);

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
        
        const { courseCode } = req.body;
        let result = {};

        const student = await Student.findOne({nim: data.nim}).select("_id");
        const course = await Course.findOne({id: courseCode}).select("_id");

        const studentRecords = await Attendance.find({ student, course }, ["status", "timestamp", "-_id"]);
        result.attendance = studentRecords
        
        let attendanceSumResult = await Attendance.aggregate([
          { 
            $group : { 
              _id: "$status", count: { 
                $sum: 1 
              } 
            } 
          } 
        ]);

        await attendanceSumResult.forEach((data) => {
          data.category = data._id;
          delete data._id;
        });

        result.category = attendanceSumResult;
        
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