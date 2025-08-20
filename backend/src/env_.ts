import dotenv from "dotenv";
import { env } from "process";

dotenv.config();

interface env_data_ {
	PORT: string
	G_CLIENT_ID: string | null
	G_CLIENT_SECRET: string | null
	G_REDIRECT_URI: string | null 
	SSL_PATH: string | null
	SSL_NAME: string | null
};

export const env_data: env_data_ = {
PORT: process.env.PORT || "443",
G_CLIENT_ID: process.env.G_CLIENT_ID || null,
G_CLIENT_SECRET: process.env.G_CLIENT_SECRET || null,
G_REDIRECT_URI: process.env.G_REDIRECT_URI || null,
SSL_PATH: process.env.SSL_PATH || null,
SSL_NAME: process.env.SSL_NAME || null
};

export function check(): boolean {
	console.debug("env_data = ", env_data)

	if (env_data.G_CLIENT_ID === null ||
			env_data.G_CLIENT_SECRET === null ||
			env_data.G_REDIRECT_URI === null ||
			env_data.SSL_PATH === null ||
			env_data.SSL_NAME === null)
			return false;
	return true;
}