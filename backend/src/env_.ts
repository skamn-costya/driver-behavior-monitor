import dotenv from "dotenv";
import { env } from "process";

dotenv.config();

interface env_data_ {
	PORT: string
	CLIENT_ID: string | null
	CLIENT_SECRET: string | null
	REDIRECT_URI: string | null 
	SSL_PATH: string | null
	SSL_NAME: string | null
};

export const env_data: env_data_ = {
PORT: process.env.PORT || "443",
CLIENT_ID: process.env.GOOGLE_CLIENT_ID || null,
CLIENT_SECRET: process.env.GOOGLE_CLIENT_SECRET || null,
REDIRECT_URI: process.env.GOOGLE_REDIRECT_URI || null,
SSL_PATH: process.env.SSL_PATH || null,
SSL_NAME: process.env.SSL_NAME || null
};

export function check(): boolean {
	if (env_data.CLIENT_ID === null ||
			env_data.CLIENT_SECRET === null ||
			env_data.REDIRECT_URI === null ||
			env_data.SSL_PATH === null ||
			env_data.SSL_NAME === null)
			return false;
	return true;
}