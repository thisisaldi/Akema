require('dotenv').config();
const express = require('express');
const rateLimit = require('express-rate-limit');
const cors = require('cors');
const cookieParser = require('cookie-parser');
const helmet = require('helmet');
const mongoose = require('mongoose');
const fs = require('fs');
const https = require('https');

const { env, port } = require("./src/configs/server.config");
const { uri, dbParams } = require('./src/configs/db.config');
const studentRouter = require('./src/router/studentRouter');

const app = express();

const limiter = rateLimit({
  windowMs: 1 * 60 * 1000, // 1 minutes
  max: 100, // 100 requests max
  message: 'Too many requests from this IP, please try again after 1 minutes',
  headers: true, 
});

app.use(limiter);
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended: true}));
app.use(cookieParser())
app.use(helmet());
app.use("/", studentRouter);

mongoose.connect(uri, dbParams)
    .then(() => console.log("MongoDB is  connected successfully"))
    .catch((err) => console.error(err));

const options = {
  cert: fs.readFileSync('./ssl/localhost.crt'),
  key: fs.readFileSync('./ssl/localhost.key'),
};

https.createServer(options, app).listen(port, () => {
  console.log(`Server started on [env, port] = [${env}, ${port}]`);
});