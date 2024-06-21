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
  courses: [{ 
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'Course' 
  }],
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

const courseSchema = new mongoose.Schema({
  id: {
    type: String,
    unique: true,
  },
  subject: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Subject",
  },
  room: String,
  starttime: String,
  endtime: String,
}, {
  collection: "course",
});

const attendanceSchema = new mongoose.Schema({
  student: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Student",
  },
  course: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Course",
  },
  status: {
    type: String,
    enum: ["hadir", "izin", "terlambat", "absen"]
  },
  timestamp: Date,
}, {
  collection: "attendance",
  versionKey: false,
});

attendanceSchema.index({ student: 1, course: 1 });

const Student = mongoose.model("Student", studentSchema);
const Subject = mongoose.model("Subject", subjectSchema);
const Course = mongoose.model("Course", courseSchema);
const Attendance = mongoose.model("Attendance", attendanceSchema);

module.exports = {
  Student,
  Subject,
  Course,
  Attendance,
};