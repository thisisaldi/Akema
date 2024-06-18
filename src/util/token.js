require("dotenv").config();
const jwt = require("jsonwebtoken");

module.exports = (nim) => { 
  return jwt.sign({ nim }, process.env.TOKEN_KEY) 
};