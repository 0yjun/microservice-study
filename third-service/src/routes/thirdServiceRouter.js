const express = require("express");
const router = express.Router();

router.get("/welcome", (req, res) => {
  res.send("welcome third");
});

router.get("/message", (req, res) => {
  const header = req.header("third-request");
  console.log(header);
  res.send("third service");
});

router.get("/check", (req, res) => {
  res.send("this is message from third service");
});

module.exports = router;
