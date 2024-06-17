require("dotenv").config();
const jwt = require("jsonwebtoken");

module.exports = (id) => { 
  return jwt.sign({ id }, process.env.TOKEN_KEY) 
};