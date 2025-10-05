const { defineConfig } = require("cypress");

module.exports = defineConfig({
    e2e: {
        baseUrl: "http://localhost:5173",
        video: true,
        viewportWidth: 1280,
        viewportHeight: 800,
        supportFile: false
    }
});
