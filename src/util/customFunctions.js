const isSameDay = (date1, date2) => {
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
}

const convertTimeToday = (time) => {
  const timestamp = new Date('2024-09-21T02:30:01.000Z');
  const [hours, minutes] = time.split(':');
  timestamp.setHours(parseInt(hours, 10), parseInt(minutes, 10), 0, 0);
  
  return timestamp;
  // const options = { timeZone: 'Asia/Jakarta', timeZoneName: 'short' };
  // return timestamp.toLocaleString('en-US', options);
};

module.exports = { 
  convertTimeToday,
  isSameDay,
};