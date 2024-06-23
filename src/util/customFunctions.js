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
  } else if (timeDiff > 120) {
    status = "absen";
  }

  return status;
};

const wibOffset = 7 * 60 * 60 * 1000;

const UTCtoWIB = (datetime) => {
  return new Date(datetime.getTime() + wibOffset);
};

const WIBtoUTC = (datetime) => {
  return new Date(datetime.getTime() - wibOffset)
};

const getStartEndofDay = (datetime) => {
  let startOfDay = datetime
  startOfDay.setUTCHours(0, 0, 0, 0);
  startOfDay = WIBtoUTC(startOfDay);

  let endOfDay = datetime
  endOfDay.setUTCHours(23, 59, 0, 0);
  endOfDay = WIBtoUTC(endOfDay);

  return [startOfDay, endOfDay];
};

module.exports = { 
  convertTimeToday,
  checkStatus,
  UTCtoWIB,
  WIBtoUTC,
  getStartEndofDay,
};