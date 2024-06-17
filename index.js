require('dotenv').config();
const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');
var cookieParser = require('cookie-parser')
const { env, port } = require("./src/configs/server.config");
const { uri, dbParams } = require('./src/configs/db.config');
const studentRouter = require('./src/router/studentRouter');

const app = express();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended: true}));
app.use(cookieParser())
app.use("/", studentRouter);

mongoose.connect(uri, dbParams)
    .then(() => console.log("MongoDB is  connected successfully"))
    .catch((err) => console.error(err));

app.listen(port,() => console.log(`Server started on [env, port] = [${env}, ${port}]`));