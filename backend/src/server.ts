import express from "express";
import dotenv from "dotenv";

import cors from "cors";
import { getQr, getStatus } from "./authController";


import * as fs from "fs";
import https from "https";
import { check, env_data } from "./env_data";
import { exit } from "process";

dotenv.config();

if (check() === false) {
	console.error(`Check .env file.`);
	exit(1);
}

const appDir: string = fs.realpathSync(process.cwd());

console.log(`SSL path is ${appDir}${env_data.SSL_PATH}`);

const app = express();
app.use(cors())
app.use(express.json());

app.get("/", (req, res) => {
	res.json({ Hello: "world" }); // возвращаем строку
});

app.get("/auth/qr", getQr)
app.get("/auth/status/:sessionId", getStatus)

https.createServer({
		key:  fs.readFileSync(`${appDir}${env_data.SSL_PATH}${env_data.SSL_NAME}.key`, "utf-8"),
		cert: fs.readFileSync(`${appDir}${env_data.SSL_PATH}${env_data.SSL_NAME}.crt`, "utf-8"),
		ca: fs.readFileSync(`${appDir}${env_data.SSL_PATH}${env_data.SSL_CA}.crt`, "utf-8"),
	}, app).listen(env_data.PORT);