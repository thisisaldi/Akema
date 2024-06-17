require('dotenv').config();
const { env } = require("./server.config");

const uri = env === 'prod'? process.env.ATLAS_URI : 'mongodb://localhost:27017/akema';
    
module.exports = {
  uri
};