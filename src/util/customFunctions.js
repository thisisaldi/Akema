const convertTimeToday = (time) => {
  const timestamp = new Date();
  const [hours, minutes] = time.split(':');
  timestamp.setHours(parseInt(hours, 10), parseInt(minutes, 10), 0, 0);
  
  return timestamp;
  // const options = { timeZone: 'Asia/Jakarta', timeZoneName: 'short' };
  // return timestamp.toLocaleString('en-US', options);
};

const checkStatus = (izinIsTrue, now, startTime) => {
  const timeDiff = (now.getMinutes() - startTime.getMinutes());
  let status = "";

  if (izinIsTrue) {
    status = "izin";
  } else if (timeDiff >= -30 && timeDiff <= 30) {
    status = "hadir";
  } else if (timeDiff > 30 && timeDiff <= 120) {
    status = "terlambat";
  } 

  return status;
};

module.exports = { 
  convertTimeToday,
  checkStatus,
};