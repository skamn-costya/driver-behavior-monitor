import express from "express";
import dotenv from "dotenv";
import { google } from "googleapis";

import * as fs from "fs";
import https from "https";
import { check, env_data } from "./env_";
import { exit } from "process";

dotenv.config();

if (check() === false) {
	console.error(`Check .env file.`);
	exit(1);
}

const appDir: string = fs.realpathSync(process.cwd());

console.log(`SSL path is ${appDir}${env_data.SSL_PATH}`);

const key = fs.readFileSync(`${appDir}${env_data.SSL_PATH}${env_data.SSL_NAME}.key`, "utf-8");
const cert = fs.readFileSync(`${appDir}${env_data.SSL_PATH}${env_data.SSL_NAME}.crt`, "utf-8");

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 443;

const CLIENT_ID = env_data.G_CLIENT_ID!;
const CLIENT_SECRET = env_data.G_CLIENT_SECRET!;
const REDIRECT_URI = env_data.G_REDIRECT_URI!;

// Инициализация OAuth2 клиента
const oauth2Client = new google.auth.OAuth2(
	CLIENT_ID,
	CLIENT_SECRET,
	REDIRECT_URI
);

// Генерация ссылки для QR
app.get("/auth/qr", (req, res) => {
	const url = oauth2Client.generateAuthUrl({
		access_type: "offline",
		scope: ["profile", "email"],
		prompt: "consent",
	});
	res.json({ qrUrl: url });
});

app.get("/", (req, res) => {
	res.json({ Hello: "world" }); // возвращаем строку
});

https.createServer({ key, cert }, app).listen(PORT);