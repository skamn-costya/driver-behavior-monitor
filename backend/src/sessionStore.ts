type Session = {
	deviceCode: string
	userCode: string
	verificationUrl: string
	status: "pending" | "approved" | "error" | "expired"
	tokens?: any
}

const sessions: Record<string, Session> = {}

export function createSession(sessionId: string, data: Session) {
	sessions[sessionId] = data
}

export function updateSession(sessionId: string, update: Partial<Session>) {
	if (sessions[sessionId]) {
		sessions[sessionId] = { ...sessions[sessionId], ...update }
	}
}

export function getSession(sessionId: string) {
	console.debug("getSession", sessions[sessionId])
	return sessions[sessionId]
}
