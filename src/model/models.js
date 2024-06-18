const mongoose = require('mongoose');

const studentSchema = new mongoose.Schema({
  nim: {
    type: String,
    unique: true,
  },
  email: String,
  name: String,
  password: String,
  address: String,
  classOf: Number,
}, {
  collection: "student",
});

const subjectSchema = new mongoose.Schema({
  id: { 
    type: String,
    unique: true,
  },
  name: String,
  
}, {
  collection: "subject",
});



const Student = mongoose.model("Student", studentSchema);
const Subject = mongoose.model("Subject", subjectSchema);

module.exports = {
  Student,
  Subject,
};