const mongoose = require('mongoose');

const studentSchema = new mongoose.Schema({
  nim: {
    type: String,
    unique: true,
  },
  name: String,
  password: String,
}, {
  collection: "student",
});

module.exports = mongoose.model("Student", studentSchema);;