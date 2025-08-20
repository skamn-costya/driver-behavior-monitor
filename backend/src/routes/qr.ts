// import { Router } from "express";
// // import { generateQR } from "../utils/qrGenerator";

// const router = Router();

// // GET /qr?text=somevalue
// router.get("/", async (req, res) => {
//   const { text } = req.query;
//   if (!text || typeof text !== "string") {
//     return res.status(400).json({ error: "Missing 'text' query parameter" });
//   }

//   try {
//     // const qrDataUrl = await generateQR(text);
//     res.json({ qr: qrDataUrl });
//   } catch (err) {
//     res.status(500).json({ error: "Failed to generate QR code" });
//   }
// });

// export default router;
