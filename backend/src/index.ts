import express from "express";
// import cors from "cors";
// import qrRoutes from "./routes/qr";

const app = express();
const PORT = process.env.PORT || 443;

// app.use(cors());
app.use(express.json());

// app.use("/qr", qrRoutes);

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
