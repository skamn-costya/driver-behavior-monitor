import { Request, Response } from "express"
import fetch from "node-fetch"
import { v4 as uuidv4 } from "uuid"
import { createSession, getSession, updateSession } from "./sessionStore"
import { env_data } from "./env_data";

const DEVICE_CODE_URL: string = "https://oauth2.googleapis.com/device/code"
const TOKEN_URL: string = "https://oauth2.googleapis.com/token"

// 1. Клиент (AAOS) запрашивает QR
export async function getQr(req: Request, res: Response) {
	try {
		const response = await fetch(DEVICE_CODE_URL, {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams({
				client_id: env_data.G_CLIENT_ID!,
				scope: "openid email profile",
			}),
		})

		const data: any = await response.json()

		console.debug("getQr return data -> ", data)

		const sessionId = uuidv4()
		createSession(sessionId, {
			deviceCode: data.device_code,
			userCode: data.user_code,
			verificationUrl: data.verification_url,
			status: "pending",
		})

		// Отдаём AAOS клиенту строку для QR
		res.json({
			sessionId,
			qrData: `${data.verification_url}?user_code=${data.user_code}`,
			userCode: `${data.user_code}`,
		})

		// Запускаем polling
		startPolling(sessionId, data.device_code, data.interval, data.expires_in)
	} catch (e) {
		console.error("getQr error", e)
		res.status(500).json({ error: "Failed to create QR session" })
	}
}

// 2. Polling токена от Google
async function startPolling(sessionId: string, deviceCode: string, interval: number, expiresIn: number) {
	const start = Date.now();
	const poll = async () => {
		if ((Date.now() - start) / 1000 > expiresIn) {
			updateSession(sessionId, { status: "expired" });
			return;
		}

		try {
			const resp = await fetch(TOKEN_URL, {
				method: "POST",
				headers: { "Content-Type": "application/x-www-form-urlencoded" },
				body: new URLSearchParams({
					client_id: env_data.G_CLIENT_ID!,
					client_secret: env_data.G_CLIENT_SECRET!,
					device_code: deviceCode,
					grant_type: "urn:ietf:params:oauth:grant-type:device_code",
				}),
			})
			const data: any = await resp.json()
			console.debug("QR Polling got", data)

			if (data.error === "authorization_pending") {
				setTimeout(poll, interval * 1000)
			} else if (data.error) {
				updateSession(sessionId, { status: "error" })
			} else {
				updateSession(sessionId, { status: "approved", tokens: data })
			}
		} catch (e) {
			console.error("Polling error", e)
			updateSession(sessionId, { status: "error" })
		}
	}
	setTimeout(poll, interval * 1000)
}

// 3. Клиент (AAOS) проверяет статус
export function getStatus(req: Request, res: Response) {
	const { sessionId } = req.params
	const session = getSession(sessionId)
	if (!session) {
		return res.status(404).json({ error: "Session not found" })
	}
	res.json(session)
}
