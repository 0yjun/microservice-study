const expresss = require("express");
const app = expresss();
const thirdServiceRouter = require("./routes/thirdServiceRouter");
require("dotenv").config();

app.use("/third-service", thirdServiceRouter);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`server start at ${PORT}`);
});
