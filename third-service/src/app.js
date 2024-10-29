const expresss = require("express");
const app = expresss();
const thirdServiceRouter = require("./routes/thirdServiceRouter");
const Eureka = require("eureka-js-client").Eureka;
require("dotenv").config();

const PORT = process.env.PORT || 3000;

const client = new Eureka({
  instance: {
    app: "third-service",
    hostName: "localhost",
    ipAddr: "127.0.0.1",
    port: {
      $: PORT,
      "@enabled": "true",
    },
    vipAddress: "third-service",
    dataCenterInfo: {
      "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
      name: "MyOwn",
    },
  },
  eureka: {
    host: "localhost",
    port: 8761,
    servicePath: "/eureka/apps",
  },
});

// Eureka에 서비스 등록
client.start((error) => {
  console.log("Eureka client started");
  if (error) {
    console.error("Eureka registration failed:", error);
  } else {
    console.log("third-service registered with Eureka");
  }
});
app.use(expresss.json());
app.use("/third-service", thirdServiceRouter);

app.listen(PORT, () => {
  console.log(`server start at ${PORT}`);
});
