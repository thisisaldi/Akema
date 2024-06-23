const { Attendance, Course, Student } = require('../model/models');
const { getStartEndofDay } = require('../util/customFunctions');

const isAttendanceExists = async (now, student, course) => {
  const [startOfDay, endOfDay] = getStartEndofDay(now)

  const todayAttendanceTimestamp = (await Attendance.findOne({student, course, 
    timestamp: { 
      $gte: startOfDay, 
      $lte: endOfDay 
    }
  }, ["timestamp"]))?.timestamp;
  
  if (!todayAttendanceTimestamp) 
    return false;
  else if (todayAttendanceTimestamp.getDate() === now.getDate())
    return true;
  return false
}

const createAttendance = async (student, course, status, now) => {
  const attendance = new Attendance({
    student: student._id,
    course: course._id,
    status,
    timestamp: now,
  });

  return await Attendance.create(attendance);
};

const getAttendance = async (student, course) => {
  let result = {};
  const studentRecords = await Attendance.find({ student, course }, ["status", "timestamp", "-_id"]);
  result.attendance = studentRecords;
  
  let attendanceSumResult = await Attendance.aggregate([
    {
      $match: {
        student: student._id,
        course: course._id,
      }
    },
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
  return result
}

module.exports = {
  isAttendanceExists,
  getAttendance,
  createAttendance,
}