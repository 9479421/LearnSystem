import { app, BrowserWindow, Menu, ipcMain } from "electron";
import { electron } from "webpack";
import "../renderer/store";



/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== "development") {
  global.__static = require("path")
    .join(__dirname, "/static")
    .replace(/\\/g, "\\\\");
}

let mainWindow;
const winURL =
  process.env.NODE_ENV === "development"
    ? `http://localhost:9080`
    : `file://${__dirname}/index.html`;

function createWindow() {
  /**
   * Initial window options
   */

  Menu.setApplicationMenu(null);
  mainWindow = new BrowserWindow({
    height: 983,
    useContentSize: true,
    width: 1381,
    frame: false, // 去掉默认的标题栏
    webPreferences:{
      // devTools:false
      webSecurity: false,
      partition: String(+new Date())
    }
  });

  mainWindow.loadURL(winURL);
 
  mainWindow.on("closed", () => {
    mainWindow = null;
  });
}

ipcMain.on('close-app', () => {
  if (mainWindow) {
    mainWindow.close()
  }
})
ipcMain.on('min-app', () => {
  mainWindow.minimize()
})

app.on("ready", createWindow);

app.on("window-all-closed", () => {
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  if (mainWindow === null) {
    createWindow();
  }
});

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

/*
import { autoUpdater } from 'electron-updater'

autoUpdater.on('update-downloaded', () => {
  autoUpdater.quitAndInstall()
})

app.on('ready', () => {
  if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
})
 */
