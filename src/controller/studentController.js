const bcrypt = require('bcrypt');
const Student = require("../model/studentModel");
const getToken = require('../util/token');
const jwt = require("jsonwebtoken");

const login = async (req, res, next) => {
  try {
    const { nim, password } = req.body;

    let student = await Student.findOne({ nim })
    student = student?.toObject();
    
    if (student !== undefined) {
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

module.exports = {
  login,
  logout
}